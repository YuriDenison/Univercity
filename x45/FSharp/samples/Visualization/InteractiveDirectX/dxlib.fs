//----------------------------------------------------------------------------
// The Famous DirectX Demo - library code
//
// Copyright (c) Microsoft Corporation 2005-2008.
//
// This sample code is provided "as is" without warranty of any kind. 
// We disclaim all warranties, either express or implied, including the 
// warranties of merchantability and fitness for a particular purpose. 
//----------------------------------------------------------------------------


module Sample.DirectX

open System
open System.Drawing
open System.Threading  
open System.Windows.Forms
open Microsoft.DirectX
open Microsoft.DirectX.Direct3D

//----------------------------------------------------------------------------
// common
//----------------------------------------------------------------------------

module MathOps = 
    type time = float
    let now () = (float Environment.TickCount / 1000.0) |> single
    let sqr (x:float32) = x * x
    let pi = Math.PI |> single

/// Vectors: origin, basis and operations 
module VectorOps =

    open MathOps

    let O   = Vector3( 0.0f, 0.0f, 0.0f) 
    let X1  = Vector3( 1.0f, 0.0f, 0.0f) 
    let Y1  = Vector3( 0.0f, 1.0f, 0.0f)
    let Z1  = Vector3( 0.0f, 0.0f, 1.0f)

    let cross     u v = Vector3.Cross(u,v)
    let normalize u   = Vector3.Normalize(u)
    let scale     k u = Vector3.Scale(u,k)
    let planeProject n v = v - scale (Vector3.Dot(n,v)) n // n is plane normal 
    let transformAt v m = Matrix.Translation(v) * m * Matrix.Translation(-v)  
    let singlev (x,y,z) = Vector3(single x,single y,single z)


open MathOps
open VectorOps


//----------------------------------------------------------------------------
// DirectX form
//
// Create an enclosing form which owns its own painting.

type SmoothForm() as x = 
    inherit Form()
    do x.SetStyle(ControlStyles.AllPaintingInWmPaint ||| ControlStyles.Opaque ||| ControlStyles.UserPaint, true);


//----------------------------------------------------------------------------
// Color Interpolation


module Vertex =     

    let Colored (c:Color) v = CustomVertex.PositionNormalColored(v,Z1,c.ToArgb())

    let White v = Colored Color.White v

    let Interpolated x v =
        let r,g,b =
            if x < 0.0f then (1.0f,0.0f,0.0f)
            elif x < 0.5f then 
               let z = 2.0f * x       
               1.0f-z,z,0.0f 
            elif x < 1.0f then
               let z = 2.0f * x - 1.0f 
               0.0f,1.0f-z,z   
            else
               0.0f,0.0f,1.0f
        let byte x = int (x * 255.0f) 
        let color = Color.FromArgb(0,byte r,byte g,byte b)
        Colored color v

//----------------------------------------------------------------------------
// Mouse events
//----------------------------------------------------------------------------

let MouseTracker (c : #Control) = 
    let event = new Event<_>() 
    let lastArgs = ref None
    c.MouseDown.Add(fun args -> lastArgs := Some args)
    c.MouseUp.Add  (fun args -> lastArgs := None)
    c.MouseMove.Add(fun args -> 
        match !lastArgs with
        | Some last -> event.Trigger(last,args); lastArgs := Some args
        | None -> ());
    event.Publish
                                 
//----------------------------------------------------------------------------
// setView
//----------------------------------------------------------------------------
    
// yaw, pitch, roll, Focus, Zoom state
type View = 
    { YawPitchRoll: Matrix;
      Focus       : Vector3;
      Zoom        : float }
    with
        member view.AdjustZoom(dx,dy) = 
            { view with Zoom = view.Zoom * exp (float dy / 100.0) } 

        member view.AdjustYawPitchRoll(dx,dy) = 
            let rx = float32 dx / 20.0f
            let ry = float32 dy / 20.0f    
            let m = Matrix.RotationYawPitchRoll(ry,0.0f,rx)    // Rotate 
            let m = transformAt ((X1 + Y1 + Z1) *  -0.5f ) m // at centre Vertex.White 
            { view with YawPitchRoll = m * view.YawPitchRoll }

        member view.AdjustFocus(dx,dy) = 
            let dv = Y1 * (float32 (-dx) / 50.0f)  + Z1 * (float32 dy / 50.0f) 
            { view with Focus = view.Focus + dv } // Move Focus 
    end


type BaseMesh(X,Y) = 
    static member Create(gx,gy,m,n) =
        let X = Array2D.init n m (fun i j -> gx n i)
        let Y = Array2D.init n m (fun i j -> gy m j)
        BaseMesh(X,Y)
        
    static member Grid(m,n) = 
        let normi n i = float32 i / float32 (n-1)
        BaseMesh.Create(normi,normi,m,n)

    member x.Dimensions = Array2D.length1 X, Array2D.length2 Y

    member x.Item with get(i,j  ) = X.[i,j], Y.[i,j]

    member x.ToIndex(i,j) = let m,n = x.Dimensions in i + j * n 

    member x.FromIndex(k) = let m,n = x.Dimensions in k%n,k/n   


type VertexBuffer<'primType>(size,device,usage,pool) = 
    let format = 
        match (typeof<'primType>) with 
        | ty when ty = (typeof<CustomVertex.PositionColored>)         -> CustomVertex.PositionColored.Format
        | ty when ty = (typeof<CustomVertex.PositionColoredTextured>) -> CustomVertex.PositionColoredTextured.Format
        | ty when ty = (typeof<CustomVertex.PositionOnly>)            -> CustomVertex.PositionOnly.Format
        | ty when ty = (typeof<CustomVertex.PositionNormalColored>)   -> CustomVertex.PositionNormalColored.Format
        | ty when ty = (typeof<CustomVertex.PositionNormalTextured>)  -> CustomVertex.PositionNormalTextured.Format
        | _ -> failwith "unknown format type" 
    let vertexBuffer =  new VertexBuffer((typeof<'primType>), size, device, usage, format, pool) 

    member x.VertexBuffer = vertexBuffer
    member x.SetData(pts :'primType[]) = vertexBuffer.SetData(pts,0,LockFlags.None)

    member x.DrawPrimitive primitive nPrim =
        device.SetStreamSource(streamNumber=0,streamData=vertexBuffer,offsetInBytes=0);
        device.VertexFormat <- format;
        device.DrawPrimitives(primitive,0,nPrim)

    interface IDisposable with 
        member x.Dispose() = vertexBuffer.Dispose()

    static member OfPoints(pts : 'primType[], device) =
        let vertexBuffer = new VertexBuffer<'primType>
                                    (pts.Length, // number pts 
                                     device,     
                                     Usage.None,
                                     // Pool.Managed resources survive device loss 
                                     Pool.Managed) 
        vertexBuffer.SetData(pts);
        vertexBuffer

              
type DirectXRenderer(control:Control) = 

    let drawEvent = new Event<_>()
    let pparams  = 
        PresentParameters(Windowed=true, 
                          SwapEffect=SwapEffect.Discard,
                          // Turn on a Depth stencil
                          EnableAutoDepthStencil=true, 
                          // And the stencil format
                          AutoDepthStencilFormat=DepthFormat.D16)  
    let device =
      new Device(0, DeviceType.Hardware, control,
                    CreateFlags.SoftwareVertexProcessing, 
                    [| pparams |]) 

    /// Reset the device if it is rudely taken from us
    let rec checkResetThen f = 
        if device.CheckCooperativeLevel() 
        then f()
        else 
            let ok,res = device.CheckCooperativeLevel()
            if not ok && res = int ResultCode.DeviceNotReset then 
                device.Reset([|pparams|]);
                checkResetThen f

    let checkVisibleThen f = if control.Visible then f()
        
    let clearScene (color:Color) (device:Device) =
        device.Clear(ClearFlags.ZBuffer ||| ClearFlags.Target,color, 1.0f, 0)

    /// If device available and not hidden, do the required device actions 
    let doRender () = 
        checkResetThen (fun () -> 
            checkVisibleThen (fun () -> 
                device.BeginScene();
                clearScene Color.Black device;
                drawEvent.Trigger(now());
                device.EndScene();
                try device.Present() with _ -> ()))
        
    // doInitialize: initialise device properties and invalidate to trigger redraw       
    let doInitialize() =
        device.RenderState.ZBufferEnable <- true;
        device.RenderState.Ambient <- Drawing.Color.White;
        control.Invalidate()
      
    // Render loop and initial condition
    do device.DeviceReset.Add(fun _ -> doInitialize())
    do doInitialize()
    do control.Paint.Add(fun _ -> doRender(); control.Invalidate())

    // publish
    member x.Device = device
    [<CLIEvent>]
    member x.DrawScene = drawEvent.Publish

    member x.DrawTriangeStrip (ptsA : CustomVertex.PositionNormalColored[]) =
        using (VertexBuffer.OfPoints(ptsA,device)) (fun vb -> 
            vb.DrawPrimitive PrimitiveType.TriangleStrip (ptsA.Length-2))

    member x.DrawLines (ptsA : CustomVertex.PositionNormalColored[]) =
        using (VertexBuffer<_>.OfPoints(ptsA,device)) (fun vb -> 
            vb.DrawPrimitive PrimitiveType.LineList (ptsA.Length/2))

    member x.DrawSurface (mesh:BaseMesh) f =
        let nRows,nCols = mesh.Dimensions
        let data = Array.init (nCols*nRows) (fun k ->
                                       let i,j = mesh.FromIndex(k)
                                       let x,y = mesh.[i,j]
                                       let z = f x y  
                                       (x,y,z))  

        let blendPlace (i,j) =
            let k = mesh.ToIndex(i,j)
            let x,y,z = data.[k]
            Vertex.Interpolated z (X1*x + Y1*y + Z1*z)

        let colorPlace c (i,j) =
            let k = mesh.ToIndex(i,j)
            let x,y,z = data.[k]
            Vertex.Colored c (X1*x + Y1*y + Z1*z)

        let triangleRows = [| for j in 0 .. (nRows-2) 
                                -> [| for i in 0 .. (2*nCols-1) 
                                       -> if i%2 = 0 then (i/2,j) else (i/2,j+1) |] |]

        for row in triangleRows do 
            let strip = Array.map blendPlace row
            x.DrawTriangeStrip strip
            
        let lines = [| for i in 0 .. nCols-2 do
                       for j in 0 .. nRows-2 do
                       yield! [| (i,j); (i+1,j); (i,j); (i,j+1) |] |]
                      
        let lines = lines |> Array.map (colorPlace Color.Black) 
        x.DrawLines lines

    member x.DrawCubeAxis() =

        let planeN = 6  // number of division on XY plane grid 
        let planePts = 
            [| for i in 0 .. planeN do 
               let k = float32 i / float32 planeN 
               for p in  [| Y1*k; X1 + Y1*k;         // Line k.Y  to    X + k.Y 
                            X1*k; Y1 + X1*k; |]      // Line k.X  to  k.X +   Y 
               -> Vertex.Colored Color.Gray p |]

        let boxPts =
            Array.append
              (Array.map Vertex.White 
                 [| O ;O  + Z1;
                    O ;X1;
                    X1 ;X1 + Y1; |])
              (Array.map (Vertex.Colored Color.Gray)
                 [| Y1      ; Y1      + Z1;
                    X1 + Y1 ; X1 + Y1 + Z1;
                    O + Z1 ; Y1      + Z1;
                    Y1 + Z1 ; X1 + Y1 + Z1; |])

        device.RenderState.CullMode <- Cull.None;
        x.DrawLines planePts;
        x.DrawLines boxPts 

    member x.DrawPlaneArrow n p dir = 
        let dir2 = normalize (cross n dir)
        x.DrawLines 
          (Array.map 
              Vertex.White 
              [| p + dir * 0.15f ; p;
                 p + dir * 0.15f ; p + dir * 0.10f  + dir2 * 0.02f ;
                 p + dir * 0.15f ; p + dir * 0.10f  - dir2 * 0.02f |]) 


    member x.SetView(view) =
        let eye = (X1 + Y1 + Z1) * 2.0f - X1 * single view.Zoom
        device.Transform.View <- Matrix.Invert(view.YawPitchRoll) * Matrix.LookAtLH(eye,view.Focus,Z1);
        device.Transform.Projection <-
            Matrix.PerspectiveFovLH(fieldOfViewY=single (Math.PI / 8.0),  
                                    aspectRatio =1.0f,                    
                                    znearPlane  =0.1f,                    
                                    zfarPlane   =100.0f);
        device.Transform.World <- Matrix.Identity

    member x.SetupLights() =
        let mutable material = new Direct3D.Material(DiffuseColor=ColorValue.FromColor(Color.White),
                                                     AmbientColor=ColorValue.FromColor(Color.White))
        device.Material <- material
        device.RenderState.Lighting <- true
        device.Lights.[0].Type      <- LightType.Directional
        device.Lights.[0].Diffuse   <- System.Drawing.Color.White
        device.Lights.[0].Direction <- Vector3(0.0f,0.0f,-1.0f)
        device.Lights.[0].Enabled   <- true
        device.RenderState.Ambient <- System.Drawing.Color.FromArgb(0x101010)


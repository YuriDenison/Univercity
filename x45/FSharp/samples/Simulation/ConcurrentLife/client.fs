// Copyright (c) Microsoft Corporation 2005-2008.
// This sample code is provided "as is" without warranty of any kind. 
// We disclaim all warranties, either express or implied, including the 
// warranties of merchantability and fitness for a particular purpose. 

//--------------------------------------------------------------------------
// Game of life: client GUI
//-------------------------------------------------------------------------- 

// NOTE: This sample uses 'light' syntax.  This means whitespace
// is signficant.


#nowarn "40"

open System
open System.Windows.Forms
open System.IO
open System.Drawing
open System.Drawing.Drawing2D
open System.Drawing.Imaging
open System.ComponentModel
open Worker

//--------------------------------------------------------------------------

type DoubleBufferedForm() = 
    inherit Form()
    do base.DoubleBuffered<-true

/// A bitmap is used to store the visual state
type Client() = 

    let deadColor = Color.Black
    let liveColor = Color.SeaGreen
    let gridSize = 60
    let magnification = 7
    

    let bitmap = new Bitmap(gridSize,gridSize,PixelFormat.Format24bppRgb) 
    let plotOnBitmap c (i,j) = bitmap.SetPixel(i,j,c) 
    let clearBitmap () = 
        for i = 0 to gridSize - 1 do
          for j = 0 to gridSize - 1 do
            bitmap.SetPixel(i,j,deadColor)
          
    /// Create the worker object and its thread.  The worker performs
    /// computations and fires events back on the GUI thread.
    let form = new DoubleBufferedForm(Width = magnification*gridSize,
                                      Height = magnification *gridSize, 
                                      Text = "Life", 
                                      Menu = new MainMenu())

    do form.ResizeEnd.Add(fun _ -> form.Invalidate())
    do form.Closing.Add(fun _ -> Application.Exit())

    // Add the operations to redraw the GUI at various points
    let guiRefresh(graphics: Graphics) = 
        let region = new Rectangle(0,0,gridSize,gridSize) 
        let cliprect2 = form.ClientRectangle 
        lock graphics (fun () -> graphics.DrawImage(bitmap,cliprect2,region,GraphicsUnit.Pixel))

    do form.Paint.Add(fun e -> guiRefresh e.Graphics)

    /// Create and configure the worker automata, ready to be placed
    /// onto a thread. 
    let worker = new Worker(gridSize)
                                  
    // Create and configure the menus. Note that the individual 
    // menu items can be referred to before they are specified. 
    let fileMenu = form.Menu.MenuItems.Add("&File")

    // Each menu item sets a signal for the worker to see
    // and reconfigures the state of the other menu items
    let rec runMenuItem : MenuItem = 
        new MenuItem("&Run", 
                     (fun _ _ -> 
                       runMenuItem.Enabled <- false
                       stopMenuItem.Enabled <- true
                       inputMenuItem.Enabled <- true
                       stepMenuItem.Enabled <- false
                       worker.RunAsync() ),
                     Shortcut.CtrlG)

    and stopMenuItem : MenuItem = 
        new MenuItem("&Stop", 
                     (fun _ _ -> 
                       runMenuItem.Enabled <- true
                       stopMenuItem.Enabled <- false
                       stepMenuItem.Enabled <- true
                       worker.StopAsync()),
                     Shortcut.CtrlC)
          

    and resetMenuItem : MenuItem = 
        new MenuItem("&Reset", 
                     (fun _ _ -> 
                         clearBitmap()
                         worker.ResetAsync() ),
                     Shortcut.CtrlR)
          
    and stepMenuItem : MenuItem = 
        new MenuItem("&Step", 
                     (fun _ _ -> worker.StepAsync()),
                     Shortcut.CtrlS)
          
    and inputMenuItem : MenuItem = 
        new MenuItem("User &Input", 
                     (fun _ _ -> inputMenuItem.Checked <- not inputMenuItem.Checked),
                     Shortcut.CtrlF)
          
    and closeMenuItem : MenuItem = 
        new MenuItem("Close", 
                     (fun _ _ -> form.Close()), 
                     Shortcut.CtrlX)
          
    // This callback is invoked by the worker on the GUI thread
    // when the game finishes early, which case the menu items need to
    // be reconfigured.
    do worker.FinishedEarly.Add(fun () ->
                 stopMenuItem.Enabled <- false
                 runMenuItem.Enabled <- true
                 inputMenuItem.Enabled <- false
                 stepMenuItem.Enabled <- false)
        
    // This is the refresh operation that will be invoked by the worker. 
    do worker.Updates.Add(fun updates ->
        updates |> Seq.iter (fun (born,died) ->
                 died |> List.iter (plotOnBitmap deadColor) 
                 born |> List.iter (plotOnBitmap liveColor) 
                 form.Invalidate()
                 ))
           
    do fileMenu.MenuItems.Add(runMenuItem)   |> ignore
    do fileMenu.MenuItems.Add(resetMenuItem) |> ignore
    do fileMenu.MenuItems.Add(stepMenuItem)  |> ignore
    do fileMenu.MenuItems.Add(stopMenuItem)  |> ignore
    do fileMenu.MenuItems.Add(inputMenuItem) |> ignore
    do fileMenu.MenuItems.Add("-") |> ignore
    do fileMenu.MenuItems.Add(closeMenuItem) |> ignore 

    //------------------------------------------------------------------
    // Add the mouse tracking that populates the userInput queue.

    do form.MouseMove 
       |> Event.filter (fun e -> e.Button = MouseButtons.Left)
       |> Event.filter (fun _ -> inputMenuItem.Checked)
       |> Event.add (fun evArgs -> 
            // inj: Drawing -> Grid 
            let injX x = (gridSize * (x+1) - 1) / form.ClientSize.Width 
            let injY y = (gridSize * (y+1) - 1) / form.ClientSize.Height
            let x,y = injX evArgs.X,injY evArgs.Y 
            if 0 <= x && x < gridSize  && 0 <= y && y < gridSize then 
               worker.UserInputAsync [(x,y)]
          )
      
    //------------------------------------------------------------------
    // Final steps for each form: set the initial menu state, record 
    // and activate the created form, start the worker

    do inputMenuItem.Enabled <- true
    do runMenuItem.Enabled <- true
    do stopMenuItem.Enabled <- true
    do stepMenuItem.Enabled <- false
    do form.Visible <- true
    do form.Activate()
    do worker.Start()
    
    member x.Form = form



let client = new Client()

// Run the main code. The attribute marks the startup application thread as "Single 
// Thread Apartment" mode, which is necessary for GUI applications. 
[<STAThread>]    
do Application.Run(client.Form)


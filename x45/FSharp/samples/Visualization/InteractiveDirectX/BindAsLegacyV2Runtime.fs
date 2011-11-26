module Sample.DirectXBindings

open System
open System.Runtime.InteropServices
open System.Runtime.CompilerServices


//----------------------------------------------------------------------------
// Bind for mixed-mode assembly.
// Avoid: error FS0193: Mixed mode assembly is built against version 'v1.1.4322' of the runtime and cannot be loaded in the 4.0 runtime without additional configuration information.
//----------------------------------------------------------------------------

[<ComImport>]
[<InterfaceType(ComInterfaceType.InterfaceIsIUnknown)>]
[<Guid("BD39D1D2-BA2F-486A-89B0-B4B0CB466891")>]
type ICLRRuntimeInfo =
    // vtable slots
    abstract xGetVersionString : unit -> unit 
    abstract xGetRuntimeDirectory : unit -> unit
    abstract xIsLoaded : unit -> unit
    abstract xIsLoadable : unit -> unit
    abstract xLoadErrorString : unit -> unit
    abstract xLoadLibrary : unit -> unit
    abstract xGetProcAddress : unit -> unit
    abstract xGetInterface : unit -> unit
    abstract xSetDefaultStartupFlags : unit -> unit
    abstract xGetDefaultStartupFlags : unit -> unit
    [<MethodImpl(MethodImplOptions.InternalCall, MethodCodeType=MethodCodeType.Runtime)>]
    abstract BindAsLegacyV2Runtime : unit -> unit

try
    let rtInfo = RuntimeEnvironment.GetRuntimeInterfaceAsObject(Guid.Empty,typeof<ICLRRuntimeInfo>.GUID) :?> ICLRRuntimeInfo            
    if (box rtInfo) <> null then
        printfn "Setting to 2.0 binding"
        rtInfo.BindAsLegacyV2Runtime()

with _ -> ()
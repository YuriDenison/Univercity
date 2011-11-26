// Copyright (c) Microsoft Corporation 2005-2008.
// This sample code is provided "as is" without warranty of any kind. 
// We disclaim all warranties, either express or implied, including the 
// warranties of merchantability and fitness for a particular purpose. 

// Game of life: worker automaton.
//
// Compute the Game of Life step-by-step and send the results to a client by 
// calling the given functions.  Also permit certain operations to 
// control the computation, e.g. to cancel it. 
 
// NOTE: This sample uses 'light' syntax.  This means whitespace
// is signficant.


module Worker

#nowarn "57"
#nowarn "40"

open System.Threading
open System.Collections.Generic
open Microsoft.FSharp.Control
open Microsoft.FSharp.Control.CommonExtensions

type msg = 
    | Run 
    | Exit
    | Pause
    | Step
    | Reset
    | UserInput of Game.points 

/// A worker automaton is a reactive automaton running on a dedicated thread of its
/// own.
type Worker(size:int) = 

    // Capture the synchronization context of the thread that creates this object. This
    // allows us to send messages back to the GUI thread painlessly.
    let callerCtxt = 
        match System.Threading.SynchronizationContext.Current with 
        | null -> null // System.ComponentModel.AsyncOperationManager.SynchronizationContext
        | x -> x
    //do if callerCtxt = null then failwith "Couldn't detect the synchronization context of the calling thread"
        
    let runInGuiCtxt f = 
        match callerCtxt with 
        | null -> 
            // callerCtxt is null on Mono, and System.Threading.SynchronizationContext.Current doesn't return a useful
            // result. System.ComponentModel.AsyncOperationManager.SynchronizationContext returns
            // an inconsistent result.
            //
            // So here we work around, where we find the open form and send to it. 
            if System.Windows.Forms.Application.OpenForms.Count > 0 then 
                System.Windows.Forms.Application.OpenForms.Item(0).BeginInvoke(new System.Windows.Forms.MethodInvoker(fun _ -> f())) |> ignore
        | _ -> callerCtxt.Post((fun _ -> f()),null)

    // This events are fired in the synchronization context of the GUI (i.e. the thread
    // that created this object)
    let updateEvent = new Event<_>() 
    let finishedEarlyEvent = new Event<_>() 

    // Updates are generated very, very quickly. So we keep a queue, and every time we push
    // into an empty queue we trigger an event in the GUI context that empties the queue and invokes 
    // the update event in the GUI context.
    let updateQueue = new Queue<_>()
    let enqueueUpdates(update) = 
        let first = 
            lock (updateQueue) (fun () -> 
                let first = (updateQueue.Count = 0)
                updateQueue.Enqueue(update); 
                first)
        if first then 
            runInGuiCtxt(fun _ -> 
                let updates = 
                    lock (updateQueue) (fun () ->  
                        [ while updateQueue.Count > 0 do 
                             yield updateQueue.Dequeue() ])
                updateEvent.Trigger(updates))


    /// Compute one step of the game and call the 
    /// NotifyUpdates callback.  That is, this function provides
    /// glue between the core computation and the computation of that algorithm
    let oneStep(s) = 
        let born,died = Game.step(s)
        enqueueUpdates(born,died);
        (s, not (born = [] && died = []))

    /// This function resets the game and calls the NotifyUpdates callback.  
    let resetGame(s) = 
        enqueueUpdates([],Game.alive(s));
        let (s,born) = Game.newRandomGame size
        enqueueUpdates(born,[]);
        s

    let userInput inp s = 
        Game.augment s inp
        enqueueUpdates(inp,[]);
        s  
            
    // The control logic is written using the 'async' non-blocking style. We could also write this
    // using a set of synchronous recursive functions, or using a synchronous workflow,
    // but it's more fun to use the asynchronous version, partly because the MailboxProcessor type gives us a 
    // free message queue.
    //
    // Wherever you see 'return!' in the code below you should interpret 
    /// that as 'go to the specified state'.
    let mailboxProcessor = 
        new MailboxProcessor<_>(fun inbox -> 
            /// This is the States of the worker's automata using a set of 
            /// tail-calling recursive functions. 
            let rec Running(s) = 
                async { let! msgOption = inbox.TryReceive(timeout=0)
                        match msgOption with 
                        | None -> return! StepThen (SleepThen Running) s
                        | Some(msg) ->
                            match msg with
                            | Pause          -> return! Paused s
                            | Step           -> return! Running s
                            | Run            -> return! Running s
                            | Reset          -> return! Running (resetGame s)
                            | UserInput(inp) -> return! Running (userInput inp s) 
                            | Exit           -> return! Finish s }

            and StepThen f s =  
                async { let s,cont = oneStep(s)
                        if cont then return! f s 
                        else return! FinishEarly(s) }
                
            and SleepThen f s = 
                async { // yield to give the GUI time to update
                        do! Async.Sleep(20);
                        // Requeue in thread pool - strictly speaking we dont have to 
                        // do this, but it ensures we reclaim stack on Mono and other 
                        // platforms that do not take tailcalls on all architectures.
                        do! Async.SwitchToThreadPool()
                        return! f(s)  }
                
            and Paused(s) = 
                async { let! msg = inbox.Receive()
                        match msg with 
                        | Pause          -> return! Paused s
                        | Step           -> return! StepThen Paused s
                        | Run            -> return! Running s
                        | Reset          -> return! Paused (resetGame s)
                        | UserInput(inp) -> return! Paused (userInput inp s)
                        | Exit           -> return! Finish s  }

            and FinishEarly(s) =  
                async { do runInGuiCtxt(fun _ -> finishedEarlyEvent.Trigger())
                        return! Paused(s)  }
                
            and Finish(s) = 
                async { return () }

            // Enter the initial state
            Running (resetGame Game.empty))
    
    /// Here is the public API to the worker
    member w.RunAsync () = mailboxProcessor.Post(Run)
    member w.StopAsync() = mailboxProcessor.Post(Pause)
    member w.ExitAsync() = mailboxProcessor.Post(Exit)
    member w.StepAsync() = mailboxProcessor.Post(Step)
    member w.ResetAsync() = mailboxProcessor.Post(Reset)
    member w.UserInputAsync inp = mailboxProcessor.Post(UserInput(inp))
    [<CLIEvent>]
    member w.Updates       = updateEvent.Publish
    [<CLIEvent>]
    member w.FinishedEarly = finishedEarlyEvent.Publish
    member w.Start()  = mailboxProcessor.Start()
        

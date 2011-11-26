open System

let numberOfComputers = 50
let mutable numberOfInfectedComputers = 2
let numberOfSteps = 100
let probWindowsInfection = 0.02
let probLinuxInfection = 0.001
let probWindows = 0.8
let probConnection = 0.75

let rand = System.Random()
let mutable numInfWindows = 0
let mutable numInfLinux = 0
let mutable numWindows = 0
let mutable numLinux = 0

type OperationSystem =
    | Linux
    | Windows

type Computer (random:double) =
    let os = if (random < probWindows) then Windows else Linux       
    let probOfInfecting = if (os = Windows) then probWindowsInfection else probLinuxInfection
    let mutable isInfected = false 
   
    member this.makeInfected = 
        isInfected <- true
    member this.getOS = os
    member this.isInf = isInfected
    member this.getProb = probOfInfecting

type Lan (rand:System.Random)=
    let mutable (computers : Computer list) = [for i in 0..numberOfComputers-1 do
                                                let comp = new Computer(rand.NextDouble())
                                                yield comp]
    let generateConnections = 
        let conn:bool array array = Array.create (numberOfComputers + 1) (Array.create (numberOfComputers + 1) false)
        for i in 0..numberOfComputers do
            for j in i+1..numberOfComputers do
                let prob = rand.NextDouble()
                conn.[i].[j] <- (prob > probConnection)
                conn.[j].[i] <- conn.[i].[j]
        conn

    let generateInfections = 
        let rec makeInf (lst : Computer list) num = 
            if num > 0 then 
                (List.head lst).makeInfected
                makeInf lst.Tail (num-1)                     
            else ()
        makeInf computers numberOfInfectedComputers 

    let rec simulate num = 
        let connections = generateConnections
        if num > 0 then 
                let mutable resultOfStep = Array.zeroCreate numberOfComputers
                for i in 0..numberOfComputers-1 do
                    if not computers.[i].isInf then
                        for j in 0..numberOfComputers-1 do
                            if (connections.[i].[j] && i <> j && computers.[j].isInf) then 
                                let pr = rand.NextDouble()
                                if pr < computers.[i].getProb then resultOfStep.[i] <- 1 
                for i in 0..numberOfComputers-1 do
                    if (resultOfStep.[i] = 1) then 
                        computers.[i].makeInfected
                        numberOfInfectedComputers <- numberOfInfectedComputers + 1
                        if (computers.[i].getOS = Windows) then 
                            numInfWindows <- numInfWindows + 1 
                        else numInfLinux <- numInfLinux + 1
                           
                printfn "Step %d: Windows Infected %d/%d, Linux Infected %d/%d" (numberOfSteps-num+1) numInfWindows numWindows numInfLinux numLinux
                simulate (num-1)        
    
    member this.generateLan = 
        generateInfections
        for i in 0..numberOfComputers-1 do
            if computers.[i].getOS = Windows then 
                numWindows <- numWindows + 1 
            else numLinux <- numLinux + 1
        simulate numberOfSteps

let ran = new System.Random()
let lan = new Lan(ran)
lan.generateLan
    
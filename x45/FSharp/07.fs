open System.ComponentModel

let mutable result = Array.zeroCreate 100
let mutable finish =  [| for i in 1 .. 100 -> false |]
let res = [for i in 1..100 ->
                                let worker = new BackgroundWorker()
                                let numIterations = 100
                                worker.DoWork.Add(fun args -> 
                                     let rec sum acc =
                                        if acc < 10000 then sum (acc + 1)
                                            else args.Result <- box acc
                                     sum 0)
                                worker.RunWorkerCompleted.Add(fun args -> 
                                    result.[i-1] <- int (string args.Result)
                                    finish.[i-1] <- true)
                                worker.RunWorkerAsync()]

while( (finish |> Array.filter (fun x -> x = false) |> Array.length) <> 0) do 
    printf "fuuuuuuuck!\n"
printf "Sum = %i\n" (Array.sum result)
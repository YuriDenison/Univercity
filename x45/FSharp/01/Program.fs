open System

let rec factorial n = if n = 1 then 1 else n * factorial (n-1)
let rec fib n = if n < 2 then 1 else fib (n-1) + fib (n-2)

printfn "Input n for factorial: "
let n1 = System.Console.ReadLine()
let n2 = int n1
printfn "(%d)! = %d" n2 (factorial n2)

printfn "Input n for Fibbonachi: "
let n3 = System.Console.ReadLine()
let n4 = int n3
printfn "Fib(%d) = %d" n4 (fib n4) 

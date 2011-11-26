open System
open System.IO

printfn "\nOlolo 1st: "
let rec checkBracketList sl list = 
    match sl with
    | h::t -> 
        match h with
        | '(' -> checkBracketList t (["("] @ list)
        | '{' -> checkBracketList t (["{"] @ list)
        | '[' -> checkBracketList t (["["] @ list)
        | ')' -> (list.Head = "(")  && (checkBracketList t list.Tail)
        | '}' -> (list.Head = "{")  && (checkBracketList t list.Tail)
        | ']' -> (list.Head = "[")  && (checkBracketList t list.Tail) 
    | [] -> list.IsEmpty
let checkBrackets str = 
    let strList = (str:String).ToCharArray() |> Array.toList     
    checkBracketList strList []

let input1 = "(({}){[][][[]()]})" 
let input2 = "({)}"
printfn " %A is correct - %A" input1 (checkBrackets input1)
printfn " %A is correct - %A" input2 (checkBrackets input2)

printf "\nOlolo 2nd: "
let func x l = List.map (fun y -> y * x) l
let func'1 x = List.map (fun y -> y * x)
let func'2 x = List.map ((*) x)
let func'3 x = List.map <| (*) x
let func'4 x = (List.map << (*)) x
let func'5 = List.map << (*)

let input = [1; 2; 3; 4; 5]
printfn " %A" (func 2 input)
printfn " %A" (func'1 2 input)
printfn " %A" (func'2 2 input)
printfn " %A" (func'3 2 input)
printfn " %A" (func'4 2 input)
printfn " %A" (func'5 2 input)

printf "\nOlolo 3rd: "

let read filePath = 
    let is = seq {
        use sr = new StreamReader (File.OpenRead(filePath))
        while not sr.EndOfStream do
            yield sr.ReadLine () }
    is |> Seq.toList |> List.filter (fun (x:string) ->  x <> "") |> List.map (fun (x:string) -> let a = x.Split(' ') in (a.[0], a.[1]))

let write filePath list = 
    let reader = new StreamWriter(File.OpenWrite(filePath))
    List.iter (fun (x,y) -> reader.WriteLine((y + " " + x + "\n"):string)) list
    reader.Close()
    0

let rec work (dataBase:List<string*string>) =
    printfn "\n 1 - add\n 2 - find by name\n 3 - find by number\n 4 - save to file\n 5 - read from file\n 0 - exit"    
    let input = System.Console.Read()
    System.Console.ReadLine() |> ignore
    match (input - int '0') with
    | 1 ->
        printfn "Input number: "
        let number = System.Console.ReadLine()
        printfn "Input name: "
        let name = System.Console.ReadLine()
        work (dataBase @ [number, name])
    | 2 ->
        printfn "Input name: "
        let name = System.Console.ReadLine()
        dataBase |> List.iter (fun (x,y) -> if (y.Equals(name)) then printf "%s " x)
        work dataBase
    | 3 -> 
        printfn "Input number: "
        let number = System.Console.ReadLine()
        dataBase |> List.iter (fun (x,y) -> if (x.Equals(number)) then printf "%s " y)
        work dataBase
    | 4 -> 
        write "input.txt" dataBase
        work dataBase
    | 5 -> 
        let input = read "input.txt"
        work (dataBase @ input)
    | 0 -> ()
    | _ -> 
        printfn "error"
        work dataBase
               
let num = ["number"]
let name = ["name"]
let dataBase = List.zip num name

work (dataBase)

printfn "\nOlolo 4th: "
type exp =
    |Number of int
    |Variable of char
    |Involution of int * exp
    |Add of exp * exp
    |Sub of exp * exp
    |Mult of exp * exp
    |Div of exp * exp

let rec derivative exp = 
    match exp with
    |Number n -> Number 0
    |Add (m, n) -> Add(derivative m, derivative n)
    |Sub (m, n) -> Sub(derivative m, derivative n)
    |Mult (m, n) -> Add( Mult(derivative m, n), Mult(derivative n, m))
    |Div (m, n) -> Div( Sub( Mult(derivative m, n), Mult(derivative n, m)), Involution(2, n))
    |Variable x -> Number 1
    |Involution (n, x) -> if (n > 1) then Mult( Mult(Number n, Involution(n - 1, x)), derivative x)
                          else Number 1

let rec simplification exp = 
    match exp with
    |Add (m, n) -> match (m, n) with
                   |(Number a, Number b) -> Number (a + b)
                   |(Number 0, n) -> simplification n
                   |(m, Number 0) -> simplification m
                   |_ -> Add (simplification m, simplification n)
    |Sub (m, n) -> match (m, n) with
                   |(Number a, Number b) -> Number (a - b)
                   |(Number 0, n) -> Mult(Number -1, simplification n)
                   |(m, Number 0) -> simplification m
                   |_ -> Sub (simplification m, simplification n)
    |Mult (m, n) -> match (m, n) with
                   |(Number a, Number b) -> Number (a * b)
                   |(Number 0, n) -> Number 0
                   |(m, Number 0) -> Number 0
                   |(Number 1, n) -> simplification n
                   |(m, Number 1) -> simplification m
                   |_ -> Mult (simplification m, simplification n)
    |Div (m, n) ->  match (m, n) with
                   |(Number a, Number b) -> Number (a / b)
                   |(Number 0, n) -> Number 0
                   |(m, Number 1) -> simplification m
                   |_ -> Div (simplification m, simplification n)
    |Involution (num, x) -> if(num = 1) then simplification x
                            else Involution (num, simplification x)
    |_ -> exp

let rec doSimple s = 
    let s1 = simplification s
    if s1 = s then s
    else doSimple s1

let rec printExpr expr result = 
    match expr with
    |Number n -> result + (string) n
    |Variable _ -> result + "x"
    |Involution (a, b)-> result + (printExpr b "") + "^(" + (string)a + ")"
    |Add (a, b)-> result + (printExpr a "") + " + " + ( printExpr b "")
    |Sub (a, b)-> result + (printExpr a "") + " - " + (printExpr b "")
    |Mult (a, b)-> result + (printExpr a "") + " * " + (printExpr b "")
    |Div (a, b)-> result + (printExpr a "") + " / " + (printExpr b "")

let expr = Add(Sub( Involution(2, Variable 'x'), Mult(Number 2, Variable 'x')), Number 1)
let s = expr |> derivative |> doSimple
printfn "exp:%A" expr
printfn "simple exp:%A" (printExpr expr "")
printfn "derivate:%A" s
printfn "simple derivate:%A" (printExpr s "")
    
printf "\nOlolo 5th: "
type Node =
    | Node of int * Node * Node
    | Leaf of int

let rec treeMap func node= 
    match node with
    |Node(num, left, right) -> Node(func(num), treeMap func left, treeMap func right)
    |Leaf n -> Leaf(func(n))
let inputTree = Node(1, Node(2, Node(3, Leaf 2, Node(1, Leaf 1, Leaf 1)), Leaf 1), Leaf 1)
let resultTree = treeMap ((*)2) inputTree
printf " %A" resultTree
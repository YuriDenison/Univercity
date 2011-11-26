open System

let input = [1; 2; 3; -4; 1; -6; 10; 12; -3]
printf "\nOlolo 1st: "

let maxNearSum list =
    let l1 = (List.tail list) @ [0]
    let l2 = List.zip list l1
    let (x, y) = l2 |> List.mapi (fun i (x,y) -> (x+y, i+1)) |> List.sortBy (fun (x,y) -> -x) |> List.head
    x

printf "%A" (maxNearSum input)

printf "\nOlolo 2nd: "
let rec elemMatch list = 
    if List.length list < 2 then false
        elif list.Head = List.head list.Tail then true
        else elemMatch list.Tail
let checkUnique list = 
    not (elemMatch (List.sort list))
printf "list is unique = %A" (checkUnique input)

printf "\nOlolo 3rd: "
let mapParity list =
    let l1 = (List.map (fun x -> (Math.Abs(x % 2))) list)
    let l2 = List.sort l1
    let l3 = (List.findIndex (fun x -> x = 1) l2)    
    l3

let filterParity list = List.filter (fun x -> x % 2 = 0) list |> List.length
let foldParity list = List.fold (fun acc x -> acc + (1 - Math.Abs(x % 2))) 0 list 

printf "\n  Map: %A" (mapParity input)
printf "\n  Filter: %A" (filterParity input)
printf "\n  Fold: %A" (foldParity input)

printf "\nOlolo 4th: "
type Node =
    | Node of int * Node * Node
    | Leaf of int

let rec treeHeight node = 
    match node with
    |Node(_, left, right) ->
        let a = treeHeight left + 1
        let b = treeHeight right + 1
        if (a > b) then a
            else b
    |Leaf _ -> 0
let inputTree = Node(1, Node(2, Node(3, Leaf 2, Node(1, Leaf 1, Leaf 1)), Leaf 1), Leaf 1)
printf "%A" (treeHeight inputTree)

printf "\nOlolo 5th: "
type Expression =
    | Number of int
    | Plus of Expression * Expression
    | Minus of Expression * Expression
    | Multiply of Expression * Expression
    | Divide of Expression * Expression

let rec count expr = 
    match expr with 
    | Number num -> num
    | Plus (x, y) -> count x + count y
    | Minus (x, y) -> count x - count y
    | Multiply (x, y) -> count x * count y
    | Divide (x, y) -> count x / count y
printf "%A" (count (Plus (Divide (Number 6, Minus (Number 3, Number 1)), Multiply (Number 2, Number 3))))

printf "\nOlolo 6th: "
let checkSimple n =
    let rec iter i =
        i > n / 2 || (n % i <> 0) && iter (i + 1)
    iter 2
let primes = Seq.filter (fun x -> checkSimple x) (Seq.initInfinite (fun x -> x + 1))

printfn "%A" primes
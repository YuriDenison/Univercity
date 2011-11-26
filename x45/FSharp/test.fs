printfn "\nOlolo: 1st: "
let Compare a b = 
    if a > b then b
             else a
let lst = [1; 2; 3; -4; 5]
let findMin =
    printfn "%A" (List.fold(fun acc x -> Compare acc x) lst.Head lst.Tail)

printfn "\nOlolo: 2nd: "
type Node<'a> =
    | Node of 'a * Node<'a> * Node<'a>
    | Leaf of 'a

let rec filter (f: 'a -> bool) node = 
    match node with
    |Node(num, left, right) -> if f num then num :: filter f left @ filter f right
                                        else filter f left @ filter f right
    |Leaf n -> if f n then n :: []
               else []

let tree = Node(15, Node(32, Node(2, Leaf 15, Leaf 3), Leaf 13), Leaf -4)
let tree1 = filter (fun x -> x < 10) tree
printfn "%A" tree
printfn "%A" tree1

printfn "\nOlolo: 3rd: "
let rec minSin lst res size= 
    match lst with
    |h::tl -> 
        let cur = (res*size + (System.Math.Sin h))/(size+1.0)
        minSin tl cur (size+1.0)
    |[] -> res

let lst1 = [1.0; 2.0; 3.0]
printfn "%A" (minSin lst1 0.0 0.0)

printfn "\nOlolo: 4rd: "
type Stack() =
    let mutable list = []

    member this.push (value:int) =
        list <- value :: list
    
    member this.pop =
        if list.Length = 0 then failwith("fuck away!!!")
        let res = List.head list 
        list <- List.tail list
        res

    member this.isEmpty = 
        list.Length = 0

    member this.printAll = 
        printfn "%A" list 

let st = new Stack()
st.push 1
st.push 2
st.push 3
printfn "%A" st.pop
st.printAll
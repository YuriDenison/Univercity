
type Node =
    |Node of int * Node * Node
    |Empty

let rec add value node =
    match node with
    |Empty -> Node(value, Empty, Empty)
    |Node(a, Empty, Empty) ->
        if a <= value then Node(a, Node(value, Empty, Empty), Empty)
        else Node(a, Empty, Node(value, Empty, Empty))
    |Node(a, left, right) -> 
        if value > a then Node(a, left, add value right)
        else Node(a, add value left, right)

let rec findMaxValue node =
    match node with
    |Node(a, left, Empty) -> a
    |Node(_, left, right) -> findMaxValue right
    |_ -> failwith "ololo"

let rec remove value node =
    match node with
    |Empty -> 
        printfn "not found"
        Empty
    |Node(a, Empty, right) -> 
        if a = value then right
        else Node(a, Empty, remove value right)
    |Node(a, left, Empty) -> 
        if a = value then left
        else Node(a, remove value left, Empty)
    |Node(a, left, right) -> 
        if value > a then Node(a, left, remove value right)
        elif value < a then Node(a, remove value left, right)
        else 
            let maxValue = findMaxValue left
            Node(maxValue, remove maxValue left, right)
                                      
let rec contains value node =
    match node with
    |Empty -> false
    |Node(a, left, right) -> (a = value) || contains value left || contains value right

let rec toList node =
    match node with
    |Empty -> []
    |Node(a, left, right) -> a :: toList left @ toList right

let rec work node =
    printfn "\nHelp:\n 1 - add to List\n 2 - remove from List\n 3 - print Tree\n 4 - print List\n 0 - exit "
    let input = System.Console.Read()
    System.Console.ReadLine() |> ignore
    match (input - int '0') with
    |1 -> 
        printf "\nInput elem to add: "
        let newElem = System.Console.Read()
        System.Console.ReadLine() |> ignore
        work ( node |> add (newElem - 48) )
    |2 -> 
        printf"\nInput elem for remove: "
        let elementToDelete = System.Console.Read()
        System.Console.ReadLine() |> ignore
        work (node |> remove (elementToDelete - 48) )
    |3 -> 
        printfn"\n Tree:\n%A" node
        work node
    |4 -> 
        printfn"%A" (toList node)
        work node
    |0 -> 
        Empty
    |_ -> 
        printfn "NOOOOOOO!!!11"
        work node
            
work Empty |> ignore
open System

let rec printList lst =
    match lst with 
    | [] -> printf("\n")
    | h :: t -> 
        printf "%d " h
        printList t

let rec swapList xs = 
    match xs with
    | []    -> []
    | h :: t -> (swapList t) @ [h]
        

let rec powerOfTwo n = 
    if n = 0 then 1
    else 2 * powerOfTwo (n-1)
let formPowerOfTwoList n = [for i in [n..(n+4)] -> powerOfTwo i] 
        
let rec multiplicationOfDigits n = 
    if n / 10 = 0 then n
    else (n % 10) * (multiplicationOfDigits (n/10))

let rec findDigitRec n lst = 
    match lst with
    | [] -> None
    | h :: t -> 
        if h = n then Some(lst.Length)
        else findDigitRec n t 

let findDigit n lst = 
    match findDigitRec n lst with
    | None -> printf "No %d in list\n" n
    | Some(num) -> printf "Place num of (%d) = %d\n" n (lst.Length - num + 1)

let checkPalindrom s = (List.ofSeq s = swapList (List.ofSeq s))

// #1
let lst = [1; 2; 3; 4; 5]
printList lst
printList (swapList lst)

// #2
printList (formPowerOfTwoList 4)

// #3
printf "%d\n" (multiplicationOfDigits 12345)

// #4
findDigit 10 [1; 2; 3; 4; 5; 10; 1; 2; 3; 4; 5]
findDigit 1 [2; 4; 5]

// #5
printf "%b\n" (checkPalindrom "fuck")
printf "%b\n" (checkPalindrom "fuf")
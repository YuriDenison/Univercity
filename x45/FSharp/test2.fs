let rec printChar (a:string) n = 
    if n > 0 then 
        printf "%s" a
        printChar a (n-1)

let rec printUpLines k n =
    if n > 1 then     
        printChar " " (n-1)     
        printChar "*" k
        printf "\n"
        printUpLines (k+2) (n-1)

let rec printDownLines k n =
    if k > 0 then     
        printChar " " (n-1)     
        printChar "*" k
        printf "\n"
        printDownLines (k-2) (n+1)

let printRhombus n = 
    printUpLines 1 n
    printDownLines (2*n-1) 1 

printRhombus 8
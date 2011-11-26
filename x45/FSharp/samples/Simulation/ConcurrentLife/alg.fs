// Copyright (c) Microsoft Corporation 2005-2008.
// This sample code is provided "as is" without warranty of any kind. 
// We disclaim all warranties, either express or implied, including the 
// warranties of merchantability and fitness for a particular purpose. 

// Game of life: the algorithm (imperative implementation)

// NOTE: This sample uses 'light' syntax.  This means whitespace
// is signficant.



module Game

type point = int * int
type points = point list

let prev sz i = if i = 0 then sz-1 else i-1 
let next sz i = if i = sz-1 then 0 else i+1
let neighbours sz (i,j) =
    [ (prev sz i,prev sz j); (prev sz i,j); (prev sz i,next sz j);
      (i,        prev sz j);                (i,        next sz j);
      (next sz i,prev sz j); (next sz i,j); (next sz i,next sz j)]

type cell =
    { x: int
      y: int
      mutable neighbours:  cell list
      mutable count: int  
      mutable alive: bool }

let dummyCell = 
    { x=0
      y=0 
      neighbours= [ ]
      count=0
      alive=false } 

type GameGrid = 
    { size: int; 
      arr: cell[,]; 
      mutable living: point list }
    // Defining the 'Item' property lets us use grid.[i,j] syntax
    member grid.Item 
        with get(p)   = let (x,y) = p in grid.arr.[x,y]
        and  set(p) v = let (x,y) = p in grid.arr.[x,y] <- v

    member grid.Neighbours(p) = 
       neighbours grid.size p
       |> List.map (fun p -> grid.[p])  

let empty = 
    { size=0; 
      arr = Array2D.create 0 0 dummyCell; 
      living=[] }

let makeGrid size = 
    // First make all the cells 
    let grid = 
        { size = size
          arr = Array2D.init size size (fun i j -> { x=i;y=j; neighbours= [ ]; count=0; alive=false; })
          living=[] } 
    // Now record the neighbours of each cell using direct references
    // to the neighbouring cell objects stored in the array.  This is
    // an imperative implementation of the Game of Life
    for i = 0 to size - 1 do
        for j = 0 to size - 1 do 
            let p = (i,j)
            grid.[p].neighbours <- grid.Neighbours(p)
    grid

let union l1 l2 = 
    List.fold (fun l x -> if l |> List.exists (fun y -> y = x) then l else x::l) l1 l2

let augment (grid : GameGrid) living =
    // Set each cell the list to "live" 
    for p in living do
        grid.[p].alive <- true
    grid.living <- union grid.living living

let (--) f x = f x

let step grid =
    // For each living cell or neighbour of a living cell, set the count to 0 
    for p in grid.living do
        grid.[p].count <- 0
        for nb in grid.[p].neighbours do
            nb.count <- 0
        
    // Now compute the number of neighbours for these cells 
    for p in grid.living do
        for nb in grid.[p].neighbours do 
            nb.count <- nb.count + 1
        
    // Now compute which cells live/die 
    let born = 
        [ for p in grid.living do 
             for nb in grid.[p].neighbours do 
                  if (not nb.alive) && (nb.count = 3) then
                     nb.alive <- true
                     yield (nb.x,nb.y) ]

    let alive = 
        [ yield! born
          // calculate survivors
          for p in grid.living do 
            let c1 = grid.[p]
            if c1.count = 2 || c1.count = 3 then 
                yield (c1.x,c1.y) ]
    let died = 
        [ for p in grid.living do 
            let c1 = grid.[p]
            if c1.count <> 2 && c1.count <> 3 then 
                yield (c1.x,c1.y) ]
       
    for p in died do 
        grid.[p].alive <- false

    grid.living <- alive
    born, died

let alive grid = grid.living


let newRandomGame size = 
    let grid = makeGrid size 
    let born = 
        let gen = new System.Random()
        let density = 0.05 + (gen.NextDouble() * 0.1)
        [ for i = 0 to size-1 do
             for j = 0 to size-1 do
                if gen.NextDouble() < density then 
                    yield (i,j) ]
    augment grid born
    grid, born

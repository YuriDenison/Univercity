// Copyright (c) Microsoft Corporation 2005-2008.
// This sample code is provided "as is" without warranty of any kind. 
// We disclaim all warranties, either express or implied, including the 
// warranties of merchantability and fitness for a particular purpose. 

// NOTE: This sample uses 'light' syntax.  This means whitespace
// is signficant.


module Game

type point = int * int
type points = point list

type GameGrid

val empty: GameGrid
val newRandomGame: int -> GameGrid * points
val step: GameGrid -> points * points
val augment: GameGrid -> points -> unit
val alive: GameGrid -> points


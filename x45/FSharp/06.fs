type Builder(n:int) =
    member this.Bind ((x : float), (rest : float -> float)) =
        rest(System.Math.Round(x, n))

    member this.Return (x : float) = System.Math.Round(x, n)

let roundComputation =
    (new Builder(3)) {
    let! a = 2.0 / 12.0
    let! b = 3.5
    return a / b
    }

printfn "%A" roundComputation
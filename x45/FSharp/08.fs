open System.Net
open System.IO
open System.Text.RegularExpressions

let pageReadAsync(url:string) =
    async { 
        let req = WebRequest.Create(url)
        let! resp = req.AsyncGetResponse()
        let reader = new StreamReader(resp.GetResponseStream())
        let page = reader.ReadToEnd()  
        do printfn "Read %s | len = %d" url page.Length
        }

let getLinks (url:string) = 
    let request = WebRequest.Create(url)
    let response = request.GetResponse()
    let reader = new StreamReader(response.GetResponseStream())
    let page = reader.ReadToEnd()
    let expr = Regex.Matches(page, "a href=\"http://[^\"]*\"")
    [for m in expr -> let l = m.Groups.[0].Value  
                      l.Substring(8, l.Length - 9)]

let print (url:string) =
    let links = getLinks url
    let pages = [for l in links -> pageReadAsync l]
    Async.Parallel pages |> Async.RunSynchronously |> ignore
        
print "http://google.com"
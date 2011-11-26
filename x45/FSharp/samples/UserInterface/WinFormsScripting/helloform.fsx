
// Copyright (c) Microsoft Corporation 2005-2006.
// This sample code is provided "as is" without warranty of any kind. 
// We disclaim all warranties, either express or implied, including the 
// warranties of merchantability and fitness for a particular purpose. 


open System
open System.Windows.Forms

let form = new Form()
form.Width  <- 400
form.Height <- 300
form.Visible <- true 
form.Text <- "Hello World Form"

// Menu bar, menus 
let mMain = form.Menu <- new MainMenu()
let mFile = form.Menu.MenuItems.Add("&File")
let miQuit  = new MenuItem("&Quit")
mFile.MenuItems.Add(miQuit)

// RichTextView 
let textB = new RichTextBox()
textB.Dock <- DockStyle.Fill  
textB.Text <- "Hello World\n\nOK."
form.Controls.Add(textB)    

// callbacks 
miQuit.Click.Add(fun _ -> form.Close())

#if COMPILED
// Run the main code. The attribute marks the startup application thread as "Single 
// Thread Apartment" mode, which is necessary for GUI applications. 
[<STAThread>]    
do Application.Run(form)
#endif

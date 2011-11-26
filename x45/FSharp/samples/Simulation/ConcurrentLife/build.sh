#!/bin/sh

mono ../../../bin/fscp10ntc.exe --no-warn 40 -o life.exe alg.fsi alg.fs worker.fs client.fs
chmod u+x life.exe

echo Built ok, you may now run life.exe using 
echo   mono life.exe




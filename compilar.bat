@echo off
setlocal

:: Set paths
set LIB=lib/*
set SRC=src
set OUT=build

:: Create build dir if it doesn't exist
if not exist %OUT% mkdir %OUT%

:: Compile
echo Compilando...
javac -cp "%LIB%" -d %OUT% %SRC%/*.java

echo Compilacao finalizada.
endlocal
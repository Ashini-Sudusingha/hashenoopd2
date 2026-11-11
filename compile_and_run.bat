@echo off
title SmartCity360 - Console Demonstration (Hashen Liyanaarachchi)
cls
echo ==============================================================================
echo Compiling all Java Source Files...
echo Student: Hashen Liyanaarachchi (JIAT/OODP II /EX/01)
echo ==============================================================================
if not exist bin mkdir bin
javac -cp "lib\*" -d bin src\com\smartcity360\partA_decorator\*.java src\com\smartcity360\partB_composite\*.java src\com\smartcity360\partC_chainofresponsibility\*.java src\com\smartcity360\partD_flyweight\*.java src\com\smartcity360\partE_interpreter\*.java src\com\smartcity360\controller\*.java src\com\smartcity360\web\*.java src\com\smartcity360\main\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo BUILD SUCCESSFUL! Launching Console Application...
    echo ==============================================================================
    java -cp "bin;lib\*" com.smartcity360.main.SmartCity360App
) else (
    echo.
    echo BUILD FAILED with errors!
)
echo.
pause

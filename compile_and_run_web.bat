@echo off
title SmartCity360 Web Dashboard - Hashen Liyanaarachchi
cls
echo ==============================================================================
echo   SMARTCITY360 - ENTERPRISE URBAN PLATFORM (WEB & MVC ARCHITECTURE)
echo   Student: Hashen Liyanaarachchi (JIAT/OODP II /EX/01)
echo   Java Institute for Advanced Technology - Department of Examinations
echo ==============================================================================
echo.
echo [1/3] Creating output directory...
if not exist bin mkdir bin

echo [2/3] Compiling all Java classes and Servlets...
javac -cp "lib\*" -d bin src\com\smartcity360\partA_decorator\*.java src\com\smartcity360\partB_composite\*.java src\com\smartcity360\partC_chainofresponsibility\*.java src\com\smartcity360\partD_flyweight\*.java src\com\smartcity360\partE_interpreter\*.java src\com\smartcity360\controller\*.java src\com\smartcity360\web\*.java src\com\smartcity360\main\*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [3/3] BUILD SUCCESSFUL! Launching Embedded MetroWebServer...
    echo ------------------------------------------------------------------------------
    echo Opening Web Browser at: http://localhost:8081
    echo ------------------------------------------------------------------------------
    java -cp "bin;lib\*" com.smartcity360.web.MetroWebServer
) else (
    echo.
    echo [ERROR] Compilation failed with errors!
)

echo.
pause

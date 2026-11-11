@echo off
title SmartCity360 - Web Operations Launcher (Hashen Liyanaarachchi)
cls
echo ==============================================================================
echo Launching SmartCity360 Metro Web Dashboard & Server...
echo Student: Hashen Liyanaarachchi (JIAT/OODP II /EX/01)
echo Port: 8081 (http://localhost:8081)
echo ==============================================================================
java -cp "bin;lib\*" com.smartcity360.web.MetroWebServer
echo.
pause

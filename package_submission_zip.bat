@echo off
title SmartCity360 - Package Submission ZIP (Hashen Liyanaarachchi)
cls
echo ==============================================================================
echo Creating Project Source Code Submission ZIP...
echo Student: Hashen Liyanaarachchi (JIAT/OODP II /EX/01)
echo ==============================================================================
echo.
powershell -Command "Compress-Archive -Path 'src', 'web', 'lib', '*.bat', '*.md' -DestinationPath '..\SmartCity360_HashenLiyanaarachchi_Submission.zip' -Force"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ZIP Created Successfully!
    echo Location: ..\SmartCity360_HashenLiyanaarachchi_Submission.zip
) else (
    echo.
    echo Failed to create ZIP archive.
)
echo.
pause

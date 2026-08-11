@echo off
setlocal
cd /d "%~dp0"

if not exist "target\sistema-almacen-itla.jar" (
    echo El programa aun no esta compilado.
    echo Ejecuta primero compilar_y_ejecutar.bat
    pause
    exit /b 1
)

java -jar target\sistema-almacen-itla.jar
if errorlevel 1 pause

endlocal

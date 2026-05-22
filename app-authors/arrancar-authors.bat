@echo off
setlocal enabledelayedexpansion
title Levantar Instancias Quarkus - App Authors

echo ==================================================
echo   CONTROL DE INSTANCIAS QUARKUS: APP-AUTHORS
echo ==================================================
echo.

:: 1. Preguntar por el puerto base (Por defecto 8070)
set "PUERTO_BASE=8070"
set /p PUERTO_INPUT="1. Introduce el PUERTO BASE [Por defecto 8070]: "
if not "%PUERTO_INPUT%"=="" set "PUERTO_BASE=%PUERTO_INPUT%"

:: 2. Preguntar cuántas ventanas nuevas quiere abrir
echo.
set /p INSTANCIAS_NUEVAS="2. Cuantas INSTANCIAS NUEVAS deseas levantar?: "

:: 3. Preguntar cuántas ya están corriendo
echo.
set /p INSTANCIAS_EXISTENTES="3. Cuantas instancias tienes YA LEVANTADAS actualmente?: "

echo.
echo ==================================================
echo Calculando puertos y levantando servicios de Autores...
echo ==================================================

set /a TOTAL_BUCLE=%INSTANCIAS_NUEVAS% - 1

for /L %%i in (0, 1, %TOTAL_BUCLE%) do (
    set /a PUERTO_ACTUAL=%PUERTO_BASE% + %INSTANCIAS_EXISTENTES% + %%i

    echo Levantando nueva instancia de Authors en el puerto !PUERTO_ACTUAL!...

    start "Authors - Puerto !PUERTO_ACTUAL!" java -Dquarkus.http.port=!PUERTO_ACTUAL! -jar build/quarkus-app/quarkus-run.jar
)

echo.
echo [OK] ¡Proceso terminado para app-authors!
echo.
pause
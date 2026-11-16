@echo off
REM Script para reiniciar el backend Spring Boot correctamente

echo.
echo ============================================
echo  REINICIANDO BACKEND SPRING BOOT
echo ============================================
echo.

REM Matar cualquier proceso Java anterior en puerto 8081
echo [1/3] Deteniendo instancias anteriores...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8081.*LISTENING"') do (
    echo   Matando PID %%a...
    taskkill /PID %%a /F /T >nul 2>&1
)
timeout /t 3 /nobreak

REM Compilar
echo.
echo [2/3] Compilando...
call mvnw.cmd clean compile -q
if errorlevel 1 (
    echo ERROR en compilacion
    pause
    exit /b 1
)

REM Iniciar
echo.
echo [3/3] Iniciando servidor...
echo.
call mvnw.cmd spring-boot:run

pause

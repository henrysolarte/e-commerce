@echo off
setlocal enabledelayedexpansion

:: Script para limpiar la tabla users de la base de datos ecommerce
:: Uso: cleanup-users.bat

set PSQL_PATH="C:\Program Files\PostgreSQL\17\bin\psql.exe"
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=ecommerce
set DB_USER=postgres
set DB_PASSWORD=postgres

echo ===== LIMPIANDO TABLA USERS =====
echo Host: !DB_HOST!:!DB_PORT!
echo Base de datos: !DB_NAME!
echo Usuario: !DB_USER!
echo.

:: Limpiar la tabla
%PSQL_PATH% -h !DB_HOST! -p !DB_PORT! -U !DB_USER! -d !DB_NAME! -c "DELETE FROM users WHERE 1=1; ALTER SEQUENCE IF EXISTS users_id_seq RESTART WITH 1;" 2>&1

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Limpieza completada exitosamente
) else (
    echo.
    echo ✗ Error durante la limpieza
    pause
    exit /b 1
)

pause

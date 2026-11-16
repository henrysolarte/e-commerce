@echo off
REM Script para verificar conectividad con PostgreSQL
REM y crear la base de datos si no existe

echo ========================================
echo Verificador de Conectividad - PostgreSQL
echo ========================================
echo.

REM Verificar si PostgreSQL está instalado
echo [1] Verificando si PostgreSQL está disponible...
psql --version >nul 2>&1
if errorlevel 1 (
    echo ❌ ERROR: psql no está instalado o no está en el PATH
    echo Instala PostgreSQL o agrega su bin al PATH
    pause
    exit /b 1
)
echo ✅ PostgreSQL encontrado

REM Intentar conectar a la BD
echo.
echo [2] Intentando conectar a la base de datos...
echo.

REM PGPASSWORD permite pasar la contraseña sin prompt
set PGPASSWORD=postgres

psql -h localhost -U postgres -d ecommerce -c "SELECT version();" >nul 2>&1
if errorlevel 1 (
    echo ⚠️  La base de datos 'ecommerce' no existe o no se pudo conectar
    echo.
    echo [3] Intentando crear la base de datos...
    
    REM Intentar crear la BD
    psql -h localhost -U postgres -c "CREATE DATABASE ecommerce;" 2>&1
    
    if errorlevel 1 (
        echo ❌ No se pudo crear la base de datos
        echo Verifica que PostgreSQL esté corriendo y que las credenciales sean correctas
        pause
        exit /b 1
    )
    echo ✅ Base de datos 'ecommerce' creada exitosamente
) else (
    echo ✅ Conexión exitosa a la base de datos 'ecommerce'
)

echo.
echo [4] Información de la versión de PostgreSQL:
psql -h localhost -U postgres -d ecommerce -c "SELECT version();"

echo.
echo ========================================
echo ✅ Verificación completada
echo ========================================
echo.
echo El backend puede iniciar ahora. Ejecuta:
echo   run-backend.bat
echo.
pause

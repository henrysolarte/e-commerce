@echo off
REM Script para iniciar el backend de E-Commerce
REM Asegúrate de que PostgreSQL esté corriendo en localhost:5432
REM Usuario: postgres, Contraseña: postgres

echo ========================================
echo Iniciando Backend E-Commerce
echo ========================================
echo.
echo Configuración:
echo - Base de datos: postgresql
echo - Host: localhost
echo - Puerto: 5432
echo - Base de datos: ecommerce
echo - Usuario: postgres
echo.
echo Verificando que la base de datos exista...
echo.

REM Iniciar el servidor
cd /d "%~dp0"
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar

echo.
echo Backend iniciado. Accede a:
echo - URL Base: http://localhost:8081
echo - API Docs (Swagger): http://localhost:8081/swagger-ui.html
echo.
pause

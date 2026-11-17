@echo off
REM Test Render deployment
REM Usage: test-render-deployment.bat <render_url>

setlocal enabledelayedexpansion

if "%1"=="" (
    echo Usage: test-render-deployment.bat ^<render_url^>
    echo Example: test-render-deployment.bat https://ecommerce-prod-7m9n.onrender.com
    exit /b 1
)

set "RENDER_URL=%1"

echo Testing Render Deployment: %RENDER_URL%
echo.

REM Test 1: Health check
echo [1/4] Testing health/status endpoint...
curl -s -w "\nHTTP Status: %%{http_code}\n" "%RENDER_URL%/api/health" || echo Failed to connect

REM Test 2: Swagger docs
echo.
echo [2/4] Testing Swagger documentation...
curl -s -w "\nHTTP Status: %%{http_code}\n" "%RENDER_URL%/swagger-ui.html" | head -1 || echo Failed to connect

REM Test 3: Products endpoint
echo.
echo [3/4] Testing products endpoint...
curl -s -w "\nHTTP Status: %%{http_code}\n" "%RENDER_URL%/api/products" || echo Failed to connect

REM Test 4: Auth endpoint
echo.
echo [4/4] Testing login endpoint...
curl -s -X POST "%RENDER_URL%/api/auth/login" ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"test\",\"password\":\"test\"}" ^
  -w "\nHTTP Status: %%{http_code}\n" || echo Failed to connect

echo.
echo Testing complete!

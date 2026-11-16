#!/bin/bash
# Script para iniciar el backend en sistemas Unix/Linux/Mac
# Para Windows, usa run-backend.bat

set -e

echo "========================================"
echo "Iniciando Backend E-Commerce"
echo "========================================"
echo ""
echo "Configuración:"
echo "- Base de datos: postgresql"
echo "- Host: localhost"
echo "- Puerto: 5432"
echo "- Base de datos: ecommerce"
echo "- Usuario: postgres"
echo ""

# Navegar al directorio del backend
cd "$(dirname "$0")"

# Verificar que el JAR existe
if [ ! -f "target/ecommerce-0.0.1-SNAPSHOT.jar" ]; then
    echo "❌ JAR no encontrado. Compilando..."
    ./mvnw clean package -DskipTests
fi

echo ""
echo "Iniciando servidor..."
echo ""

# Iniciar el backend
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar

echo ""
echo "Backend iniciado. Accede a:"
echo "- URL Base: http://localhost:8081"
echo "- API Docs (Swagger): http://localhost:8081/swagger-ui.html"
echo ""

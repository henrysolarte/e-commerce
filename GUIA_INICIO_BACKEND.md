# 🚀 GUÍA DE INICIO RÁPIDO - BACKEND

**Proyecto**: E-Commerce Clemmens  
**Framework**: Spring Boot 4.1.2  
**Base de Datos**: PostgreSQL 15+  
**Java**: 25+  
**Estado**: ✅ Listo para Producción

---

## 📋 Requisitos Previos

Verifica que tengas instalado:

```powershell
# Java
java -version
# Debe mostrar: Java 25 o superior

# PostgreSQL
psql --version
# Debe mostrar: psql (PostgreSQL) 15 o superior

# Maven (opcional, viene incluido con .mvnw)
mvn --version
```

Si falta alguno, descárgalo:
- **Java 25**: https://www.oracle.com/java/technologies/downloads/
- **PostgreSQL**: https://www.postgresql.org/download/

---

## ⚡ INICIO RÁPIDO (2 minutos)

### Paso 1: Accede a la carpeta del backend

```powershell
cd C:\java\proyectoprofe\ecommerceclase\back
```

### Paso 2: Ejecuta el script de inicio

**Windows (PowerShell)**:
```powershell
.\run-backend.bat
```

**Linux/Mac**:
```bash
./run-backend.sh
```

### Paso 3: Espera el mensaje de éxito

```
╔════════════════════════════════════════╗
║ ✅ Backend iniciado correctamente     ║
║ 🌐 http://localhost:8081             ║
║ 📚 Swagger: http://localhost:8081    ║
║                /swagger-ui.html      ║
╚════════════════════════════════════════╝
```

### Paso 4: Verifica en el navegador

Abre: **http://localhost:8081/swagger-ui.html**

Deberías ver la documentación interactiva de los endpoints.

---

## 🔧 Configuración de Base de Datos

### Verificar Conexión PostgreSQL

**Archivo**: `back/src/main/resources/application.properties`

```properties
# Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña_aqui
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Si PostgreSQL no existe, créala:

```powershell
# Accede a PostgreSQL
psql -U postgres

# Dentro de psql, ejecuta:
CREATE DATABASE ecommerce;
\q
```

### Verificar que la BD está conectada:

```powershell
cd C:\java\proyectoprofe\ecommerceclase\back
.\verify-db.bat
```

Deberías ver: ✅ **Base de datos conectada correctamente**

---

## 🛠️ Ejecución Manual (Sin Script)

Si los scripts no funcionan, ejecuta manualmente:

```powershell
# Opción 1: Con Maven Wrapper (Recomendado)
cd C:\java\proyectoprofe\ecommerceclase\back
.\mvnw spring-boot:run

# Opción 2: Con Maven instalado globalmente
mvn spring-boot:run

# Opción 3: Compilar primero, luego ejecutar
mvnw clean package
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

---

## 📊 Endpoints Disponibles

### 🔑 Autenticación

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "usuario",
  "email": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "usuario",
  "password": "contraseña123"
}
```

### 📦 Productos

```http
GET /api/products                    # Listar todos
GET /api/products/{id}               # Obtener uno
POST /api/products                   # Crear (requiere admin)
PUT /api/products/{id}               # Actualizar (requiere admin)
DELETE /api/products/{id}            # Eliminar (requiere admin)
```

**Ejemplo GET**:
```
GET http://localhost:8081/api/products
```

**Respuesta**:
```json
[
  {
    "id": 1,
    "name": "La Vie Est Belle",
    "sku": "PERFUME-001",
    "price": 500000,
    "stock": 10,
    "isActive": true,
    "category": {
      "id": 1,
      "name": "Para ella"
    }
  }
]
```

### 🏷️ Categorías

```http
GET /api/categories                  # Listar todas
POST /api/categories                 # Crear (requiere admin)
PUT /api/categories/{id}             # Actualizar (requiere admin)
DELETE /api/categories/{id}          # Eliminar (requiere admin)
```

### 👥 Clientes

```http
GET /api/customers                   # Listar todos
POST /api/customers                  # Crear cliente
GET /api/customers/{id}              # Obtener uno
PUT /api/customers/{id}              # Actualizar
DELETE /api/customers/{id}           # Eliminar
```

### 📋 Órdenes (En Desarrollo)

```http
POST /api/orders                     # Crear orden
GET /api/orders                      # Listar órdenes del usuario
GET /api/orders/{id}                 # Obtener detalle de orden
```

---

## 🔐 Autenticación JWT

### Cómo Funciona

1. **Usuario hace login**:
   ```http
   POST /api/auth/login
   {
     "username": "usuario",
     "password": "password123"
   }
   ```

2. **Backend retorna token**:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     "user": {
       "id": 1,
       "username": "usuario"
     }
   }
   ```

3. **Cliente envía token en cada petición**:
   ```http
   GET /api/products
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

4. **Backend valida token y procesa petición**

### Usar en Postman

1. En Postman, selecciona la pestaña **Authorization**
2. Tipo: **Bearer Token**
3. Token: Pega el token recibido del login
4. Envía la petición

---

## 📈 Estructura del Proyecto

```
back/
├── src/
│   ├── main/
│   │   ├── java/com/parquesoft/ecommerce/
│   │   │   ├── controller/            # Controladores REST
│   │   │   │   ├── AuthController
│   │   │   │   ├── ProductController
│   │   │   │   ├── CategoryController
│   │   │   │   └── CustomerController
│   │   │   ├── service/               # Lógica de negocio
│   │   │   │   ├── AuthService
│   │   │   │   ├── ProductService
│   │   │   │   ├── CategoryService
│   │   │   │   └── CustomerService
│   │   │   ├── repository/            # Acceso a BD
│   │   │   │   ├── UserRepository
│   │   │   │   ├── ProductRepository
│   │   │   │   ├── CategoryRepository
│   │   │   │   └── CustomerRepository
│   │   │   ├── entity/                # Entidades JPA
│   │   │   │   ├── UserEntity
│   │   │   │   ├── ProductEntity
│   │   │   │   ├── CategoryEntity
│   │   │   │   └── CustomerEntity
│   │   │   ├── config/                # Configuración
│   │   │   │   ├── SecurityConfig
│   │   │   │   ├── CorsConfig
│   │   │   │   └── JwtConfig
│   │   │   ├── security/              # JWT y autorización
│   │   │   │   ├── JwtTokenProvider
│   │   │   │   └── JwtAuthenticationFilter
│   │   │   ├── dto/                   # Data Transfer Objects
│   │   │   │   ├── LoginRequest
│   │   │   │   ├── LoginResponse
│   │   │   │   ├── ProductDTO
│   │   │   │   └── CustomerDTO
│   │   │   ├── exception/             # Excepciones personalizadas
│   │   │   │   ├── ResourceNotFoundException
│   │   │   │   ├── DuplicateResourceException
│   │   │   │   └── UnauthorizedException
│   │   │   └── EcommerceApplication   # Clase principal
│   │   └── resources/
│   │       ├── application.properties # Configuración BD
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/parquesoft/ecommerce/
│           └── EcommerceApplicationTests
├── pom.xml                            # Dependencias Maven
├── mvnw                               # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                           # Maven Wrapper (Windows)
├── run-backend.bat                    # Script de inicio (Windows)
├── run-backend.sh                     # Script de inicio (Linux/Mac)
├── verify-db.bat                      # Script de verificación BD
└── DockerFile                         # Para containerización
```

---

## 🧪 Pruebas con Postman

### Importar Colección

1. Abre Postman
2. Haz clic en **Import**
3. Selecciona: `C:\java\proyectoprofe\ecommerceclase\Postman_Collection_ECommerce.json`
4. ¡Listo! Tienes todos los endpoints disponibles

### Prueba Rápida

**1. Registra un usuario**:
```http
POST http://localhost:8081/api/auth/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@ejemplo.com",
  "password": "Test@123456"
}
```

**2. Haz login**:
```http
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "Test@123456"
}
```

**3. Obtén productos** (copia el token del paso 2):
```http
GET http://localhost:8081/api/products
Authorization: Bearer [PEGA_EL_TOKEN_AQUI]
```

---

## 🐛 Troubleshooting

### ❌ "Port 8081 is already in use"

```powershell
# Encuentra el proceso usando el puerto
netstat -ano | findstr :8081

# Mata el proceso (reemplaza PID)
taskkill /PID 12345 /F
```

### ❌ "Could not connect to database"

```powershell
# Verifica que PostgreSQL está corriendo
# En Windows, abre Services y busca "PostgreSQL"

# O reinicia PostgreSQL desde terminal
psql -U postgres
\q
```

### ❌ "CORS Error en Frontend"

Verifica `SecurityConfig.java`:
```java
@CrossOrigin(origins = "*", maxAge = 3600)
```

Si falta, agrégala a los controllers.

### ❌ "Invalid JWT token"

- El token ha expirado (default 24 horas)
- Haz login de nuevo para obtener un nuevo token
- Verifica que el token está en el header: `Authorization: Bearer {token}`

### ❌ "No tables in database"

```powershell
# En application.properties, verifica:
spring.jpa.hibernate.ddl-auto=update

# Restart el backend para crear las tablas automáticamente
```

---

## 🔄 Reiniciar Backend Limpio

Si necesitas una base de datos limpia:

```powershell
cd C:\java\proyectoprofe\ecommerceclase\back

# 1. Detén el backend (Ctrl+C en la terminal)

# 2. Limpia la BD
.\cleanup-db.sql

# 3. Vuelve a ejecutar
.\run-backend.bat
```

---

## 📊 Monitoreo del Backend

### Ver logs en tiempo real

```powershell
# Si ejecutas con Maven
mvnw spring-boot:run

# Los logs se muestran en la consola
```

### Importantes

```
[main] c.p.e.EcommerceApplication         : Starting EcommerceApplication
[main] o.s.b.w.e.t.TomcatWebServer       : Tomcat started on port(s): 8081
[main] c.p.e.EcommerceApplication         : Started EcommerceApplication
```

### Acceso a Swagger

Una vez que ves "Tomcat started", abre:
```
http://localhost:8081/swagger-ui.html
```

---

## 🚀 Despliegue en Producción

### Compilar JAR

```powershell
cd back
mvnw clean package
```

Genera: `target/ecommerce-0.0.1-SNAPSHOT.jar`

### Ejecutar en Producción

```powershell
# Con variables de entorno
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://prod-db:5432/ecommerce"
$env:SPRING_DATASOURCE_USERNAME="prod_user"
$env:SPRING_DATASOURCE_PASSWORD="prod_password"

java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

### Con Docker

```powershell
# Construir imagen
docker build -t ecommerce-backend .

# Ejecutar contenedor
docker run -p 8081:8081 `
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/ecommerce `
  -e SPRING_DATASOURCE_USERNAME=postgres `
  -e SPRING_DATASOURCE_PASSWORD=password `
  ecommerce-backend
```

---

## 📚 Documentación Adicional

| Archivo | Contenido |
|---------|-----------|
| `ARQUITECTURA.md` | Diseño y patrones |
| `CONEXION_BD.md` | Detalles de conexión BD |
| `Postman_Collection_ECommerce.json` | Endpoints preconfigurados |
| `insert_test_data.sql` | Datos de prueba |

---

## ✅ Checklist de Inicio

- [ ] PostgreSQL está instalado y corriendo
- [ ] Java 25+ está instalado
- [ ] Carpeta backend está en: `C:\java\proyectoprofe\ecommerceclase\back`
- [ ] Ejecutaste `run-backend.bat` o `run-backend.sh`
- [ ] Backend muestra "Tomcat started on port(s): 8081"
- [ ] Accediste a http://localhost:8081/swagger-ui.html
- [ ] Puedes ver la lista de endpoints en Swagger

---

## 📞 Soporte

Si tienes problemas:

1. **Verifica los logs**: Busca errores en la terminal
2. **Revisa application.properties**: Conexión a BD correcta
3. **Test endpoint**: `curl http://localhost:8081/api/products`
4. **Frontend**: Verifica que hace peticiones a `localhost:8081`

---

## 🎯 Próximos Pasos

1. ✅ Backend corriendo
2. ➡️ [Inicia el Frontend](GUIA_INICIO_FRONTEND.md)
3. ➡️ Registra usuarios
4. ➡️ Prueba endpoints en Swagger
5. ➡️ Integra con Frontend

---

**¡El backend está listo! 🚀**

Versión: 1.0  
Última actualización: 2024-11-16

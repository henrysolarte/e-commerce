# 🛍️ E-Commerce Backend - Guía Rápida

## 📋 Requisitos Previos

✅ **Verificado y Confirmado:**
- Java 27 (requerido: 25+) ✅
- Maven 3.9.9 ✅
- Backend compilado ✅
- BD "ecommerce" creada ✅

❌ **Necesario:**
- PostgreSQL corriendo en `localhost:5432`
- Usuario: `postgres` con contraseña: `postgres`
- Base de datos `ecommerce` debe existir

---

## 🚀 INICIO RÁPIDO

### Opción 1: Windows (Recomendado)
```bash
cd back
run-backend.bat
```

### Opción 2: Linux/Mac
```bash
cd back
chmod +x run-backend.sh
./run-backend.sh
```

### Opción 3: Maven directo (Cualquier SO)
```bash
cd back
mvnw spring-boot:run
```

---

## ✅ Verificar que está corriendo

Una vez ejecutado, deberías ver:
```
Backend de E-Commerce iniciado correctamente.
```

Luego accede a:
- **API Base**: http://localhost:8081
- **Documentación Swagger**: http://localhost:8081/swagger-ui.html

---

## 📊 Configuración de Base de Datos

**Archivo**: `back/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

**Notas:**
- `ddl-auto=update` → Hibernate sincroniza automáticamente el schema
- Las tablas se crearán automáticamente si no existen
- La BD debe existir previamente

---

## 🔄 Conectar Frontend

Una vez el backend esté corriendo, el frontend está listo para conectarse.

**Frontend ubicado en**: `front/`

```bash
cd front
npm install
npm run dev
```

El httpClient ya está configurado en `front/src/api/httpClient.js` para conectar a:
```
http://localhost:8081/api
```

---

## 🗄️ Estructura de Base de Datos

Las siguientes tablas se crearán automáticamente:

```sql
-- Tabla de usuarios
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) DEFAULT 'ROLE_USER'
);

-- Tabla de categorías
CREATE TABLE categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT
);

-- Tabla de productos
CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  sku VARCHAR(50) UNIQUE NOT NULL,
  price DOUBLE PRECISION NOT NULL,
  stock INTEGER NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  category_id BIGINT REFERENCES categories(id)
);

-- Tabla de clientes
CREATE TABLE customers (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  phone VARCHAR(20),
  address VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🛠️ Herramientas Útiles

### Verificar conectividad con BD
```bash
cd back
verify-db.bat
```

### Recompilar manualmente
```bash
cd back
mvnw clean package -DskipTests
```

### Ver logs en tiempo real
```bash
cd back
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar | tee backend.log
```

---

## 🔐 Endpoints de Autenticación

El backend implementa autenticación JWT. Endpoints principales:

- **POST** `/api/auth/register` - Registrar nuevo usuario
- **POST** `/api/auth/login` - Login y obtener JWT token
- **GET** `/api/products` - Listar productos (público)
- **POST** `/api/products` - Crear producto (requiere ROLE_ADMIN)
- **GET** `/api/customers` - Listar clientes
- **GET** `/api/categories` - Listar categorías

Ver documentación completa en: http://localhost:8081/swagger-ui.html

---

## ❌ Troubleshooting

### "Connection refused"
```
❌ PostgreSQL no está corriendo
✅ Inicia el servicio PostgreSQL
```

### "Database 'ecommerce' does not exist"
```sql
CREATE DATABASE ecommerce;
```

### "Port 8081 already in use"
```properties
# En application.properties:
server.port=8082
```

### "role postgres does not exist"
```
Verifica credenciales en application.properties
Debe coincidir con tu usuario PostgreSQL
```

---

## 📦 Dependencias Principales

- **Spring Boot 4.1.2** - Framework web
- **Spring Data JPA** - ORM y persistencia
- **PostgreSQL Driver** - Conexión a BD
- **Spring Security** - Autenticación y autorización
- **JWT (JJWT)** - Tokens JWT
- **SpringDoc OpenAPI** - Documentación Swagger

---

## 📁 Estructura del Proyecto

```
ecommerceclase/
├── back/                                    ← Backend (JAVA)
│   ├── src/main/java/com/parquesoft/ecommerce/
│   │   ├── controller/      # Endpoints REST
│   │   ├── service/         # Lógica de negocio
│   │   ├── entity/          # Modelos JPA (ProductEntity, UserEntity, etc.)
│   │   ├── repository/      # Acceso a datos
│   │   ├── security/        # Seguridad JWT
│   │   └── config/          # Configuraciones
│   ├── src/main/resources/
│   │   └── application.properties  # ⚙️ Config BD aquí
│   ├── pom.xml              # Dependencias Maven
│   ├── run-backend.bat      # Script Windows
│   ├── run-backend.sh       # Script Linux/Mac
│   └── verify-db.bat        # Verificar BD
│
├── front/                                   ← Frontend (REACT)
│   ├── src/
│   │   ├── api/httpClient.js    # Cliente HTTP (ya configurado)
│   │   ├── components/          # Componentes React
│   │   ├── services/            # Servicios API
│   │   ├── context/             # Context API
│   │   └── pages/               # Páginas
│   ├── package.json
│   └── vite.config.js
│
└── README.md (este archivo)
```

---

## ✨ ¡Listo!

Tu backend está completamente integrado con la base de datos "ecommerce".

**Solo necesitas:**
1. Asegurar que PostgreSQL esté corriendo
2. Ejecutar `run-backend.bat` (Windows) o `run-backend.sh` (Linux/Mac)
3. Acceder a http://localhost:8081

¡Disfruta! 🚀

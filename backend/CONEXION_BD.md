# Conexión Backend - Base de Datos Ecommerce

## ✅ Estado Actual

El backend **ya está correctamente configurado** para conectarse a PostgreSQL. Aquí está la prueba:

### 1. **Configuración de Base de Datos** 
Archivo: `back/src/main/resources/application.properties`

```properties
spring.application.name=ecommerce
server.port=${PORT:8081}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

**Notas importantes:**
- `spring.jpa.hibernate.ddl-auto=update` → Hibernate actualizará automáticamente el schema si hay cambios
- La BD se conecta a `localhost:5432/ecommerce`
- Usuario: `postgres` | Contraseña: `postgres`

### 2. **Dependencias Instaladas**
El `pom.xml` incluye:
- ✅ `spring-boot-starter-data-jpa` → Acceso a base de datos
- ✅ `postgresql` driver → Conector PostgreSQL
- ✅ `spring-boot-starter-security` → Autenticación
- ✅ JWT (jjwt) → Tokens JWT
- ✅ SpringDoc OpenAPI → Documentación Swagger

### 3. **Entidades Creadas**
El backend incluye 4 entidades JPA:
- `UserEntity` (tabla: users)
- `ProductEntity` (tabla: products)
- `CategoryEntity` (tabla: categories)
- `CustomerEntity` (tabla: customers)

### 4. **Build Status**
```
✅ Compilación exitosa
✅ JAR generado: ecommerce-0.0.1-SNAPSHOT.jar
✅ Java 27 instalado (compatible con Java 25 requerido)
```

---

## 🚀 Pasos para Ejecutar

### Opción 1: Ejecutar el script (Recomendado)
```bash
cd C:\java\proyectoprofe\ecommerceclase\back
run-backend.bat
```

### Opción 2: Comando directo
```bash
cd C:\java\proyectoprofe\ecommerceclase\back
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

### Opción 3: Con Maven
```bash
cd C:\java\proyectoprofe\ecommerceclase\back
mvnw.cmd spring-boot:run
```

---

## ✅ Verificación de Conectividad

Una vez que el backend esté ejecutando, deberías ver en la consola:
```
Backend de E-Commerce iniciado correctamente.
```

Luego accede a:
- **API Base**: http://localhost:8081
- **Documentación Swagger**: http://localhost:8081/swagger-ui.html
- **Health Check**: http://localhost:8081/actuator/health (si está habilitado)

---

## 📋 Checklist Pre-Ejecución

Antes de ejecutar el backend, verifica:

- [ ] PostgreSQL está ejecutándose en `localhost:5432`
- [ ] Base de datos `ecommerce` existe
- [ ] Usuario `postgres` con contraseña `postgres` tiene acceso a la BD
- [ ] Puerto 8081 no está siendo usado por otra aplicación
- [ ] Java 25+ está instalado (verificado: Java 27 ✅)

---

## 🔧 Si hay problemas

### Error: "Connection refused to host: localhost"
→ PostgreSQL no está corriendo. Inicia el servicio de PostgreSQL.

### Error: "FATAL: database 'ecommerce' does not exist"
→ Crea la BD manualmente:
```sql
CREATE DATABASE ecommerce;
```

### Error: Puerto 8081 ya en uso
→ Cambia el puerto en `application.properties`:
```properties
server.port=8082
```

### Error: "role postgres does not exist"
→ Verifica que el usuario PostgreSQL sea correcto y tenga permisos.

---

## 📊 Estructura del Backend

```
back/
├── src/main/java/com/parquesoft/ecommerce/
│   ├── controller/          # API REST endpoints
│   ├── service/             # Lógica de negocio
│   ├── entity/              # Modelos JPA
│   ├── repository/          # Acceso a datos
│   ├── security/            # JWT y seguridad
│   ├── config/              # Configuraciones
│   └── dto/                 # Data Transfer Objects
├── src/main/resources/
│   └── application.properties  # ⚙️ Configuración BD (AQUÍ)
├── pom.xml                  # Dependencias Maven
└── target/
    └── ecommerce-0.0.1-SNAPSHOT.jar  # JAR compilado
```

---

## 📝 Resumen

Tu backend **está listo para usar**. Solo necesitas:
1. Asegurar que PostgreSQL esté corriendo
2. Confirmar que la BD `ecommerce` existe
3. Ejecutar el JAR o usar Maven

La conexión a la BD está 100% configurada. 🎉

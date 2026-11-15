# ✅ CHECKLIST DE VERIFICACIÓN

## Estado Actual del Proyecto

### ✅ Backend Java
- [x] Compilado exitosamente
- [x] JAR generado: `ecommerce-0.0.1-SNAPSHOT.jar`
- [x] Java 27 instalado (requerido: 25+)
- [x] Todas las dependencias descargadas
- [x] Controllers creados
- [x] Services implementados
- [x] Entities definidas
- [x] Repositories configurados
- [x] Seguridad JWT integrada
- [x] Swagger/OpenAPI habilitado

### ✅ Configuración Base de Datos
- [x] `application.properties` configurado
- [x] Driver PostgreSQL añadido
- [x] URL de conexión: `jdbc:postgresql://localhost:5432/ecommerce`
- [x] Usuario: `postgres`
- [x] Contraseña: `postgres`
- [x] DDL Auto: `update` (sincronización automática)

### ✅ Documentación
- [x] README.md creado
- [x] CONEXION_BD.md creado
- [x] SETUP_COMPLETADO.md creado
- [x] ARCHITECTURE.md creado
- [x] Scripts de inicio creados

### ✅ Frontend
- [x] React configurado
- [x] httpClient.js lista
- [x] Base URL configurada: `http://localhost:8081/api`
- [x] Services implementados
- [x] Context API lista

---

## Antes de Ejecutar

### PostgreSQL
- [ ] PostgreSQL está instalado
- [ ] PostgreSQL está en ejecución
- [ ] Puerto 5432 está disponible
- [ ] Base de datos `ecommerce` existe
- [ ] Usuario `postgres` existe
- [ ] Contraseña es `postgres`

### Sistema
- [ ] Puerto 8081 está disponible
- [ ] Java 25+ está instalado
- [ ] Maven está instalado
- [ ] Conexión a internet (para descargas)

---

## Pasos de Ejecución

### 1️⃣ Verificar Base de Datos
```bash
cd back
verify-db.bat
```
- [ ] Script ejecutado sin errores
- [ ] Conexión a PostgreSQL establecida
- [ ] Base de datos `ecommerce` confirmada

### 2️⃣ Iniciar Backend
```bash
cd back
run-backend.bat
```
- [ ] Servidor inicia sin errores
- [ ] Último mensaje: "Backend de E-Commerce iniciado correctamente."
- [ ] Consumo de memoria normal

### 3️⃣ Verificar Backend
Abrir navegador y acceder a:
```
http://localhost:8081/swagger-ui.html
```
- [ ] Swagger carga correctamente
- [ ] Endpoints se muestran
- [ ] Documentación visible

### 4️⃣ Iniciar Frontend (Opcional)
```bash
cd front
npm install
npm run dev
```
- [ ] Frontend compila sin errores
- [ ] Servidor React inicia en puerto 3000
- [ ] Frontend conecta al backend

---

## Endpoints Disponibles

### Autenticación
- [ ] `POST /api/auth/register` - Registrar usuario
- [ ] `POST /api/auth/login` - Login

### Productos
- [ ] `GET /api/products` - Listar productos
- [ ] `GET /api/products/{id}` - Obtener producto
- [ ] `POST /api/products` - Crear producto (Admin)
- [ ] `PUT /api/products/{id}` - Actualizar producto (Admin)
- [ ] `DELETE /api/products/{id}` - Eliminar producto (Admin)

### Categorías
- [ ] `GET /api/categories` - Listar categorías
- [ ] `POST /api/categories` - Crear categoría (Admin)

### Clientes
- [ ] `GET /api/customers` - Listar clientes
- [ ] `POST /api/customers` - Crear cliente

---

## Prueba de Conectividad

### Con Swagger UI
1. Ir a: http://localhost:8081/swagger-ui.html
2. Click en "Try it out" en cualquier endpoint
3. Click en "Execute"
4. Verificar respuesta

### Con cURL (Windows)
```powershell
# Listar productos (sin autenticación requerida)
curl -X GET "http://localhost:8081/api/products"

# Resultado esperado: [...]
```

### Con Postman
1. Importar endpoints desde: http://localhost:8081/v3/api-docs
2. Crear colección
3. Probar endpoints

---

## Logs Esperados

Al ejecutar `run-backend.bat`, deberías ver:
```
2026-11-15 15:30:00.000  INFO ... TomcatWebServer      : Tomcat started on port(s): 8081 (http)
2026-11-15 15:30:00.500  INFO ... EcommerceApplication : Backend de E-Commerce iniciado correctamente.
```

---

## Troubleshooting Rápido

| Problema | Solución |
|----------|----------|
| ❌ "Connection refused" | Inicia PostgreSQL |
| ❌ "Port already in use" | Cambia puerto en `application.properties` |
| ❌ "Database doesn't exist" | Ejecuta `CREATE DATABASE ecommerce;` |
| ❌ "Authentication failed" | Verifica usuario/contraseña en `application.properties` |
| ❌ "JAR not found" | Ejecuta `mvnw clean package -DskipTests` |
| ❌ "Java not found" | Instala Java 25+ |

---

## Estado Final: ✅ LISTO

```
┌─────────────────────────────────────────┐
│  Backend:        ✅ Compilado y Listo   │
│  BD Config:      ✅ Configurada         │
│  Frontend:       ✅ Listo               │
│  Documentación:  ✅ Completa            │
│  Seguridad:      ✅ JWT Integrado       │
│  API Docs:       ✅ Swagger Habilitado  │
└─────────────────────────────────────────┘
```

---

## Próximos Pasos

1. **Ejecuta el backend**: `run-backend.bat`
2. **Verifica en Swagger**: http://localhost:8081/swagger-ui.html
3. **Inicia el frontend**: `cd front && npm run dev`
4. **Prueba la aplicación**: http://localhost:3000

---

## Contacto / Ayuda

Si tienes problemas:
1. Revisa `CONEXION_BD.md` para troubleshooting detallado
2. Verifica `ARCHITECTURE.md` para entender la estructura
3. Consulta `README.md` para instrucciones completas

¡Listo para usar! 🚀

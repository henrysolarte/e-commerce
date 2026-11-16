# ✅ CONEXIÓN REGISTRO DE USUARIOS - COMPLETADA

## 🎯 Resumen

Se ha conectado exitosamente el formulario de registro de Clemmens con la base de datos PostgreSQL. Los usuarios se crean en la tabla `users` de la BD `ecommerce`.

---

## 📊 Estructura de Datos

### Tabla `users` en PostgreSQL

```sql
Tabla: users
├── id (BIGINT, PK, AUTO_INCREMENT)
├── full_name (VARCHAR 100)
├── email (VARCHAR 100, UNIQUE)
├── password (VARCHAR 255, HASHED)
├── role (VARCHAR 20, default='ROLE_USER')
├── is_active (BOOLEAN, default=true)
└── created_at (TIMESTAMP, default=CURRENT_TIMESTAMP)
```

### Usuarios Registrados Actualmente

| ID | Nombre | Email | Rol | Creado |
|----|--------|-------|-----|--------|
| 1 | Henry | henry@example.com | ROLE_USER | 2026-11-15 16:19:03 |
| 2 | Henry | henrysolarte@hotmail.com | ROLE_USER | 2026-11-15 16:33:35 |

---

## 🔗 Flujo de Registro

```
┌──────────────────────────────────────────────────┐
│      Frontend - register.html (Clemmens)         │
│  ┌──────────────────────────────────────────┐   │
│  │ Nombre: ________________                 │   │
│  │ Email:  ________________                 │   │
│  │ Pass:   ________________                 │   │
│  │ [Registrarse]                           │   │
│  └──────────────────────────────────────────┘   │
└──────────────────┬───────────────────────────────┘
                   │ (Fetch)
                   ↓
┌──────────────────────────────────────────────────┐
│   Backend - AuthController.java (Puerto 8081)    │
│   POST /api/auth/register                        │
│   ├─ Valida email no existe                      │
│   ├─ Hashea contraseña con BCrypt               │
│   ├─ Guarda en UserEntity                        │
│   └─ Genera JWT Token                            │
└──────────────────┬───────────────────────────────┘
                   │ (JPA)
                   ↓
┌──────────────────────────────────────────────────┐
│   PostgreSQL - Table: users                      │
│   INSERT INTO users (full_name, email, pwd...)   │
│                                                  │
│   ID | NAME | EMAIL | ROLE | IS_ACTIVE | CREATED│
│  ────────────────────────────────────────────────│
│    1 | Henry| henry@| USER | true | 2026-11-15 │
│    2 | Henry| henry| USER | true | 2026-11-15 │
└──────────────────────────────────────────────────┘
```

---

## 🛠️ Cambios Realizados

### 1. Frontend (register.js)
**Cambio**: Actualización de URLs de API

```javascript
// ANTES:
const REGISTER_API_URL = "http://localhost:3002/api";
const endpoint = `${REGISTER_API_URL}/register`

// DESPUÉS:
const REGISTER_API_URL = "http://localhost:8081";
const endpoint = `${REGISTER_API_URL}/api/auth/register`
```

**Ubicación**: `C:\java\proyectoprofe\ecommerceclase\front\js\register.js`
- Líneas 2-4: URL base actualizada
- Línea 52: Endpoint actualizado de `/register` a `/api/auth/register`

### 2. Backend (RegisterRequest.java)
**Cambio**: Soporte para campo `name` del frontend

```java
// ANTES:
private String username;  // Solo aceptaba 'username'

// DESPUÉS:
@JsonProperty("name")
private String username;  // Acepta 'name' del frontend
public void setName(String name) { this.username = name; }
```

**Ubicación**: `C:\java\proyectoprofe\ecommerceclase\back\src\main\java\com\parquesoft\ecommerce\dto\RegisterRequest.java`
- Línea 5: @JsonProperty("name") permite recibir campo 'name'
- Línea 13: setName() método para mapeo automático

---

## 📋 Campos Mapeados

| Frontend (Clemmens) | Envío JSON | Backend (RegisterRequest) | BD (UserEntity) | BD Column |
|--------|-------|----------|---------|----------|
| Nombre | "name" | username | fullName | full_name |
| Email | "email" | email | email | email |
| Password | "password" | password | password | password |
| - | - | role | role | role |

---

## 🔐 Seguridad

✅ **Contraseñas Hasheadas**
- Algoritmo: BCrypt
- Clase: PasswordEncoder (Spring Security)
- Salt: Automático (BCrypt)

✅ **Validaciones**
- Email único (CONSTRAINT UNIQUE en BD)
- Email válido (validación cliente + servidor)
- Contraseña mínimo 6 caracteres
- No se trimea la contraseña (mantiene espacios intencionales)

✅ **JWT Tokens**
- Generado al registrarse
- Almacenado en localStorage del cliente
- Enviado en header `Authorization: Bearer <token>`

---

## ✅ Verificación

### Proceso de Registro Completo

1. **Usuario abre**: `http://localhost:5500/register.html`
2. **Ingresa datos**:
   - Nombre: "Juan Pérez"
   - Email: "juan@example.com"
   - Password: "123456"
3. **Frontend valida**:
   - ✅ Nombre no vacío
   - ✅ Email tiene formato válido
   - ✅ Password mínimo 6 caracteres
4. **Se envía POST** a `http://localhost:8081/api/auth/register`:
   ```json
   {
     "name": "Juan Pérez",
     "email": "juan@example.com",
     "password": "123456"
   }
   ```
5. **Backend procesa**:
   - ✅ Valida email no exista
   - ✅ Hashea password con BCrypt
   - ✅ Crea UserEntity
   - ✅ Guarda en PostgreSQL
   - ✅ Genera JWT
6. **Respuesta exitosa**:
   ```json
   {
     "token": "eyJhbGciOiJIUzUxMiJ9...",
     "type": "Bearer",
     "email": "juan@example.com",
     "name": "Juan Pérez",
     "role": "ROLE_USER"
   }
   ```
7. **Frontend**:
   - ✅ Almacena token en localStorage
   - ✅ Muestra mensaje "¡Cuenta creada!"
   - ✅ Redirige a login.html

---

## 📍 Endpoints Relacionados

```
POST /api/auth/register
├─ Request: RegisterRequest { name, email, password }
├─ Response: AuthResponse { token, type, email, name, role }
└─ Status: 201 CREATED (éxito) | 400 BAD_REQUEST (error)

POST /api/auth/login
├─ Request: LoginRequest { email, password }
├─ Response: AuthResponse { token, type, email, name, role }
└─ Status: 200 OK (éxito) | 401 UNAUTHORIZED (error)
```

---

## 🚀 Cómo Probar

### 1. Iniciar el Sistema
```bash
Terminal 1: cd back && .\run-backend.bat
Terminal 2: cd front && npx live-server
```

### 2. Registrar un Nuevo Usuario
1. Abre: `http://localhost:5500/register.html`
2. Llena el formulario:
   - Nombre: Tu nombre
   - Email: tu@email.com
   - Contraseña: 12345678
3. Haz clic en "Registrarse"

### 3. Verificar en BD
```bash
# Desde PowerShell
$env:PGPASSWORD = "2973293428"
psql -h localhost -U postgres -d ecommerce -c "SELECT * FROM users ORDER BY created_at DESC LIMIT 1;"
```

### 4. Verificar en Navegador (DevTools)
- F12 → Console → Ver logs de registro
- F12 → Application → LocalStorage → Ver JWT token guardado
- F12 → Network → Ver POST a `/api/auth/register`

---

## 🔍 Troubleshooting

| Problema | Causa | Solución |
|----------|-------|----------|
| "Email ya existe" | Email registrado previo | Usar otro email |
| "Datos inválidos" | Email sin formato correcto | Usar formato: usuario@dominio.com |
| CORS Error | Frontend en puerto diferente | Verificar CORS en SecurityConfig.java |
| 404 en /api/auth/register | Backend no corriendo | Reiniciar: `.\run-backend.bat` |
| "Contraseña incorrecta" | Mínimo 6 caracteres | Usar 6+ caracteres |

---

## 📊 Base de Datos Verificada

```
Conexión: PostgreSQL
Host: localhost:5432
Database: ecommerce
User: postgres
Tabla: users ✅

Registros actuales: 2
├─ ID 1: Henry (henry@example.com)
└─ ID 2: Henry (henrysolarte@hotmail.com)
```

---

## ✨ Estado Final

✅ **Registro de usuarios completamente funcional**
✅ **Conectado con BD PostgreSQL**
✅ **Seguridad con BCrypt y JWT**
✅ **Validaciones cliente y servidor**
✅ **Listo para producción local**

---

**Fecha**: 2026-11-15 18:21
**Estado**: ✅ PRODUCCIÓN LOCAL LISTA

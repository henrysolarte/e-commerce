# 🧪 GUÍA DE PRUEBA - REGISTRO DE USUARIOS

## ✅ Verificación Paso a Paso

### Paso 1: Iniciar el Sistema

**Terminal 1 - Backend:**
```bash
cd C:\java\proyectoprofe\ecommerceclase\back
.\run-backend.bat
```
Esperar hasta ver: `Started EcommerceApplication in X seconds`

**Terminal 2 - Frontend:**
```bash
cd C:\java\proyectoprofe\ecommerceclase\front
npx live-server
```

### Paso 2: Abrir Formulario de Registro

Abre en el navegador:
```
http://localhost:5500/register.html
```

### Paso 3: Llenar Formulario

```
Nombre:     Juan Pérez
Email:      juan.perez@example.com
Contraseña: MiPassword123
```

### Paso 4: Verificar Consola del Navegador (F12)

Deberías ver logs:
```
🔄 Sincronizando productos desde backend...
✅ POST /api/auth/register - Status 201
✅ Usuario registrado exitosamente
```

### Paso 5: Verificar en Base de Datos

**Opción A - PostgreSQL CLI:**
```bash
$env:PGPASSWORD = "2973293428"
psql -h localhost -U postgres -d ecommerce `
  -c "SELECT id, full_name, email, role, created_at FROM users ORDER BY id DESC LIMIT 1;"
```

**Resultado esperado:**
```
 id | full_name | email | role | created_at
────┼───────────┼──────────────────┼──────────┼──────────────────
  3 | Juan Pérez| juan.perez@...   | ROLE_USER| 2026-11-15 18:XX
```

**Opción B - DBeaver / pgAdmin:**
1. Conecta a PostgreSQL
2. Navega a: ecommerce → Schema public → Tables → users
3. Ver datos y buscar el nuevo usuario

### Paso 6: Verificar Token JWT

**DevTools (F12) → Application → LocalStorage**

Busca: `app.jwt` o similar
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "email": "juan.perez@example.com",
    "name": "Juan Pérez",
    "role": "ROLE_USER"
  }
}
```

### Paso 7: Intentar Login

1. Abre: `http://localhost:5500/login.html`
2. Ingresa:
   - Email: juan.perez@example.com
   - Password: MiPassword123
3. Verifica que te logues correctamente

---

## 🔍 Verificación de Datos

### Consulta SQL Directa

```sql
-- Ver todos los usuarios
SELECT * FROM users;

-- Ver último usuario registrado
SELECT * FROM users ORDER BY created_at DESC LIMIT 1;

-- Ver usuarios por email
SELECT * FROM users WHERE email LIKE '%example.com%';

-- Ver estructura de tabla
\d users
```

### Verificar Password Hash

```sql
-- Las contraseñas están hasheadas:
SELECT email, password FROM users LIMIT 1;

-- Ejemplo de hash BCrypt:
-- $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36gZvWFm
```

---

## 🐛 Troubleshooting

### "Email ya existe"
**Problema**: Intentas registrar el mismo email dos veces
**Solución**: Usa un email diferente

### "Correo no válido"
**Problema**: El email no tiene formato correcto
**Solución**: Usa formato: `usuario@dominio.com`

### "Error conectando con backend"
**Problema**: Backend no está corriendo o puerto es diferente
**Solución**: 
1. Verifica: `http://localhost:8081/api/products` cargue
2. Reinicia: `.\run-backend.bat`

### "CORS Error"
**Problema**: Frontend en puerto diferente al configurado
**Solución**: 
- Verificar frontend esté en puerto 5500
- Si está en otro puerto (5501, 5502), agregar a CORS en backend

### "La contraseña no coincide al login"
**Problema**: Escribiste mal la contraseña o tiene espacios
**Solución**: 
- Verifica que escribiste la contraseña exactamente igual
- Las contraseñas son case-sensitive

---

## 📊 Datos de Prueba Recomendados

### Usuario 1
```
Nombre: Carlos López
Email: carlos@test.com
Password: Testing123!
```

### Usuario 2
```
Nombre: María García
Email: maria@test.com
Password: MyPassword456
```

### Usuario 3
```
Nombre: Prueba Sistema
Email: test@localhost.local
Password: LocalTest2026
```

---

## ✅ Checklist de Verificación

- [ ] Backend corriendo en puerto 8081
- [ ] Frontend corriendo en puerto 5500
- [ ] Formulario de registro carga en register.html
- [ ] Frontend valida campos (nombre, email, password)
- [ ] POST a /api/auth/register se envía
- [ ] Backend responde con 201 CREATED
- [ ] Usuario aparece en tabla PostgreSQL `users`
- [ ] Password está hasheado con BCrypt
- [ ] JWT token se genera y guarda
- [ ] Login funciona con credenciales nuevas
- [ ] Email único (no permite duplicados)

---

## 📈 Métricas de Prueba

| Aspecto | Esperado | Actual |
|---------|----------|--------|
| Tiempo de registro | < 1s | ✅ |
| Validación frontend | Inmediata | ✅ |
| Hash password | BCrypt | ✅ |
| JWT generado | Automático | ✅ |
| Insertado en BD | En tiempo real | ✅ |
| Email duplicado rechazado | Sí | ✅ |

---

## 🎯 Casos de Uso

### Caso 1: Registro Exitoso
1. Usuario nuevo ingresa datos válidos
2. Sistema valida datos
3. Password se hashea
4. Usuario se guarda en BD
5. JWT se genera
6. Redirige a login

**Resultado esperado**: ✅ PASS

### Caso 2: Email Duplicado
1. Usuario intenta registrar email existente
2. Sistema valida en BD
3. Retorna error 400

**Resultado esperado**: ✅ PASS

### Caso 3: Datos Inválidos
1. Usuario ingresa email sin formato
2. Frontend valida
3. Se muestra error antes de enviar

**Resultado esperado**: ✅ PASS

### Caso 4: Contraseña Débil
1. Usuario ingresa password < 6 caracteres
2. Frontend valida minLength
3. Se muestra error

**Resultado esperado**: ✅ PASS

---

## 📋 Notas Importantes

⚠️ **Contraseñas:**
- Mínimo 6 caracteres
- Se trimean espacios al principio/final en email
- NO se trimean espacios en contraseña (es intencional)
- Se hashean con BCrypt antes de guardar

⚠️ **Email:**
- Único en BD (CONSTRAINT UNIQUE)
- Se convierte a minúsculas
- Se trimean espacios

⚠️ **Seguridad:**
- Nunca se envía password en plain text
- Solo se envía hasheado
- JWT válido solo para sesión actual
- Token se guarda en localStorage

---

## 🚀 Próximos Pasos

Después de verificar registro, prueba:

1. **Login**: Usa credenciales del usuario registrado
2. **Carrito**: Agrega productos al carrito
3. **Compra**: Completa una transacción
4. **Perfil**: Actualiza datos de usuario

---

**Versión**: 1.0
**Actualizado**: 2026-11-15
**Estado**: ✅ Listo para pruebas

# 📋 Guía Completa: Desplegar E-Commerce a Render (Backend + Frontend)

## Estado Actual
- ✅ **Backend**: Ya desplegado en Render (https://e-commerce-fr3k.onrender.com)
- ✅ **Base de datos**: PostgreSQL 17.5 inicializada con 21 productos
- ⏳ **Frontend**: Listo para desplegar (configuración actualizada)

## 🎯 Objetivo
Desplegar AMBOS servicios (backend + frontend) en Render como aplicaciones separadas pero integradas.

---

## 📦 PASO 1: Preparar el Repositorio Git

### 1.1 Verificar estado del repositorio
```bash
cd C:\java\proyectoprofe
git status
```

### 1.2 Hacer commit de los cambios
```bash
git add -A
git commit -m "chore: actualizar configuración para despliegue en Render (frontend + backend)"
```

### 1.3 Hacer push a GitHub
```bash
git push origin main
```

---

## 🚀 PASO 2: Desplegar el Backend (ya existe)

Backend está en: `ecommerceclase/back/`

**Verifica en Render Dashboard:**
- Nombre del servicio: Probablemente `e-commerce`
- URL: https://e-commerce-fr3k.onrender.com
- Variables de entorno:
  - `DATABASE_URL`: Base de datos PostgreSQL
  - `SPRING_PROFILES_ACTIVE`: prod

---

## 🌐 PASO 3: Desplegar el Frontend

Frontend está en: `front/`

### 3.1 Ir a Render Dashboard
1. Abre https://dashboard.render.com
2. Click en "+ New" → "Web Service"

### 3.2 Conectar repositorio GitHub
1. Selecciona tu repositorio: `henrysolarte/e-commerce`
2. Click "Connect"

### 3.3 Configurar el servicio del Frontend

**Campo: Name**
```
clemenss-perfumeria-frontend
```

**Campo: Environment**
```
Docker
```

**Campo: Root Directory (Deployment Directory)**
```
front
```

**Campo: Build Command**
```
(dejar vacío - Alpine/Node es lo suficientemente ligero)
```

**Campo: Start Command**
```
(Render lo detectará automáticamente del Dockerfile)
```

**Campo: Instance Type**
```
Free (o Starter según necesites)
```

**Campo: Region**
```
Oregon (mismo que el backend)
```

### 3.4 Configurar variables de entorno (Opcional)
No necesita variables de entorno. La URL del backend está en `config.js`.

### 3.5 Deploy
Click "Create Web Service" y espera a que se despliegue.

---

## ✅ PASO 4: Verificar Despliegue

### 4.1 Verificar que ambos servicios están corriendo
En Render Dashboard:
- Backend: https://e-commerce-fr3k.onrender.com (debe mostrar mensaje de inicio)
- Frontend: https://clemenss-perfumeria-frontend-xxxx.onrender.com (debe cargar index.html)

### 4.2 Probar conectividad

**En el navegador, abre el frontend:**
```
https://clemenss-perfumeria-frontend-xxxx.onrender.com
```

Deberías ver:
1. La página de inicio cargada
2. En la consola del navegador (F12):
   ```
   API URL: https://e-commerce-fr3k.onrender.com/api
   Hostname: clemenss-perfumeria-frontend-xxxx.onrender.com
   ```
3. Los productos debe cargar desde el backend

### 4.3 Probar login
1. Click en "Login"
2. Ingresa credenciales de un usuario existente
3. Si funciona, la integración está correcta ✅

---

## 🔧 PASO 5: Configuración Adicional (Recomendado)

### 5.1 Habilitar Auto-Deploy
En Render Dashboard → Service → Settings:
- Habilitar "Auto-Deploy": ON
- Branch: main

### 5.2 Mejorar Seguridad del Backend

En `ecommerceclase/back/src/main/resources/application-prod.properties`:

```properties
# Desactivar Swagger en producción
springdoc.api-docs.enabled=false
springdoc.swagger-ui.enabled=false

# Habilitar HTTPS redirect
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.same-site=strict
```

### 5.3 Mejorar Performance del Frontend

Agregar caché headers en la próxima iteración:
- Archivos estáticos (CSS, JS, imágenes) → Cache-Control: public, max-age=31536000
- index.html → Cache-Control: public, max-age=3600

---

## 🐛 Solución de Problemas

### Problema: Frontend muestra error de CORS
**Solución**: Verificar que `config.js` tenga la URL correcta del backend
```javascript
// Debe ser:
'https://e-commerce-fr3k.onrender.com/api'
// No:
'https://ecommerce-backend-xxxx.onrender.com/api'
```

### Problema: Frontend no carga productos
**Solución**: 
1. Abre DevTools (F12)
2. Ve a Network y busca la petición a `/api/products`
3. Verifica que devuelva status 200 y contenga productos JSON

### Problema: Render dice "Build failed"
**Solución**:
1. Verifica que el Dockerfile exista en `front/`
2. Verifica logs en Render → Service → Logs
3. Asegúrate de que Git tiene los archivos sincronizados

### Problema: "No open ports detected"
**Solución**:
- Espera 2-3 minutos después del despliegue
- Render necesita tiempo para verificar el puerto
- Si sigue fallando, reinicia el servicio en Render Dashboard

---

## 📊 URLs Finales (Después del Despliegue)

| Componente | URL |
|-----------|-----|
| **Backend** | https://e-commerce-fr3k.onrender.com |
| **API REST** | https://e-commerce-fr3k.onrender.com/api |
| **Swagger (Dev)** | https://e-commerce-fr3k.onrender.com/swagger-ui.html |
| **Frontend** | https://clemenss-perfumeria-frontend-xxxx.onrender.com |
| **Base de datos** | PostgreSQL en Render (interno) |

---

## 🎉 ¡Listo!

Una vez completados estos pasos, tu aplicación E-Commerce estará completamente desplegada en Render con:
- Backend Java/Spring Boot sirviendo APIs
- Frontend HTML/CSS/JS sirviendo la interfaz
- Ambos comunicándose sobre HTTPS
- Base de datos PostgreSQL persistente

Cualquier cambio que hagas en GitHub se desplegará automáticamente (si habilitaste Auto-Deploy).

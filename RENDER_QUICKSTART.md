# 🚀 INSTRUCCIONES RÁPIDAS PARA RENDER

## ✅ Ya preparado en tu repositorio:
- ✓ Backend configurado para Render (render.yaml)
- ✓ Propiedades de producción (application-prod.properties)
- ✓ Config.js para URL dinámica de API
- ✓ Guía completa de despliegue

---

## 📋 PASOS A SEGUIR EN RENDER.COM

### 1️⃣ CONECTAR REPOSITORIO
1. Ve a https://dashboard.render.com
2. Haz clic en **"New +"** → **"Web Service"**
3. Selecciona **"Deploy an existing project from a Git repository"**
4. Autoriza GitHub y selecciona: `henrysolarte/e-commerce`

---

### 2️⃣ CONFIGURAR BACKEND

**Configuración General:**
- **Name:** `ecommerce-backend`
- **Environment:** `Docker` (o Java si está disponible)
- **Region:** `Ohio` (o la más cercana a ti)
- **Plan:** `Free` (por ahora)

**Comandos:**
- **Build Command:** 
  ```
  cd back && ./mvnw clean package -DskipTests
  ```
- **Start Command:** 
  ```
  cd back && java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
  ```

**Variables de Entorno:**
Haz clic en "Advanced" → "Environment" y agrega:

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DATABASE_URL` | `postgresql://user:password@host:5432/ecommerce` |
| `PORT` | `8081` |

**🔑 DATABASE_URL debe ser:**
```
postgresql://[TU_USUARIO]:[TU_PASSWORD]@[TU_HOST]:[TU_PUERTO]/[TU_BD]
```

4. Haz clic en **"Create Web Service"**

---

### 3️⃣ CONFIGURAR FRONTEND

1. Ve a **"New +"** → **"Static Site"**
2. Selecciona tu repositorio `henrysolarte/e-commerce`

**Configuración:**
- **Name:** `ecommerce-frontend`
- **Build Command:** `echo "No build needed"`
- **Publish Directory:** `front`

3. Haz clic en **"Create Static Site"**

---

### 4️⃣ ACTUALIZAR URL DEL BACKEND

**Después de desplegar:**

1. Copia la URL del backend desplegado
   - Ej: `https://ecommerce-backend-xxxx.onrender.com`

2. Edita `front/config.js` local:
   ```javascript
   window.__API_URL__ = 'https://ecommerce-backend-xxxx.onrender.com/api';
   ```

3. Haz commit y push:
   ```bash
   git add front/config.js
   git commit -m "Update API URL for production"
   git push origin main
   ```

4. Render redesplegará automáticamente ✨

---

## 🔗 ENLACES ÚTILES

- **Dashboard Render:** https://dashboard.render.com
- **Repositorio:** https://github.com/henrysolarte/e-commerce
- **Documentación completa:** Ver `GUIA_DESPLEGAR_RENDER.md`

---

## ⚠️ IMPORTANTE

1. **DATABASE_URL:** Reemplaza con tus credenciales reales
2. **Tiempo de inicio:** El primer deploy tarda ~5-10 min
3. **Plan Free:** Se duerme después de 15 min de inactividad
4. **CORS:** Verifica que el backend permite requests desde tu frontend

---

## 🆘 PROBLEMAS COMUNES

| Problema | Solución |
|----------|----------|
| "Build failed" | Verifica que `pom.xml` sea accesible en `back/` |
| "No conecta a BD" | Revisa DATABASE_URL y firewall de BD |
| "Frontend no ve API" | Actualiza `config.js` con URL correcta |
| "502 Bad Gateway" | El backend está iniciándose, espera 2-3 min |

---

¡Éxito! 🎉

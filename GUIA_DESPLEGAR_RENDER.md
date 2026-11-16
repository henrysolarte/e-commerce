# Guía: Desplegar E-Commerce en Render

## PASO 1: Preparar el Backend

### 1.1 Configurar variables de entorno en Render

Ve a https://dashboard.render.com/ y sigue estos pasos:

**Para el Backend (Web Service):**

1. Click en "+ New" → "Web Service"
2. Selecciona tu repositorio: `https://github.com/henrysolarte/e-commerce.git`
3. Configura:
   - **Name:** `ecommerce-backend`
   - **Environment:** `Java`
   - **Region:** `Ohio` (or closest to you)
   - **Build Command:** `cd back && ./mvnw clean package -DskipTests`
   - **Start Command:** `cd back && java -jar target/ecommerce-0.0.1-SNAPSHOT.jar`

4. En "Advanced" → Haz scroll hasta "Environment":
   - Agrega estas variables:
   ```
   SPRING_PROFILES_ACTIVE = prod
   DATABASE_URL = postgresql://[usuario]:[contraseña]@[host]:[puerto]/[base_datos]
   PORT = 8081
   ```

   **Nota:** Reemplaza los valores con tu base de datos externa

5. Click en "Create Web Service"

---

## PASO 2: Desplegar el Frontend

**Para el Frontend (Static Site):**

1. Click en "+ New" → "Static Site"
2. Selecciona tu repositorio: `https://github.com/henrysolarte/e-commerce.git`
3. Configura:
   - **Name:** `ecommerce-frontend`
   - **Build Command:** `echo "No build needed"`
   - **Publish Directory:** `front`
   - **Root Directory:** (dejar vacío)

4. Click en "Create Static Site"

---

## PASO 3: Configurar el Frontend para conectar con el Backend

Una vez desplegados, necesitas actualizar la URL del backend en el frontend.

### 3.1 Obtener la URL del Backend

Después de que Render despliegue el backend:
- Ir al servicio `ecommerce-backend`
- Copiar la URL (ej: `https://ecommerce-backend-xxxx.onrender.com`)

### 3.2 Actualizar frontend para usar URL de producción

Edita `front/js/auth.js` y otros archivos JS que hagan llamadas a la API:

**Busca líneas como:**
```javascript
const API_BASE_URL = 'http://localhost:8081';
```

**Reemplaza con:**
```javascript
const API_BASE_URL = 'https://ecommerce-backend-xxxx.onrender.com';
```

Luego haz commit y push:
```bash
git add .
git commit -m "Update API URL for production"
git push origin main
```

Render redesplegará automáticamente.

---

## PASO 4: Verificar Conexión a BD Externa

Asegúrate que tu base de datos PostgreSQL externa está accesible desde Render:

1. Verifica que las credenciales sean correctas
2. Confirma que el host está permitido para conexiones externas
3. Prueba la conexión en Render:
   - Ir a tu servicio → Logs
   - Deberías ver mensajes de conexión exitosa

---

## PASO 5: Variables de Entorno Completas

**Para el Backend, en Render agrega:**

```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=postgresql://usuario:contraseña@host:5432/ecommerce
PORT=8081
```

**Donde:**
- `usuario` = tu usuario PostgreSQL
- `contraseña` = tu contraseña
- `host` = IP o dominio de tu servidor PostgreSQL
- `ecommerce` = nombre de la base de datos

---

## PASO 6: Verificar que todo funciona

1. Abre tu sitio frontend en Render
2. Intenta login/registro
3. Revisa que conecta correctamente con el backend
4. Verifica en los logs de Render que no hay errores

---

## Notas Importantes

⚠️ **Seguridad:**
- NO commits credenciales a Git
- Usa variables de entorno en Render para datos sensibles
- Considera usar un proxy o VPN si tu BD está restringida

⚠️ **Rendimiento:**
- El plan free de Render puede dormirse después de inactividad
- Para producción, considera un plan pago

⚠️ **CORS:**
- Asegúrate que el backend permite requests del frontend
- Agrega configuración CORS si es necesario en Spring Boot

---

## Troubleshooting

**Error: "No se conecta a la BD"**
- Verifica DATABASE_URL está correcta
- Confirma que el host acepta conexiones externas
- Revisa firewall/security groups

**Error: "Frontend no conecta con API"**
- Verifica la URL del backend en los archivos JS
- Revisa CORS en la configuración de Spring Boot
- Abre console del navegador (F12) para ver errores

**El backend se reinicia constantemente**
- Revisa los logs en Render
- Puede ser un error en la conexión a BD

---

¿Necesitas ayuda con algún paso específico?

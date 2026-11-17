# 🎨 GUÍA DE INICIO RÁPIDO - FRONTEND

**Proyecto**: E-Commerce Clemmens  
**Tipo**: Frontend HTML/CSS/JavaScript Vanilla  
**Server**: Live Server (puerto 5500)  
**Estado**: ✅ Listo para Usar

---

## 📋 Requisitos Previos

Verifica que tengas instalado:

```bash
# Node.js (incluye npm)
node --version
# Debe mostrar: v18.0.0 o superior

# npm
npm --version
# Debe mostrar: 9.0.0 o superior
```

Si no los tienes, descárgalo:
- **Node.js + npm**: https://nodejs.org/ (LTS recomendado)

---

## ⚡ INICIO RÁPIDO (2 minutos)

### Opción 1: Script Automático (Recomendado)

**Windows (PowerShell)**:
```powershell
cd C:\java\proyectoprofe\clemmens
npm run front
```

**Linux/Mac**:
```bash
cd ~/java/proyectoprofe/clemmens
npm run front
```

✅ Se abrirá automáticamente en: **http://localhost:5500**

---

### Opción 2: Manual (Si el script no funciona)

**Paso 1**: Instala dependencias
```powershell
cd C:\java\proyectoprofe\clemmens
npm install
```

**Paso 2**: Inicia el servidor
```powershell
npx live-server --host=127.0.0.1 --port=5500
```

**Paso 3**: Abre en navegador
```
http://localhost:5500
```

---

## 📁 Estructura del Proyecto

```
clemmens/
├── index.html              # Página principal
├── productos.html          # Catálogo de productos
├── carrito.html            # Carrito de compras
├── login.html              # Login de usuarios
├── register.html           # Registro de usuarios
├── pagos.html              # Proceso de pago
├── package.json            # Configuración NPM
├── style.css               # Estilos principales
├── css/                    # Estilos adicionales
│   ├── styles.css
│   ├── carrito.css
│   ├── pagos.css
│   └── normalize.css
├── js/                     # JavaScript de la aplicación
│   ├── app.js              # Lógica principal
│   ├── productos.js        # Manejo de productos
│   ├── carrito.js          # Lógica del carrito
│   ├── login.js            # Autenticación
│   └── utils.js            # Funciones auxiliares
├── images/                 # Imágenes (perfumes, banners, etc.)
├── assets/                 # Respaldos de archivos
└── server/                 # Backend local (opcional)
```

---

## 🌐 Conexión Backend

### Configuración Automática

El frontend **ya está configurado** para conectarse al backend en:
```
http://localhost:8081/api
```

### Si necesitas cambiar la URL

Busca en `js/app.js` o `js/productos.js`:
```javascript
const API_URL = 'http://localhost:8081/api';
```

Cambia `localhost:8081` por la URL de tu backend.

---

## 🏃 Navegación

Una vez que abres **http://localhost:5500**, verás:

### 🏠 Página Principal (`index.html`)
- Banner de bienvenida
- Categorías de productos
- Destacados del mes
- Newsletter

### 👗 Catálogo (`productos.html`)
- Filtrar por categoría
- Ver detalles del producto
- Agregar al carrito
- Buscar productos

### 🛒 Carrito (`carrito.html`)
- Revisar productos agregados
- Ajustar cantidades
- Ver total
- Proceder a pago

### 🔐 Login (`login.html`)
- Acceso con usuario/contraseña
- Recuperación de contraseña (placeholder)

### ✍️ Registro (`register.html`)
- Crear nuevo usuario
- Validación de formulario
- Términos y condiciones

### 💳 Pagos (`pagos.html`)
- Resumen de orden
- Datos de envío
- Método de pago
- Confirmación

---

## 🔑 Autenticación

### Login

**Archivo**: `js/login.js`

```javascript
// Cuando usuario hace login:
1. Se captura usuario y contraseña
2. Se envía POST a: http://localhost:8081/api/auth/login
3. Backend retorna token JWT
4. Token se guarda en localStorage
5. Se redirige al usuario a inicio
```

### Token Guardado

```javascript
// Se almacena en localStorage como:
localStorage.setItem('authToken', response.token);
localStorage.setItem('usuario', response.username);
```

### Usando el Token

```javascript
// En cada petición se adjunta:
const headers = {
  'Content-Type': 'application/json',
  'Authorization': `Bearer ${localStorage.getItem('authToken')}`
};

fetch('http://localhost:8081/api/productos', {
  method: 'GET',
  headers: headers
});
```

---

## 📦 Cargar Productos desde Backend

### Automático (Ya configurado)

**Archivo**: `js/productos.js`

```javascript
// Al cargar la página:
1. Se conecta a: GET /api/products
2. Backend retorna lista de productos
3. Se renderizan dinámicamente en la página
4. Se pueden filtrar por categoría
```

### Respuesta del Backend

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

---

## 🛍️ Carrito de Compras

### Agregar Producto

```javascript
// En js/carrito.js
function agregarAlCarrito(producto) {
  let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
  
  let item = carrito.find(p => p.id === producto.id);
  
  if (item) {
    item.cantidad++;
  } else {
    carrito.push({ ...producto, cantidad: 1 });
  }
  
  localStorage.setItem('carrito', JSON.stringify(carrito));
  mostrarCarrito();
}
```

### Visualizar Carrito

```javascript
function mostrarCarrito() {
  let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
  let total = carrito.reduce((sum, item) => sum + (item.price * item.cantidad), 0);
  
  console.log('Carrito:', carrito);
  console.log('Total:', total);
}
```

### Eliminar Producto

```javascript
function eliminarDelCarrito(productoId) {
  let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
  carrito = carrito.filter(p => p.id !== productoId);
  localStorage.setItem('carrito', JSON.stringify(carrito));
  mostrarCarrito();
}
```

---

## 💳 Proceso de Pago

### Crear Orden

**Archivo**: `js/pagos.js`

```javascript
async function crearOrden() {
  const carrito = JSON.parse(localStorage.getItem('carrito'));
  const token = localStorage.getItem('authToken');
  
  const orden = {
    items: carrito.map(p => ({
      productId: p.id,
      cantidad: p.cantidad,
      precio: p.price
    })),
    total: carrito.reduce((sum, p) => sum + (p.price * p.cantidad), 0),
    direccion: document.getElementById('direccion').value,
    metodo_pago: 'tarjeta'
  };
  
  const response = await fetch('http://localhost:8081/api/orders', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(orden)
  });
  
  if (response.ok) {
    alert('Orden creada exitosamente');
    localStorage.removeItem('carrito');
  }
}
```

---

## 🔧 Scripts NPM Disponibles

Abre `package.json` y verás:

```json
{
  "scripts": {
    "back": "npm --prefix server start",
    "front": "live-server --host=127.0.0.1 --port=5500 --open=/index.html .",
    "dev": "concurrently -k -n BACK,FRONT -c green,blue \"npm:back\" \"npm:front\""
  }
}
```

### Usar los scripts

```bash
# Inicia solo el frontend
npm run front

# Inicia backend + frontend simultáneamente
npm run dev

# Backend solo
npm run back
```

---

## 🌍 Variables Globales

**Archivo**: `js/app.js`

```javascript
// URL del API
const API_URL = 'http://localhost:8081/api';

// Claves de localStorage
const STORAGE_KEYS = {
  AUTH_TOKEN: 'authToken',
  USER: 'usuario',
  CARRITO: 'carrito'
};

// Estados de la aplicación
const APP_STATE = {
  isAuthenticated: false,
  currentUser: null,
  cart: []
};
```

---

## 🎨 Personalización

### Cambiar Colores

**Archivo**: `style.css`

```css
/* Variables principales */
:root {
  --color-primary: #d4af37;      /* Dorado */
  --color-secondary: #2c2c2c;    /* Negro */
  --color-accent: #ff6b6b;       /* Rojo */
}
```

### Cambiar Logo

**Archivo**: `index.html`

Busca:
```html
<img src="images/logo.png" alt="Clemmens Logo">
```

Reemplaza `images/logo.png` con tu archivo.

### Agregar Nueva Página

1. Crea `nueva-pagina.html`
2. Copia el estructura de otra página
3. Incluye `js/app.js` al final del `<body>`
4. Agrega enlace en la navegación

---

## 🧪 Pruebas

### Verificar Conexión con Backend

Abre **DevTools** (F12) → **Consola**

```javascript
// Verifica si el backend responde
fetch('http://localhost:8081/api/products')
  .then(r => r.json())
  .then(data => console.log('✅ Conexión OK', data))
  .catch(e => console.error('❌ Error:', e))
```

Deberías ver: ✅ **Conexión OK** + lista de productos

### Verificar localStorage

Abre **DevTools** → **Application** → **Local Storage**

Verifica:
- ✅ `authToken` (si has hecho login)
- ✅ `usuario` (nombre del usuario)
- ✅ `carrito` (productos agregados)

---

## 🐛 Troubleshooting

### ❌ "Port 5500 is already in use"

```bash
# Usa otro puerto
npx live-server --port 5501

# Encuentra el proceso
netstat -ano | findstr :5500

# Mata el proceso
taskkill /PID 12345 /F
```

### ❌ "CORS Error"

Error en consola: `Access to XMLHttpRequest has been blocked by CORS`

**Solución**: Verifica que el backend está corriendo en `localhost:8081`

```bash
# En otra terminal
cd C:\java\proyectoprofe\ecommerceclase\back
.\run-backend.bat
```

### ❌ "No puedo cargar productos"

1. Abre **DevTools** (F12) → **Network**
2. Recarga la página
3. Busca petición a `localhost:8081/api/products`
4. Verifica si tiene error (rojo) o éxito (verde)

Si está en rojo:
- Backend no está corriendo
- Backend en puerto diferente
- Problema de CORS

### ❌ "Login no funciona"

1. Verifica credentials en `login.html`
2. Revisa en DevTools → Network la petición POST a `/auth/login`
3. Backend debe retornar `token` y `username`

### ❌ "Carrito se borra al recargar"

Normal. Los datos están en `localStorage`, que se persiste entre recargas.

Si se borra:
- Revisa que `localStorage.setItem('carrito', ...)` se ejecuta
- Verifica en DevTools → Application → Local Storage

---

## 📱 Responsive Design

El frontend ya está optimizado para móvil:

```css
/* Media queries en style.css */
@media (max-width: 768px) {
  /* Estilos para tablet */
}

@media (max-width: 480px) {
  /* Estilos para móvil */
}
```

Prueba abriendo en móvil o usando DevTools (F12 → Toggle Device Toolbar).

---

## 🚀 Despliegue

### Compilar para Producción

No es necesario compilar, es JavaScript vanilla. Solo:

1. Copia toda la carpeta `clemmens/` a tu servidor
2. Asegúrate de actualizar `API_URL` en `js/app.js` con la URL de producción

### Con Nginx

```nginx
server {
  listen 80;
  server_name perfumeria.ejemplo.com;
  
  root /var/www/clemmens;
  index index.html;
  
  location / {
    try_files $uri $uri/ /index.html;
  }
  
  location /api/ {
    proxy_pass http://localhost:8081;
  }
}
```

### Con Apache

```apache
<Directory /var/www/clemmens>
  Options -MultiViews
  RewriteEngine On
  RewriteCond %{REQUEST_FILENAME} !-f
  RewriteRule ^ index.html [QSA,L]
</Directory>
```

---

## ✅ Checklist de Inicio

- [ ] Node.js y npm están instalados
- [ ] Backend está corriendo en `localhost:8081`
- [ ] Ejecutaste `npm run front`
- [ ] Abriste `http://localhost:5500`
- [ ] Puedes ver el catálogo de productos
- [ ] Puedes agregar productos al carrito
- [ ] DevTools No muestra errores CORS

---

## 📊 Monitoreo

### Ver logs del servidor

La terminal de `npm run front` muestra:
```
Serving files from /path/to/clemmens
Live reload enabled.
http://127.0.0.1:5500
```

### Ver errores en la aplicación

**DevTools** (F12) → **Console**

Busca mensajes como:
```
✅ Productos cargados: 21
✅ Usuario autenticado
❌ Error al cargar productos
```

---

## 📞 Soporte

Si tienes problemas:

1. **Verifica backend**: `curl http://localhost:8081/api/products`
2. **Revisa DevTools**: F12 → Console y Network
3. **Verifica localStorage**: F12 → Application
4. **Busca errores**: Inspecciona los logs del servidor

---

## 🎯 Próximos Pasos

1. ✅ Frontend corriendo
2. ✅ [Backend corriendo](GUIA_INICIO_BACKEND.md)
3. ➡️ Registra un usuario en `/register.html`
4. ➡️ Haz login en `/login.html`
5. ➡️ Explora el catálogo en `/productos.html`
6. ➡️ Agrega productos al carrito
7. ➡️ Completa el checkout

---

## 💡 Tips

- **Limpiar carrito**: Abre DevTools → Consola y ejecuta:
  ```javascript
  localStorage.removeItem('carrito')
  ```

- **Logout**: 
  ```javascript
  localStorage.removeItem('authToken')
  localStorage.removeItem('usuario')
  location.reload()
  ```

- **Ver datos guardados**:
  ```javascript
  console.log(JSON.parse(localStorage.getItem('carrito')))
  ```

---

**¡El frontend está listo! 🎨**

Versión: 1.0  
Última actualización: 2024-11-16

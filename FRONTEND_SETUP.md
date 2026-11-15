# 🎨 FRONTEND - INSTRUCCIONES DE CONEXIÓN

## Estado Actual del Frontend

El frontend React **ya está configurado** para conectarse al backend. El cliente HTTP está listo en:

📁 `front/src/api/httpClient.js`

---

## 🔗 Conexión Automática

El httpClient ya está configurado para conectar a:
```
http://localhost:8081/api
```

Esto significa que **no necesitas cambiar nada** si ejecutas el backend en el puerto 8081.

---

## ▶️ Cómo Ejecutar el Frontend

### Paso 1: Instalar Dependencias
```bash
cd front
npm install
```

### Paso 2: Iniciar el Servidor de Desarrollo
```bash
npm run dev
```

### Paso 3: Acceder a la Aplicación
```
http://localhost:3000
```

---

## 🌍 Variables de Entorno

Si necesitas cambiar la URL del backend, edita:

**Archivo**: `front/.env.local` (crear si no existe)

```env
VITE_API_BASE_URL=http://localhost:8081/api
```

Luego reinicia el servidor con `npm run dev`.

---

## 📋 Estructura del Frontend

```
front/
├── src/
│   ├── api/
│   │   └── httpClient.js        ← Cliente HTTP (ya configurado ✅)
│   ├── services/
│   │   ├── authService.js       ← Autenticación
│   │   ├── productService.js    ← Productos
│   │   ├── customerService.js   ← Clientes
│   │   └── orderService.js      ← Órdenes
│   ├── components/
│   │   ├── auth/                ← Login, Registro
│   │   ├── products/            ← Catálogo de productos
│   │   ├── cart/                ← Carrito
│   │   ├── checkout/            ← Checkout
│   │   ├── customers/           ← Gestión de clientes
│   │   ├── orders/              ← Historial de órdenes
│   │   └── common/              ← Componentes reutilizables
│   ├── context/
│   │   ├── AuthContext.jsx      ← Estado de autenticación
│   │   └── CartContext.jsx      ← Estado del carrito
│   ├── pages/
│   │   ├── HomePage.jsx
│   │   ├── LoginPage.jsx
│   │   ├── ProductsPage.jsx
│   │   ├── CustomersPage.jsx
│   │   └── OrdersPage.jsx
│   ├── hooks/
│   │   ├── useProducts.js       ← Hook para productos
│   │   └── useCustomers.js      ← Hook para clientes
│   ├── App.jsx
│   ├── main.jsx
│   └── styles/
│       └── main.css
├── package.json
├── vite.config.js
└── index.html
```

---

## 🔑 Cliente HTTP - Uso en Componentes

### Importar el cliente
```javascript
import { httpClient } from '@/api/httpClient'
```

### Realizar peticiones

#### GET (Obtener datos)
```javascript
// Listar productos
const products = await httpClient.get('/products')

// Obtener un producto específico
const product = await httpClient.get('/products/1')
```

#### POST (Crear datos)
```javascript
// Login
const response = await httpClient.post('/auth/login', {
  username: 'usuario',
  password: 'contraseña'
})

// Crear producto (requiere autenticación)
await httpClient.post('/products', {
  name: 'Nuevo Producto',
  sku: 'SKU001',
  price: 99.99,
  stock: 10
})
```

#### PUT (Actualizar datos)
```javascript
await httpClient.put('/products/1', {
  name: 'Producto Actualizado',
  price: 149.99
})
```

#### DELETE (Eliminar datos)
```javascript
await httpClient.delete('/products/1')
```

---

## 🔐 Autenticación JWT

El cliente HTTP **maneja automáticamente** los tokens JWT.

### Cómo funciona:

1. **Login**: El usuario se autentica
   ```javascript
   const response = await httpClient.post('/auth/login', {
     username: 'usuario',
     password: 'contraseña'
   })
   ```

2. **Token guardado**: El token se guarda automáticamente en localStorage
   ```javascript
   // En httpClient.js:
   localStorage.setItem('ecommerce_auth_token', token)
   ```

3. **Token inyectado**: Se añade automáticamente a todas las peticiones
   ```javascript
   // En cada request:
   headers: {
     'Authorization': `Bearer ${token}`
   }
   ```

4. **Logout**: Elimina el token
   ```javascript
   localStorage.removeItem('ecommerce_auth_token')
   ```

---

## 🎯 Endpoints Disponibles en el Frontend

### Autenticación (`authService.js`)
```javascript
POST   /auth/register    // Registrar nuevo usuario
POST   /auth/login       // Login
```

### Productos (`productService.js`)
```javascript
GET    /products         // Listar todos
GET    /products/{id}    // Obtener uno
POST   /products         // Crear (Admin)
PUT    /products/{id}    // Actualizar (Admin)
DELETE /products/{id}    // Eliminar (Admin)
```

### Categorías
```javascript
GET    /categories       // Listar categorías
POST   /categories       // Crear (Admin)
```

### Clientes (`customerService.js`)
```javascript
GET    /customers        // Listar todos
POST   /customers        // Crear cliente
GET    /customers/{id}   // Obtener uno
PUT    /customers/{id}   // Actualizar
DELETE /customers/{id}   // Eliminar
```

### Órdenes (`orderService.js`)
```javascript
POST   /orders          // Crear orden
GET    /orders          // Historial de órdenes
GET    /orders/{id}     // Detalle de orden
```

---

## 📝 Ejemplo: Componente con Fetch de Datos

```jsx
import { useState, useEffect } from 'react'
import { httpClient } from '@/api/httpClient'

export function ProductList() {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true)
        const data = await httpClient.get('/products')
        setProducts(data)
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    fetchProducts()
  }, [])

  if (loading) return <div>Cargando...</div>
  if (error) return <div>Error: {error}</div>

  return (
    <div>
      {products.map(product => (
        <div key={product.id}>
          <h3>{product.name}</h3>
          <p>${product.price}</p>
        </div>
      ))}
    </div>
  )
}
```

---

## 🛠️ Scripts NPM Disponibles

```bash
# Iniciar servidor de desarrollo
npm run dev

# Compilar para producción
npm run build

# Vista previa de build de producción
npm run preview

# Linter (si está configurado)
npm run lint
```

---

## 🚀 Compilar para Producción

```bash
npm run build
```

Esto genera la carpeta `dist/` lista para desplegar.

---

## ✅ Checklist: Antes de Ejecutar el Frontend

- [ ] Backend está corriendo en http://localhost:8081
- [ ] Base de datos está conectada
- [ ] Swagger muestra los endpoints: http://localhost:8081/swagger-ui.html
- [ ] Node.js está instalado (`node --version`)
- [ ] npm está instalado (`npm --version`)
- [ ] Carpeta `front/` tiene `package.json`

---

## 🔧 Troubleshooting

### "Cannot find module '@/api/httpClient'"
→ Verifica que el alias `@` esté configurado en `vite.config.js`

### "Connection refused" al conectar con backend
→ Asegúrate de que el backend está corriendo en puerto 8081

### "CORS error"
→ Añade `@CrossOrigin` a los controllers del backend

### "Token expired"
→ El usuario debe hacer login de nuevo

### Port 3000 ya en uso
→ Usa un puerto diferente:
```bash
npm run dev -- --port 3001
```

---

## 🎨 Componentes Principales

### AuthContext
Maneja el estado de autenticación global

```javascript
const { user, token, login, logout, isAuthenticated } = useContext(AuthContext)
```

### CartContext
Maneja el carrito de compras global

```javascript
const { items, addItem, removeItem, clearCart } = useContext(CartContext)
```

### Hooks Personalizados

#### useProducts
```javascript
const { products, loading, error, fetchProducts } = useProducts()
```

#### useCustomers
```javascript
const { customers, loading, error, fetchCustomers } = useCustomers()
```

---

## 📊 Flow de Datos

```
1. Usuario accede a http://localhost:3000
   ↓
2. React carga componentes
   ↓
3. useProducts() → httpClient.get('/products')
   ↓
4. Backend procesa la petición
   ↓
5. Backend consulta PostgreSQL
   ↓
6. Backend retorna datos JSON
   ↓
7. Frontend actualiza estado
   ↓
8. Componentes se renderizan
```

---

## 🌟 Características del httpClient

- ✅ Manejo automático de tokens JWT
- ✅ Inyección de headers Content-Type
- ✅ Normalización de errores HTTP
- ✅ Soporte para JSON
- ✅ Almacenamiento de tokens en localStorage
- ✅ Interceptadores de respuesta

---

## 🎯 Próximos Pasos

1. **Verifica que el backend esté corriendo**
   ```bash
   curl http://localhost:8081/api/products
   ```

2. **Instala dependencias del frontend**
   ```bash
   cd front
   npm install
   ```

3. **Inicia el servidor de desarrollo**
   ```bash
   npm run dev
   ```

4. **Accede a la aplicación**
   ```
   http://localhost:3000
   ```

5. **Prueba la autenticación**
   - Haz login en la interfaz
   - Verifica que el token se guardó en DevTools > Application > localStorage

---

## 📞 Soporte

Si tienes problemas:
1. Verifica que el backend está corriendo
2. Abre DevTools (F12) y revisa la consola
3. Revisa la pestaña Network para ver las peticiones HTTP
4. Comprueba en Swagger que los endpoints funcionan: http://localhost:8081/swagger-ui.html

---

**¡El frontend está listo! 🎨**

Solo necesitas ejecutar el backend y luego:
```bash
cd front
npm install
npm run dev
```

// productos-api.js
// Carga productos desde la API del backend en lugar de datos hardcodeados

const productosIsProd = location.hostname.endsWith("onrender.com");
const PRODUCTOS_API_URL = productosIsProd
  ? "https://e-commerce-fr3k.onrender.com/api"
  : (window.__API_URL__ || "http://localhost:8081/api");

console.log('[PRODUCTOS-API] Usando API URL:', PRODUCTOS_API_URL);

// Variable global para almacenar productos
window.productos = [];

// Función para cargar productos desde la API
async function cargarProductosDesdeAPI() {
  try {
    console.log('[PRODUCTOS-API] Iniciando carga de productos desde:', PRODUCTOS_API_URL + '/products');
    
    const response = await fetch(`${PRODUCTOS_API_URL}/products`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      // No enviamos credenciales para evitar problemas CORS
    });

    console.log('[PRODUCTOS-API] Response status:', response.status);

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const productos = await response.json();
    console.log('[PRODUCTOS-API] Productos recibidos:', productos);

    // Transformar los productos de la API al formato esperado por el frontend
    window.productos = productos.map(p => ({
      id: p.id,
      nombre: p.nombre,
      precio: p.precio,
      imagen: p.imagen || 'images/perfume_232x210.png',
      categoria: p.categoria || 'Otros',
      descuento: p.descuento || '',
      precioOriginal: p.precioOriginal || p.precio,
      rating: p.rating || 4.5,
      reviews: p.reviews || 0,
      descripcion: p.descripcion || '',
      stock: p.stock || 0,
      sku: p.sku || ''
    }));

    console.log('[PRODUCTOS-API] Productos transformados:', window.productos);
    
    // Disparar evento personalizado para notificar que los productos están listos
    document.dispatchEvent(new CustomEvent('productosListos', { detail: { productos: window.productos } }));
    
    return window.productos;

  } catch (error) {
    console.error('[PRODUCTOS-API] Error cargando productos:', error);
    console.error('[PRODUCTOS-API] Stack trace:', error.stack);
    
    // Disparar evento de error
    document.dispatchEvent(new CustomEvent('productosError', { detail: { error: error.message } }));
    
    // Retornar array vacío para evitar errores
    return [];
  }
}

// Cargar productos cuando el DOM esté listo
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', cargarProductosDesdeAPI);
} else {
  // Si el DOM ya está listo, cargar inmediatamente
  cargarProductosDesdeAPI();
}

// También exponer la función para que se pueda llamar manualmente
window.cargarProductosDesdeAPI = cargarProductosDesdeAPI;

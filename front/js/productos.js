// productos.js - Cargar productos desde API del backend

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

// Función para renderizar productos en la página
function renderProductos() {
  const contenedor = document.getElementById('productos-lista');
  if (!contenedor) return;
  
  contenedor.innerHTML = '';
  
  if (!window.productos || window.productos.length === 0) {
    contenedor.innerHTML = '<div class="col"><div class="alert alert-warning text-center">No hay productos para mostrar.</div></div>';
    return;
  }

  window.productos.forEach((producto, idx) => {
    const card = document.createElement('div');
    card.className = 'col';
    
    const precioOriginal = producto.precioOriginal || Math.round(producto.precio * 1.2);
    const descuento = producto.descuento || '10% OFF';
    
    card.innerHTML = `
      <div class="product-item card h-100 p-3">
        <figure class="text-center">
          <img src="${producto.imagen}" alt="${producto.nombre}" class="tab-image" style="max-width:100%;height:auto;">
        </figure>
        <div class="d-flex flex-column text-center">
          <h3 class="fs-6 fw-normal">${producto.nombre}</h3>
          <div class="mb-2">
            <span class="rating">
              ${'<svg width="18" height="18" class="text-warning"><use xlink:href="#star-full"></use></svg>'.repeat(Math.floor(producto.rating))}
              <svg width="18" height="18" class="text-warning"><use xlink:href="#star-half"></use></svg>
            </span>
            <span>(Stock: ${producto.stock || 0})</span>
          </div>
          <div class="d-flex justify-content-center align-items-center gap-2 mb-2">
            <del>$${precioOriginal.toLocaleString()}</del>
            <span class="text-dark fw-semibold">$${producto.precio.toLocaleString()}</span>
            <span class="badge border border-dark-subtle rounded-0 fw-normal px-1 fs-7 lh-1 text-body-tertiary">${descuento}</span>
          </div>
          <div class="button-area p-3 pt-0">
            <div class="row g-1 mt-2">
              <div class="col-2"><a href="#" class="btn btn-outline-dark rounded-1 p-2 fs-6"><svg width="18" height="18"><use xlink:href='#heart'></use></svg></a></div>
              <div class="col-7">
                <button type="button" class="btn btn-primary rounded-1 p-2 fs-7 btn-cart" id="btn-cart-${idx}" data-idx="${idx}">
                  <svg width="18" height="18"><use xlink:href='#cart'></use></svg> Add to Cart
                </button>
              </div>
              <div class="col-2"><a href="#" class="btn btn-outline-dark rounded-1 p-2 fs-6"><svg width="18" height="18"><use xlink:href='#heart'></use></svg></a></div>
            </div>
          </div>
        </div>
      </div>
    `;
    
    contenedor.appendChild(card);
  });

  // Asignar eventos a los botones
  document.querySelectorAll('.btn-cart').forEach(btn => {
    btn.addEventListener('click', function(e) {
      e.preventDefault();
      
      if (!usuarioLogueado()) {
        alert('Debes iniciar sesión para agregar productos al carrito.');
        window.location.href = 'login.html';
        return;
      }
      
      const idx = this.getAttribute('data-idx');
      const producto = window.productos[idx];
      
      console.log('Producto agregado al carrito:', producto.nombre);
      
      agregarAlCarrito({
        productoId: producto.id,
        nombre: producto.nombre,
        precio: producto.precio,
        imagen: producto.imagen,
        cantidad: 1
      });
      
      // Mostrar mensaje
      const mensaje = document.getElementById('mensaje');
      if (mensaje) {
        mensaje.textContent = 'Producto agregado al carrito.';
        mensaje.style.display = 'block';
        setTimeout(() => { mensaje.style.display = 'none'; }, 2500);
      }
    });
  });
  
  console.log('Productos renderizados:', window.productos.length);
}

// Función para verificar si el usuario está logueado
function usuarioLogueado() {
  try {
    const token = localStorage.getItem('token');
    return !!token;
  } catch {
    return false;
  }
}

// Escuchar el evento de productos listos y renderizar
document.addEventListener('productosListos', function(event) {
  console.log('[PRODUCTOS] Evento productosListos recibido, renderizando...');
  renderProductos();
});

// Fallback: si los productos ya están cargados al momento de DOMContentLoaded
document.addEventListener('DOMContentLoaded', function() {
  console.log('[PRODUCTOS] DOMContentLoaded, productos cargados:', window.productos.length);
  if (window.productos && window.productos.length > 0) {
    renderProductos();
  }
});

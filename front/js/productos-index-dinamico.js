// Script para poblar productos en index.html dinámicamente desde window.productos

function renderizarProductosPorCategoria(categoria, contenedorId) {
  const contenedor = document.getElementById(contenedorId);
  if (!contenedor) return;

  const productosFiltrados = window.productos.filter(p => p.categoria === categoria);

  contenedor.innerHTML = productosFiltrados.map((producto, index) => {
    // Usamos el índice global del array de `window.productos` para mantener la consistencia
    const productoIndex = window.productos.findIndex(p => p.nombre === producto.nombre);
    // Escapar comillas en el nombre del producto para el onclick
    const nombreEscapado = producto.nombre.replace(/'/g, "\\'").replace(/"/g, '&quot;');
    return `
        <div class="col">
          <div class="product-item">
            <figure>
              <a href="#" title="${producto.nombre}" onclick="event.preventDefault();showProductModal('${nombreEscapado}')">
                <img src="${producto.imagen}" alt="${producto.nombre}" class="tab-image" data-index="${productoIndex}">
              </a>
            </figure>
            <div class="d-flex flex-column text-center">
              <h3 class="fs-6 fw-normal" style="cursor:pointer;" onclick="event.preventDefault();showProductModal('${nombreEscapado}')">${producto.nombre}</h3>
              <div class="mb-2">
                <span class="stock-info" style="font-weight: 600; font-size: 1rem; color: #2c3e50;">
                  Stock: ${producto.stock}
                </span>
              </div>
              <div class="d-flex justify-content-center align-items-center gap-2 mb-2">
                <del>$${producto.precioOriginal.toLocaleString()}</del>
                <span class="text-dark fw-semibold">$${producto.precio.toLocaleString()}</span>
                <span class="badge border border-dark-subtle rounded-0 fw-normal px-1 fs-7 lh-1 text-body-tertiary">${producto.descuento}</span>
              </div>
              <div class="button-area p-3 pt-0">
                <button type="button" class="btn-add-to-cart" data-index="${productoIndex}" ${producto.stock <= 0 ? 'disabled' : ''}" style="width:100%;padding:12px 20px;font-weight:600;font-size:1rem;background-color:#4CAF50;color:white;border:none;border-radius:12px;cursor:pointer;transition:background-color 0.3s ease;font-family:Arial,sans-serif;">
                  Agregar al carrito
                </button>
              </div>
            </div>
          </div>
        </div>
      `;
  }).join('');
}

function renderizarMasVendidos(contenedorId, cantidad) {
  const contenedor = document.getElementById(contenedorId);
  if (!contenedor) return;

  // Ordenar productos por 'reviews' de forma descendente y tomar la cantidad especificada
  const masVendidos = window.productos
    .slice() // Crear una copia para no modificar el array original
    .sort((a, b) => b.reviews - a.reviews)
    .slice(0, cantidad);

  contenedor.innerHTML = masVendidos.map(producto => {
    // Escapar comillas en el nombre del producto para el onclick
    const nombreEscapado = producto.nombre.replace(/'/g, "\\'").replace(/"/g, '&quot;');
    return `
      <a href="#" class="nav-link swiper-slide text-center" onclick="event.preventDefault();showProductModal('${nombreEscapado}')">
        <img src="${producto.imagen}" class="rounded-circle" alt="${producto.nombre}">
        <h4 class="fs-6 mt-3 fw-normal category-title">${producto.nombre}</h4>
      </a>
    `;
  }).join('');
}

document.addEventListener('DOMContentLoaded', async function() {
  if (!window.productos) return;

  if (typeof window.syncProductosConBackend === 'function') {
    await window.syncProductosConBackend();
  }

  renderizarProductosPorCategoria('Para ella', 'product-grid-ella');
  renderizarProductosPorCategoria('Para él', 'product-grid-el');
  renderizarMasVendidos('mas-vendidos-wrapper', 12); // Cargar los 12 más vendidos

  // Agregar estilos hover a los botones
  const style = document.createElement('style');
  style.textContent = `
    .btn-add-to-cart:hover:not(:disabled) {
      background-color: #45a049 !important;
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
    .btn-add-to-cart:disabled {
      background-color: #cccccc !important;
      cursor: not-allowed;
      opacity: 0.6;
    }
  `;
  document.head.appendChild(style);

  // Delegación de eventos para botones "Agregar al carrito"
  document.body.addEventListener('click', function(e) {
    // Manejar botón "Agregar al carrito"
    if (e.target.closest('.btn-add-to-cart')) {
      e.preventDefault();
      const button = e.target.closest('.btn-add-to-cart');
      const productoIndex = button.dataset.index;
      const producto = window.productos[productoIndex];

      if (producto) {
        if (producto.stock <= 0) {
          alert('Producto sin stock.');
          button.disabled = true;
          return;
        }
        if (!usuarioLogueado()) {
          alert('Debes iniciar sesión para agregar productos al carrito.');
          window.location.href = 'login.html';
          return;
        }

        agregarAlCarrito({
          productoId: producto.productoId,
          nombre: producto.nombre,
          precio: producto.precio,
          imagen: producto.imagen,
          cantidad: 1
        });
        
        // Rebajar stock
        producto.stock -= 1;
        
        // Mostrar alerta
        alert(`✓ ${producto.nombre} agregado al carrito!`);
        
        // Actualizar botón si stock llega a 0
        if (producto.stock <= 0) {
          button.disabled = true;
          button.textContent = 'Sin stock';
        }
      }
    }
  });
});

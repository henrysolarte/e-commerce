// productos-render.js - Renderiza productos en la página de productos.html

function renderProductosPorCategoria() {
  // Obtener los productos ya cargados desde productos.js
  if (!window.productos || window.productos.length === 0) {
    console.warn("No hay productos cargados");
    return;
  }

  // Separar productos por categoría
  const productosElla = window.productos.filter(p => p.categoria === "Para ella");
  const productosEl = window.productos.filter(p => p.categoria === "Para él");

  // Renderizar "Para ella"
  const contenedorElla = document.getElementById('productos-ella');
  if (contenedorElla) {
    renderProductosEnContenedor(productosElla, contenedorElla);
  }

  // Renderizar "Para él"
  const contenedorEl = document.getElementById('productos-el');
  if (contenedorEl) {
    renderProductosEnContenedor(productosEl, contenedorEl);
  }
}

function renderProductosEnContenedor(productos, contenedor) {
  contenedor.innerHTML = '';

  if (!productos || productos.length === 0) {
    contenedor.innerHTML = '<div class="col-12"><div class="alert alert-warning text-center">No hay productos en esta categoría.</div></div>';
    return;
  }

  productos.forEach((producto, idx) => {
    const card = crearTarjetaProducto(producto, idx);
    contenedor.appendChild(card);
  });

  // Asignar eventos a botones
  asignarEventosBotones();
}

function crearTarjetaProducto(producto, idx) {
  const col = document.createElement('div');
  col.className = 'col mb-4';

  const card = document.createElement('div');
  card.className = 'card h-100 shadow-sm';
  card.style.cursor = 'pointer';
  card.innerHTML = `
    <div class="position-relative">
      <img src="${producto.imagen}" alt="${producto.nombre}" class="card-img-top" style="height: 250px; object-fit: cover;">
      ${producto.descuento ? `<span class="badge bg-danger position-absolute top-0 end-0 m-2">${producto.descuento}</span>` : ''}
    </div>
    <div class="card-body d-flex flex-column">
      <h5 class="card-title">${producto.nombre}</h5>
      <p class="card-text text-muted small flex-grow-1">${producto.descripcion || 'Fragancia de calidad premium'}</p>
      
      <div class="d-flex align-items-center gap-2 my-2">
        <span class="text-warning">
          ${'★'.repeat(Math.floor(producto.rating))}${'☆'.repeat(5 - Math.floor(producto.rating))}
        </span>
        <small class="text-muted">(${producto.reviews || 0})</small>
      </div>

      <div class="d-flex justify-content-between align-items-center gap-2">
        <div>
          ${producto.precioOriginal && producto.precioOriginal > producto.precio ? `
            <del class="text-muted small">$${producto.precioOriginal.toLocaleString()}</del><br/>
          ` : ''}
          <span class="h5 text-primary">$${producto.precio.toLocaleString()}</span>
        </div>
        <div>
          ${producto.stock > 0 ? `
            <small class="text-success">Stock: ${producto.stock}</small>
          ` : `
            <small class="text-danger">Sin stock</small>
          `}
        </div>
      </div>

      <div class="mt-auto pt-3 d-grid gap-2">
        <button type="button" class="btn btn-primary btn-sm btn-agregar-carrito" data-id="${idx}" ${producto.stock <= 0 ? 'disabled' : ''}>
          🛒 Agregar al carrito
        </button>
        <button type="button" class="btn btn-outline-secondary btn-sm btn-ver-detalles" data-id="${idx}">
          👁️ Ver detalles
        </button>
      </div>
    </div>
  `;

  col.appendChild(card);
  return col;
}

function asignarEventosBotones() {
  // Botones "Agregar al carrito"
  document.querySelectorAll('.btn-agregar-carrito').forEach(btn => {
    btn.addEventListener('click', function(e) {
      e.preventDefault();
      
      if (!usuarioLogueado()) {
        alert('Debes iniciar sesión para agregar productos al carrito.');
        window.location.href = 'login.html';
        return;
      }

      const idx = this.getAttribute('data-id');
      const producto = window.productos[idx];
      
      if (producto) {
        agregarAlCarrito({
          productoId: producto.productoId || idx,
          nombre: producto.nombre,
          precio: producto.precio,
          imagen: producto.imagen,
          cantidad: 1
        });
        this.textContent = '✓ Agregado';
        this.disabled = true;
        setTimeout(() => {
          this.textContent = '🛒 Agregar al carrito';
          this.disabled = false;
        }, 2000);
      }
    });
  });

  // Botones "Ver detalles"
  document.querySelectorAll('.btn-ver-detalles').forEach(btn => {
    btn.addEventListener('click', function(e) {
      e.preventDefault();
      const idx = this.getAttribute('data-id');
      const producto = window.productos[idx];
      
      if (producto) {
        mostrarDetallesProducto(producto);
      }
    });
  });
}

function mostrarDetallesProducto(producto) {
  const modal = document.getElementById('modalDetalles');
  
  if (!modal) {
    // Crear modal si no existe
    const modalHtml = `
      <div class="modal fade" id="modalDetalles" tabindex="-1">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="modalTitulo"></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body" id="modalContenido">
              <!-- Contenido dinámico -->
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
              <button type="button" class="btn btn-primary btn-agregar-modal">🛒 Agregar al carrito</button>
            </div>
          </div>
        </div>
      </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
  }

  // Llenar datos del modal
  document.getElementById('modalTitulo').textContent = producto.nombre;
  document.getElementById('modalContenido').innerHTML = `
    <div class="row">
      <div class="col-md-6">
        <img src="${producto.imagen}" alt="${producto.nombre}" class="img-fluid rounded">
      </div>
      <div class="col-md-6">
        <p><strong>Descripción:</strong> ${producto.descripcion || 'Fragancia premium de alta calidad'}</p>
        <p><strong>Categoría:</strong> ${producto.categoria}</p>
        <p><strong>Precio:</strong> <span class="h4 text-primary">$${producto.precio.toLocaleString()}</span></p>
        <p><strong>Stock:</strong> ${producto.stock > 0 ? `<span class="text-success">${producto.stock} unidades</span>` : '<span class="text-danger">Sin stock</span>'}</p>
        <p><strong>Rating:</strong> ${'★'.repeat(Math.floor(producto.rating))}${'☆'.repeat(5 - Math.floor(producto.rating))} (${producto.reviews} reseñas)</p>
      </div>
    </div>
  `;

  // Botón agregar en modal
  const btnAgregar = document.querySelector('.btn-agregar-modal');
  btnAgregar.onclick = function() {
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

    alert('Producto agregado al carrito');
    bootstrap.Modal.getInstance(document.getElementById('modalDetalles')).hide();
  };

  // Mostrar modal
  const modalDetalles = new bootstrap.Modal(document.getElementById('modalDetalles'));
  modalDetalles.show();
}

function agregarAlCarrito(producto) {
  try {
    const carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    
    const existente = carrito.find(p => p.productoId === producto.productoId);
    if (existente) {
      existente.cantidad += producto.cantidad;
    } else {
      carrito.push(producto);
    }
    
    localStorage.setItem('carrito', JSON.stringify(carrito));
    console.log('Producto agregado:', producto.nombre);
    
    // Actualizar contador del carrito si existe
    actualizarContadorCarrito();
  } catch (e) {
    console.error('Error al agregar al carrito:', e);
  }
}

function usuarioLogueado() {
  try {
    const session = localStorage.getItem('app.jwt');
    if (!session) return false;
    const obj = JSON.parse(session);
    return !!(obj && obj.token);
  } catch {
    return false;
  }
}

function actualizarContadorCarrito() {
  try {
    const carrito = JSON.parse(localStorage.getItem('carrito')) || [];
    const totalItems = carrito.reduce((sum, p) => sum + p.cantidad, 0);
    
    const badges = document.querySelectorAll('.carrito-badge');
    badges.forEach(badge => {
      badge.textContent = totalItems;
      badge.style.display = totalItems > 0 ? 'block' : 'none';
    });
  } catch (e) {
    console.error('Error al actualizar contador:', e);
  }
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', async function() {
  // Sincronizar productos del backend si es posible
  if (typeof syncProductosConBackend === 'function') {
    await syncProductosConBackend();
  }
  
  // Renderizar productos
  renderProductosPorCategoria();
  
  // Actualizar contador del carrito
  actualizarContadorCarrito();
});

// Exportar funciones para uso global
window.renderProductosPorCategoria = renderProductosPorCategoria;
window.agregarAlCarrito = agregarAlCarrito;
window.mostrarDetallesProducto = mostrarDetallesProducto;

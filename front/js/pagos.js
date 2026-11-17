const pagosIsProd = location.hostname.endsWith("onrender.com");
const PAGOS_API_URL = pagosIsProd
    ? "https://clemenss-perfumeria-backend.onrender.com/api"
    : (window.__API_URL__ || "http://localhost:8081/api");

document.addEventListener("DOMContentLoaded", function() {
    // Mostrar producto seleccionado desde el carrito
    const productoInfo = document.getElementById("producto-info");
    const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    let productosHTML = '';
    let total = 0;
    if (carrito.length > 0) {
        productosHTML += '<div style="display:flex; flex-direction:column; align-items:center;">';
        carrito.forEach(producto => {
            const itemTotal = (producto.precio || 0) * (producto.cantidad || 1);
            total += itemTotal;
            productosHTML += `
                <div class="producto-resumen">
                    <img src="${producto.imagen || 'images/perfume_232x210.png'}" alt="${producto.nombre}">
                    <p class="producto-nombre">${producto.nombre}</p>
                    <p>Cantidad: ${producto.cantidad || 1}</p>
                    <p class="producto-precio">Precio unitario: $${producto.precio ? producto.precio.toLocaleString() : '---'}</p>
                    <p>Subtotal: $${itemTotal.toLocaleString()}</p>
                </div>
            `;
        });
        productosHTML += `<div class="total-pagar">TOTAL A PAGAR: $${total.toLocaleString()}</div>`;
        productosHTML += '</div>';
        window.totalPago = total;
    } else {
        productosHTML = "<p>No hay productos seleccionados.</p>";
        window.totalPago = 0;
    }
    productoInfo.innerHTML = productosHTML;

    // darle formato a la tarjeta
    const numeroTarjeta = document.getElementById("numero");
    const nombreTarjeta = document.getElementById("nombre");
    const fechaExpiracion = document.getElementById("fecha-expiracion");
    const cvv = document.getElementById("codigo-seguridad");

    numeroTarjeta.addEventListener("input", function(e) {
        let value = e.target.value.replace(/\D/g, '')
        if (value.length > 0) {
            value = value.match(/.{1,4}/g).join(' ')
        }
        e.target.value = value
    })
    numeroTarjeta.addEventListener("blur", function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length !== 16 && value.length !== 0) {
            alert("El número de la tarjeta debe tener 16 dígitos.");
        }
    });

    fechaExpiracion.addEventListener("input", function(e) {
        let value = e.target.value.replace(/\D/g, '')
        if (value.length > 2) {
            value = value.substring(0,2) + '/' + value.substring(2,4);
        }
        e.target.value = value
        if (value.length === 5) {
            let mes = parseInt(value.substring(0,2));
            let anio = parseInt("20" + value.substring(3,5));
            const hoy = new Date();
            const mesactual= hoy.getMonth() + 1;
            const anioactual = hoy.getFullYear();
            if (mes < 1 || mes > 12) {
                alert("El mes debe estar entre 01 y 12.");
                e.target.value = "";
                return;
            }
            if (anio < anioactual || (anio === anioactual && mes < mesactual)) {
                alert("La tarjeta ha expirado.");
                e.target.value = "";
                return;
            }
        }
    })
    fechaExpiracion.addEventListener("blur", function(e) {
        let value = e.target.value;
        if (value.length !== 5 && value.length !== 0) {
            alert("La fecha de expiración debe tener el formato MM/AA.");
        }
    });

    cvv.addEventListener("input", function(e) {
        let value = e.target.value.replace(/\D/g, '')
        e.target.value = value
    })
    cvv.addEventListener("blur", function(e) {
        let value = e.target.value;
        if (value.length !== 3 && value.length !== 0) {
            alert("El código de seguridad debe tener 3 dígitos.");
        }
    });

    function guardarOrden(carrito){
        const numeroOrden = Math.floor(100000 + Math.random() * 900000);
        const orden = {
            items: carrito,
            fecha: new Date().toISOString(),
            id: numeroOrden,
        };
        localStorage.setItem("orden", JSON.stringify(orden));
        return numeroOrden;
    }

    async function confirmarCompraEnBackend(carrito) {
        const items = carrito.map((p) => ({
            productId: p.productoId,
            quantity: Number(p.cantidad) || 1,
            unitPrice: Number(p.precio) || 0
        }));

        // Calcular totales
        const subtotal = carrito.reduce((sum, p) => sum + (Number(p.precio) || 0) * (Number(p.cantidad) || 1), 0);
        const iva = subtotal * 0.19; // IVA del 19%
        const totalAmount = subtotal + iva;

        // Obtener datos de la tarjeta del formulario
        const nombreTarjeta = document.getElementById("nombre").value;
        const numeroTarjeta = document.getElementById("numero").value.replace(/\s/g, "");
        const fechaExpiracion = document.getElementById("fecha-expiracion").value;
        const codigoSeguridad = document.getElementById("codigo-seguridad").value;

        // Obtener el token JWT del localStorage
        const session = JSON.parse(localStorage.getItem("app.jwt") || "{}");
        const token = session.user?.token || "";

        const headers = {
            "Content-Type": "application/json"
        };
        
        // Agregar el token si existe
        if (token) {
            headers["Authorization"] = `Bearer ${token}`;
        }

        const payload = {
            items: items,
            subtotal: subtotal,
            iva: iva,
            totalAmount: totalAmount,
            cardName: nombreTarjeta,
            cardNumber: numeroTarjeta,
            cardExpiry: fechaExpiracion,
            cardCVV: codigoSeguridad
        };

        const res = await fetch(`${PAGOS_API_URL}/orders/create`, {
            method: "POST",
            headers: headers,
            body: JSON.stringify(payload),
        });

        let data = null;
        try {
            data = await res.json();
        } catch {}

        if (!res.ok || data?.ok === false) {
            const sinStock = Array.isArray(data?.sinStock) ? data.sinStock : [];
            if (sinStock.length > 0) {
                const detalle = sinStock
                    .map((x) => `${x.nombre}: disponibles ${x.disponible}, solicitados ${x.solicitado}`)
                    .join("\n");
                throw new Error(`Stock insuficiente:\n${detalle}`);
            }
            throw new Error(data?.error || data?.message || "No se pudo confirmar el pago.");
        }
    }

    let ordenNumero = document.getElementById("pago__orden");
    const botonPagarAnterior = document.getElementById("pagos__boton");
    const botonConfirmar = document.getElementById("pagos__boton-confirmar");
    
    botonConfirmar.addEventListener("click", async function(e) {
        e.preventDefault();
        
        // Validar que el carrito no esté vacío
        const carrito = JSON.parse(localStorage.getItem("carrito")) || [];
        if (carrito.length === 0) {
            alert("Tu carrito está vacío.");
            return;
        }

        const formulario = document.getElementById("pagos__formulario");
        const confirmacion = document.getElementById("pagos__confirmacion");
        botonConfirmar.disabled = true;
        botonConfirmar.textContent = "Procesando...";

        // Simular procesamiento (500ms)
        await new Promise(resolve => setTimeout(resolve, 500));

        try {
            // Confirmar la compra en el backend (crear orden y descontar stock automáticamente)
            await confirmarCompraEnBackend(carrito);
            
            // Generar número de aprobación aleatorio
            const numeroAprobacion = Math.floor(100000 + Math.random() * 900000);
            
            // Vaciar el carrito
            localStorage.removeItem("carrito");
            
            // Mostrar confirmación
            formulario.style.display = "none";
            confirmacion.style.display = "block";
            
            // Mostrar número de transacción
            ordenNumero.innerHTML = `
                <div style="font-size:20px;font-weight:bold;margin-bottom:20px;color:#FFD700;">Número de Transacción Aprobada:</div>
                <div style="font-size:36px;font-weight:bold;margin-bottom:30px;color:#90EE90;letter-spacing:2px;">${numeroAprobacion}</div>
                <div style="font-size:16px;margin-bottom:40px;color:#E0E0E0;">Total pagado: $${window.totalPago ? window.totalPago.toLocaleString() : "0"}</div>
                <div style="font-size:14px;color:#FFD700;animation:blink 1.5s infinite;">Redirigiendo al menú principal en 5 segundos...</div>
            `;
            
            // Redirigir después de 5 segundos
            setTimeout(function() {
                window.location.href = "index.html";
            }, 5000);
        } catch (err) {
            alert(err.message || "Error al procesar el pago.");
            botonConfirmar.disabled = false;
            botonConfirmar.textContent = "Oprime para Pagar";
        }
    });

    // Mantener el evento del botón anterior por compatibilidad (aunque está oculto)
    botonPagarAnterior.addEventListener("click", async function(e) {
        e.preventDefault();
        // No hacer nada - solo está para compatibilidad
    })
});

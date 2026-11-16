<!-- Configuración de API para Render -->
<script>
  // En desarrollo: http://localhost:8081
  // En producción: Tu URL de Render (ej: https://ecommerce-backend-xxxx.onrender.com)
  window.__API_URL__ = window.location.hostname === 'localhost' 
    ? 'http://localhost:8081/api' 
    : 'https://ecommerce-backend-xxxx.onrender.com/api';
  
  // Logging para debugging
  console.log('API URL:', window.__API_URL__);
</script>

// Configuración de API para Render
// En desarrollo: http://localhost:8081
// En producción: https://e-commerce-fr3k.onrender.com
window.__API_URL__ = window.location.hostname === 'localhost' 
  ? 'http://localhost:8081' 
  : 'https://e-commerce-fr3k.onrender.com';

// Logging para debugging
console.log('API URL:', window.__API_URL__);
console.log('Hostname:', window.location.hostname);

# 🚀 GUÍA RÁPIDA - Clemmens E-Commerce

## ✨ Estado Actual
✅ **TODO FUNCIONANDO PERFECTAMENTE**

- 21 perfumes de Clemmens en PostgreSQL
- Backend Spring Boot en puerto 8081
- Frontend Clemmens en puerto 5500
- Consultas directas de BD a través de API REST

---

## 🎯 Para Ejecutar el Sistema

### Opción 1: Terminal Rápida (Recomendado)

**Terminal 1 - Backend (PowerShell)**:
```powershell
cd C:\java\proyectoprofe\ecommerceclase\back
.\run-backend.bat
```

**Terminal 2 - Frontend (PowerShell)**:
```powershell
cd C:\java\proyectoprofe\ecommerceclase\front
npx live-server
```

Luego: Abre **http://localhost:5500** en el navegador

---

## 📦 Productos en la BD

### Para Ella (10 productos)
1. La Vie Est Belle - $500,000
2. Flowerbomb - $230,000
3. Chanel No 5 - $450,000
4. Good Girl - $420,000
5. Black Opium - $340,000
6. J'adore - $480,000
7. Sì (Giorgio Armani) - $380,000
8. Alien (Mugler) - $350,000
9. Portrait of a Lady - $400,000
10. Parfums de Marly Valaya - $390,000

### Para Él (11 productos)
1. Le Male Elixir - $400,000
2. Bleu de Chanel - $1,000,000
3. One Million - $480,000
4. Dior Sauvage - $370,000
5. Creed Aventus - $450,000
6. Acqua Di Gio Parfum - $400,000
7. Le Labo Santal 33 - $450,000
8. Afnan 9pm - $320,000
9. Boss - $390,000
10. Eau De Cedrat - $310,000
11. (Un producto más según la inserción)

---

## 🔌 API Endpoints

```
GET http://localhost:8081/api/products
↓
Retorna array de 21 productos con estructura:
{
  "id": 5,
  "name": "La Vie Est Belle",
  "sku": "PERFUME-001",
  "price": 500000,
  "stock": 10,
  "isActive": true,
  "category": { "id": 1, "name": "Para ella" }
}
```

---

## 🛠️ Si Hay Problemas

### "No puedo ver los productos"
1. Verifica que Backend esté corriendo (puerto 8081)
2. Abre DevTools (F12) → Consola
3. Busca: `✅ Productos obtenidos del backend`

### "CORS Error"
- Reinicia el Backend
- Asegúrate de que Frontend está en puerto 5500-5502

### "Base de Datos no conecta"
- Verifica: `postgresql://localhost:5432/ecommerce`
- Usuario: `postgres`
- Contraseña está en: `back/src/main/resources/application.properties`

---

## 📊 Archivos Importantes

| Archivo | Descripción |
|---------|-------------|
| `front/js/productos.js` | Sincronización con BD |
| `back/src/main/java/.../SecurityConfig.java` | CORS configuration |
| `back/src/main/resources/application.properties` | DB connection |
| `CLEMMENS_SETUP_COMPLETADO.md` | Documentación detallada |

---

## ✅ Checklist Diario

- [ ] Backend corriendo en puerto 8081
- [ ] Frontend corriendo en puerto 5500
- [ ] Productos cargan desde BD
- [ ] Puedo agregar al carrito
- [ ] Console sin errores CORS

---

**Versión**: 1.0  
**Actualizado**: 2024-11-15  
**Estado**: ✅ Producción Local Listo

package com.parquesoft.ecommerce.controller;

import com.parquesoft.ecommerce.entity.ProductEntity;
import com.parquesoft.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Tag(name = "2. Productos", description = "Endpoints para catalogo y administracion de productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Listar catalogo de productos activos", description = "Acceso publico utilizado en la tienda")
    @ApiResponse(responseCode = "200", description = "Catalogo obtenido exitosamente")
    public ResponseEntity<?> getAllActiveProducts() {
        List<ProductEntity> products = productService.getAllActiveProducts();
        
        List<Map<String, Object>> response = products.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("nombre", p.getName());
            map.put("precio", p.getPrice());
            map.put("stock", p.getStock());
            map.put("imagen", p.getImage() != null ? p.getImage() : "images/perfume_232x210.png");
            map.put("categoria", p.getCategory() != null ? p.getCategory().getName() : "Otros");
            map.put("descripcion", p.getDescription());
            map.put("rating", p.getRating() != null ? p.getRating() : 4.5);
            map.put("reviews", p.getReviews() != null ? p.getReviews() : 0);
            map.put("sku", p.getSku());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar producto por codigo SKU")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductEntity> getBySku(@PathVariable String sku) {
        return productService.getBySku(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductEntity> getById(@PathVariable Long id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar productos por rango de precio (JPQL)", description = "Ejemplo: /api/products/search?min=50&max=300")
    public ResponseEntity<List<ProductEntity>> searchByPrice(
            @RequestParam(defaultValue = "0") double min,
            @RequestParam(defaultValue = "999999") double max
    ) {
        return ResponseEntity.ok(productService.searchByPriceRange(min, max));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o SKU duplicado")
    })
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto por ID", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar producto (Borrado logico)", security = @SecurityRequirement(name = "BearerAuth"))
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @Operation(summary = "Verificar estado del servicio de productos")
    public String checkStatus() {
        return productService.getStatusMessage();
    }

    @GetMapping("/discount")
    @Operation(summary = "Calcular descuento de producto")
    public String calculateDiscount(@RequestParam double price, @RequestParam double percentage) {
        double finalPrice = productService.calculateDiscount(price, percentage);
        return "Precio original: $" + price + " | Precio con " + percentage + "% de descuento: $" + finalPrice;
    }

    @PatchMapping("/{id}/reduce-stock")
    @Operation(summary = "Descontar stock de un producto", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<?> reduceStock(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            if (quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponse("error", "La cantidad debe ser mayor a 0"));
            }
            ProductEntity updated = productService.reduceStock(id, quantity);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("error", "Producto no encontrado"));
        }
    }

    @PatchMapping("/batch/update-images")
    @Operation(summary = "Actualizar imágenes de múltiples productos")
    public ResponseEntity<?> updateBatchImages() {
        try {
            Map<String, String> updates = new HashMap<>();
            updates.put("One Millon", "images/category-thumb-6.jpg");
            updates.put("bleu de chanel", "images/category-thumb-7.jpg");
            updates.put("Le Male Elixir", "images/category-thumb-8.jpg");
            updates.put("Dior Sauvage", "images/product-thumb-13.png");
            updates.put("Creed", "images/product-thumb-14.png");
            updates.put("Acqua Di Gio Parfum", "images/product-thumb-15.png");
            updates.put("Le Labo Santal 33", "images/product-thumb-16.png");
            updates.put("Afnan 9pm", "images/product-thumb-17.png");
            updates.put("Boss", "images/product-thumb-18.png");
            updates.put("Eau De Cedrat", "images/product-thumb-21.png");

            List<Map<String, Object>> results = new ArrayList<>();
            for (Map.Entry<String, String> entry : updates.entrySet()) {
                List<ProductEntity> products = productService.getAllActiveProducts().stream()
                        .filter(p -> p.getName().equalsIgnoreCase(entry.getKey()))
                        .collect(Collectors.toList());
                
                for (ProductEntity product : products) {
                    product.setImage(entry.getValue());
                    productService.updateProduct(product.getId(), product);
                    Map<String, Object> result = new HashMap<>();
                    result.put("nombre", entry.getKey());
                    result.put("imagen", entry.getValue());
                    result.put("status", "actualizado");
                    results.add(result);
                }
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error", "Error al actualizar imágenes: " + e.getMessage()));
        }
    }

    // Clase auxiliar para respuestas de error
    public static class ErrorResponse {
        private String status;
        private String message;

        public ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}

package com.parquesoft;

public interface Vendible {
    Categoria seleccionarCategoria();
    Producto seleccionarProducto();
    double obtenerPrecio();
    int seleccionarCantidad();
    double calcularPrecioVenta();
    void disminuirStock(int cantidad);
}

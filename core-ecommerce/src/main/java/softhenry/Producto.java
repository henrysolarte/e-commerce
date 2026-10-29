package softhenry;

public class Producto implements Vendible {
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;
    private int cantidadVenta;

    public Producto(String nombre, double precio, int stock, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Arreglos de Productos por Categoría
    public static final Producto[] PRODUCTOS_COMPUTO = {
        new Producto("Laptop Dell i7", 899.99, 10, Categoria.EQUIPOS_COMPUTO),
        new Producto("PC Gamer ASUS", 2499.99, 10, Categoria.EQUIPOS_COMPUTO),
        new Producto("Monitor LG 27\"", 399.99, 10, Categoria.EQUIPOS_COMPUTO),
        new Producto("Teclado Mecanico RGB", 149.99, 10, Categoria.EQUIPOS_COMPUTO),
        new Producto("Mouse Logitech MX", 99.99, 10, Categoria.EQUIPOS_COMPUTO)
    };

    public static final Producto[] PRODUCTOS_RED = {
        new Producto("Router TP-Link WiFi 6", 179.99, 10, Categoria.EQUIPOS_RED),
        new Producto("Switch Cisco 24 puertos", 599.99, 10, Categoria.EQUIPOS_RED),
        new Producto("Modem NETGEAR", 149.99, 10, Categoria.EQUIPOS_RED),
        new Producto("Cable Ethernet Cat6", 19.99, 10, Categoria.EQUIPOS_RED),
        new Producto("Access Point Ubiquiti", 299.99, 10, Categoria.EQUIPOS_RED)
    };

    @Override
    public Categoria seleccionarCategoria() {
        return categoria;
    }

    @Override
    public Producto seleccionarProducto() {
        return this;
    }

    @Override
    public double obtenerPrecio() {
        return precio;
    }

    @Override
    public int seleccionarCantidad() {
        return cantidadVenta;
    }

    @Override
    public double calcularPrecioVenta() {
        return precio * cantidadVenta;
    }

    @Override
    public void disminuirStock(int cantidad) {
        if (cantidad <= stock) {
            stock -= cantidad;
        } else {
            System.out.println("Error: No hay suficiente stock. Stock disponible: " + stock);
        }
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerDescripcion() {
        return "Producto: " + nombre + " - Categoría: " + categoria.getNombre();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCantidadVenta(int cantidad) {
        this.cantidadVenta = cantidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + " - $" + String.format("%.2f", precio) + " (Stock: " + stock + ")";
    }
}

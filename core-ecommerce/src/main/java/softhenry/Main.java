package softhenry;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static Scanner scanner = new Scanner(System.in);
    private static List<Producto> carrito = new ArrayList<>();
    private static double totalVenta = 0;

    public static void main(String[] args) {
        mostrarBienvenida();
        menuPrincipal();
    }

    private static void mostrarBienvenida() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        BIENVENIDO A SOFTHENRY               ║");
        System.out.println("║  VAMOS A MOSTRAR LAS CATEGORIAS DE PRODUCTOS ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
    }

    private static void menuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("\n╔════════════════════════════════════════════════╗");
                System.out.println("║          MENÚ PRINCIPAL - SOFTHENRY           ║");
                System.out.println("╚════════════════════════════════════════════════╝");
                System.out.println("1. Comprar Productos");
                System.out.println("2. Reporte de Stock Vacío");
                System.out.println("3. Salir");
                System.out.println("══════════════════════════════════════════════════");
                System.out.print("¿Qué deseas hacer? (1, 2 o 3): ");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        logger.info("Usuario seleccionó: Comprar Productos");
                        seleccionarCategoria();
                        break;
                    case 2:
                        logger.info("Usuario seleccionó: Reporte de Stock Vacío");
                        mostrarReporteStockVacio();
                        break;
                    case 3:
                        logger.info("Usuario seleccionó: Salir del programa");
                        System.out.println("\n👋 ¡Hasta luego! Gracias por visitar SOFTHENRY.\n");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        logger.log(Level.WARNING, "Usuario ingresó opción inválida en menú principal: " + opcion);
                        System.out.println("❌ Opción inválida. Intenta de nuevo.");
                }
            } catch (InputMismatchException e) {
                logger.log(Level.SEVERE, "Error de entrada: Usuario ingresó un dato no numérico en menú principal. " + e.getMessage());
                System.out.println("❌ ERROR: Debes ingresar un número válido (1, 2 o 3)");
                scanner.nextLine(); // Limpiar el buffer
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error inesperado en menú principal: " + e.getMessage(), e);
                System.out.println("❌ ERROR: Ha ocurrido un error inesperado. Intenta de nuevo.");
            }
        }
    }

    private static void mostrarReporteStockVacio() {
        try {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        REPORTE DE STOCK VACÍO                 ║");
            System.out.println("╚════════════════════════════════════════════════╝");

            System.out.println("\n📦 EQUIPOS DE COMPUTO - Stock Vacío:");
            System.out.println("══════════════════════════════════════════════");
            
            List<String> productosComputoSinStock = new ArrayList<>();
            long countComputo = Arrays.stream(Producto.PRODUCTOS_COMPUTO)
                .filter(p -> p.getStock() == 0)
                .peek(p -> {
                    System.out.println("  • " + p.obtenerNombre() + " (Stock: " + p.getStock() + ")");
                    productosComputoSinStock.add(p.obtenerNombre());
                })
                .count();

            if (countComputo == 0) {
                System.out.println("  ✅ No hay productos sin stock en esta categoría.");
            }

            System.out.println("\n🌐 EQUIPOS DE RED - Stock Vacío:");
            System.out.println("══════════════════════════════════════════════");
            
            List<String> productosRedSinStock = new ArrayList<>();
            long countRed = Arrays.stream(Producto.PRODUCTOS_RED)
                .filter(p -> p.getStock() == 0)
                .peek(p -> {
                    System.out.println("  • " + p.obtenerNombre() + " (Stock: " + p.getStock() + ")");
                    productosRedSinStock.add(p.obtenerNombre());
                })
                .count();

            if (countRed == 0) {
                System.out.println("  ✅ No hay productos sin stock en esta categoría.");
            }

            System.out.println("\n══════════════════════════════════════════════");
            System.out.println("📊 RESUMEN:");
            System.out.println("  Equipos de Computo sin stock: " + countComputo);
            if (countComputo > 0) {
                for (String producto : productosComputoSinStock) {
                    System.out.println("    - " + producto);
                }
            }
            System.out.println("  Equipos de Red sin stock: " + countRed);
            if (countRed > 0) {
                for (String producto : productosRedSinStock) {
                    System.out.println("    - " + producto);
                }
            }
            System.out.println("  Total de productos sin stock: " + (countComputo + countRed));
            System.out.println("══════════════════════════════════════════════\n");

            logger.info("Reporte de stock vacío generado exitosamente");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al generar reporte de stock: " + e.getMessage(), e);
            System.out.println("❌ ERROR: No se pudo generar el reporte.");
        }
    }

    private static void seleccionarCategoria() {
        try {
            System.out.println("📦 CATEGORÍAS DISPONIBLES:");
            System.out.println("══════════════════════════════════════════════");
            System.out.println("1. " + Categoria.EQUIPOS_COMPUTO.getNombre());
            System.out.println("2. " + Categoria.EQUIPOS_RED.getNombre());
            System.out.println("══════════════════════════════════════════════");
            System.out.print("\n¿Qué categoría deseas consultar? (1 o 2): ");

            int opcionCategoria = scanner.nextInt();
            scanner.nextLine();

            Producto[] productosSeleccionados;
            
            switch (opcionCategoria) {
                case 1:
                    logger.info("Usuario seleccionó categoría: Equipos de Computo");
                    productosSeleccionados = Producto.PRODUCTOS_COMPUTO;
                    mostrarProductosPorCategoria(productosSeleccionados);
                    break;
                case 2:
                    logger.info("Usuario seleccionó categoría: Equipos de Red");
                    productosSeleccionados = Producto.PRODUCTOS_RED;
                    mostrarProductosPorCategoria(productosSeleccionados);
                    break;
                default:
                    logger.log(Level.WARNING, "Usuario ingresó categoría inválida: " + opcionCategoria);
                    System.out.println("❌ Opción inválida.\n");
                    seleccionarCategoria();
            }
        } catch (InputMismatchException e) {
            logger.log(Level.SEVERE, "Error de entrada: Usuario ingresó dato no numérico en selección de categoría. " + e.getMessage());
            System.out.println("❌ ERROR: Debes ingresar un número válido (1 o 2)");
            scanner.nextLine();
            seleccionarCategoria();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inesperado en selección de categoría: " + e.getMessage(), e);
            System.out.println("❌ ERROR: Ha ocurrido un error. Volviendo al menú principal...");
        }
    }

    private static void mostrarProductosPorCategoria(Producto[] productos) {
        try {
            System.out.println("\n💻 PRODUCTOS - " + productos[0].getCategoria().getNombre().toUpperCase());
            System.out.println("══════════════════════════════════════════════");

            for (int i = 0; i < productos.length; i++) {
                System.out.println((i + 1) + ". " + productos[i]);
            }

            System.out.println("\n❓ ¿Deseas comprar algún producto? (si/no): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            switch (respuesta) {
                case "si":
                case "s":
                    logger.info("Usuario desea comprar productos");
                    comprarProductos(productos);
                    break;
                case "no":
                case "n":
                    logger.info("Usuario canceló la compra");
                    System.out.println("\n👋 Volviendo al menú principal...\n");
                    break;
                default:
                    logger.log(Level.WARNING, "Usuario ingresó respuesta inválida en compra: " + respuesta);
                    System.out.println("❌ Opción inválida.");
                    mostrarProductosPorCategoria(productos);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al mostrar productos por categoría: " + e.getMessage(), e);
            System.out.println("❌ ERROR: Ha ocurrido un error al mostrar productos.");
        }
    }

    private static void comprarProductos(Producto[] productos) {
        boolean continuarComprando = true;

        while (continuarComprando) {
            try {
                System.out.println("\n🛍️  PRODUCTOS DISPONIBLES PARA COMPRAR:");
                System.out.println("══════════════════════════════════════════════");

                for (int i = 0; i < productos.length; i++) {
                    System.out.println((i + 1) + ". " + productos[i]);
                }

                System.out.print("\n¿Qué producto deseas? (número): ");
                int opcionProducto = scanner.nextInt();
                scanner.nextLine();

                switch (opcionProducto) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        if (opcionProducto <= productos.length) {
                            Producto productoSeleccionado = productos[opcionProducto - 1];
                            logger.info("Usuario seleccionó producto: " + productoSeleccionado.obtenerNombre());

                            System.out.print("\n¿Cuántas unidades deseas? (cantidad): ");
                            int cantidad = scanner.nextInt();
                            scanner.nextLine();

                            if (cantidad <= 0) {
                                logger.log(Level.WARNING, "Usuario ingresó cantidad inválida (cero o negativa): " + cantidad);
                                System.out.println("❌ La cantidad debe ser mayor a 0.");
                                break;
                            }

                            if (cantidad > productoSeleccionado.getStock()) {
                                logger.log(Level.WARNING, "Stock insuficiente: Usuario intentó comprar " + cantidad + 
                                           " unidades de " + productoSeleccionado.obtenerNombre() + 
                                           " (Stock disponible: " + productoSeleccionado.getStock() + ")");
                                System.out.println("\n❌ ¡ERROR DE STOCK!");
                                System.out.println("══════════════════════════════════════════════");
                                System.out.println("Cantidad solicitada: " + cantidad + " unidades");
                                System.out.println("Stock disponible: " + productoSeleccionado.getStock() + " unidades");
                                System.out.println("══════════════════════════════════════════════");
                                System.out.println("💡 Puedes comprar máximo " + productoSeleccionado.getStock() + " unidades de este producto.\n");
                                break;
                            }

                            productoSeleccionado.setCantidadVenta(cantidad);
                            carrito.add(productoSeleccionado);
                            totalVenta += productoSeleccionado.calcularPrecioVenta();

                            logger.info("Producto agregado al carrito: " + productoSeleccionado.obtenerNombre() + 
                                       " x" + cantidad + " = $" + String.format("%.2f", productoSeleccionado.calcularPrecioVenta()));

                            System.out.println("\n✅ Producto agregado al carrito: " + productoSeleccionado.obtenerNombre() + " x" + cantidad);
                            System.out.println("   Subtotal: $" + String.format("%.2f", productoSeleccionado.calcularPrecioVenta()));

                            System.out.print("\n¿Deseas agregar otro producto? (si/no): ");
                            String respuesta = scanner.nextLine().trim().toLowerCase();

                            switch (respuesta) {
                                case "si":
                                case "s":
                                    continuarComprando = true;
                                    break;
                                case "no":
                                case "n":
                                    continuarComprando = false;
                                    break;
                                default:
                                    continuarComprando = false;
                            }
                        }
                        break;
                    default:
                        logger.log(Level.WARNING, "Usuario ingresó número de producto inválido: " + opcionProducto);
                        System.out.println("❌ Opción inválida.");
                }
            } catch (InputMismatchException e) {
                logger.log(Level.SEVERE, "Error de entrada: Usuario ingresó dato no numérico en compra. " + e.getMessage());
                System.out.println("❌ ERROR: Debes ingresar un número válido");
                scanner.nextLine();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error inesperado en compra de productos: " + e.getMessage(), e);
                System.out.println("❌ ERROR: Ha ocurrido un error inesperado.");
            }
        }

        if (carrito.size() > 0) {
            finalizarVenta();
        }
    }

    private static void finalizarVenta() {
        try {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║              RESUMEN DE LA VENTA               ║");
            System.out.println("╚════════════════════════════════════════════════╝");

            for (Producto producto : carrito) {
                System.out.println("• " + producto.obtenerNombre() + " x" + producto.seleccionarCantidad() +
                                 " = $" + String.format("%.2f", producto.calcularPrecioVenta()));
            }

            System.out.println("══════════════════════════════════════════════");
            System.out.println("💰 TOTAL A PAGAR: $" + String.format("%.2f", totalVenta));
            System.out.println("══════════════════════════════════════════════");

            System.out.print("\n¿Confirmas la compra? (si/no): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            switch (confirmacion) {
                case "si":
                case "s":
                    procesarCompra();
                    break;
                case "no":
                case "n":
                    logger.log(Level.WARNING, "Usuario canceló la compra. Total: $" + String.format("%.2f", totalVenta));
                    System.out.println("\n❌ Compra cancelada. Volviendo al menú...\n");
                    carrito.clear();
                    totalVenta = 0;
                    break;
                default:
                    logger.log(Level.WARNING, "Usuario ingresó confirmación inválida: " + confirmacion);
                    System.out.println("❌ Opción inválida.");
                    finalizarVenta();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al finalizar venta: " + e.getMessage(), e);
            System.out.println("❌ ERROR: Ha ocurrido un error al finalizar la venta.");
        }
    }

    private static void procesarCompra() {
        try {
            System.out.println("\n⏳ Procesando compra...");

            for (Producto producto : carrito) {
                producto.disminuirStock(producto.seleccionarCantidad());
            }

            logger.info("COMPRA REALIZADA: Total = $" + String.format("%.2f", totalVenta) + 
                       ". Cantidad de productos: " + carrito.size());

            System.out.println("\n✅ ¡COMPRA REALIZADA CON ÉXITO!");
            System.out.println("══════════════════════════════════════════════");
            System.out.println("Monto pagado: $" + String.format("%.2f", totalVenta));
            System.out.println("══════════════════════════════════════════════");
            System.out.println("\n🎉 Gracias por comprar en SOFTHENRY.\n");

            carrito.clear();
            totalVenta = 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al procesar compra: " + e.getMessage(), e);
            System.out.println("❌ ERROR: Ha ocurrido un error al procesar la compra.");
        }
    }
}

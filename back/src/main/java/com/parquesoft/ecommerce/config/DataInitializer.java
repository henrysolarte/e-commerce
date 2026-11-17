package com.parquesoft.ecommerce.config;

import com.parquesoft.ecommerce.entity.CategoryEntity;
import com.parquesoft.ecommerce.entity.ProductEntity;
import com.parquesoft.ecommerce.repository.CategoryRepository;
import com.parquesoft.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen datos
        if (productRepository.count() > 0) {
            return; // Ya hay datos, no insertar
        }

        // Crear categorías
        CategoryEntity paraElla = new CategoryEntity();
        paraElla.setName("Para ella");
        paraElla.setDescription("Perfumes y fragancias para mujeres");
        paraElla = categoryRepository.save(paraElla);

        CategoryEntity paraEl = new CategoryEntity();
        paraEl.setName("Para él");
        paraEl.setDescription("Perfumes y fragancias para hombres");
        paraEl = categoryRepository.save(paraEl);

        // Crear productos para ella
        List<ProductEntity> productos = new ArrayList<>();

        ProductEntity p1 = new ProductEntity();
        p1.setName("La Vie Est Belle");
        p1.setPrice(500000.0f);
        p1.setSku("LVB001");
        p1.setCategory(paraElla);
        p1.setStock(50);
        p1.setImage("images/product-thumb-1.png");
        p1.setDescription("Una fragancia femenina, dulce y sofisticada, con notas de iris, jazmín y flor de azahar. Ideal para mujeres que buscan elegancia y alegría en cada aroma.");
        p1.setRating(4.5);
        p1.setReviews(222);
        productos.add(p1);

        ProductEntity p2 = new ProductEntity();
        p2.setName("Flowerbomb");
        p2.setPrice(230000.0f);
        p2.setSku("FLB001");
        p2.setCategory(paraElla);
        p2.setStock(45);
        p2.setImage("images/product-thumb-2.png");
        p2.setDescription("Explosión floral con matices de té y bergamota, envolvente y moderno. Perfecto para mujeres que desean destacar con un aroma vibrante y femenino.");
        p2.setRating(4.5);
        p2.setReviews(41);
        productos.add(p2);

        ProductEntity p3 = new ProductEntity();
        p3.setName("Chanel No 5");
        p3.setPrice(450000.0f);
        p3.setSku("CNL005");
        p3.setCategory(paraElla);
        p3.setStock(30);
        p3.setImage("images/product-thumb-3.png");
        p3.setDescription("El clásico eterno de Chanel, con notas de jazmín, rosa y aldehídos. Elegancia y sofisticación en cada gota, símbolo de la feminidad atemporal.");
        p3.setRating(4.5);
        p3.setReviews(32);
        productos.add(p3);

        ProductEntity p4 = new ProductEntity();
        p4.setName("Good Girl");
        p4.setPrice(420000.0f);
        p4.setSku("GGL001");
        p4.setCategory(paraElla);
        p4.setStock(40);
        p4.setImage("images/product-thumb-4.png");
        p4.setDescription("Una fragancia audaz y sensual, con notas de almendra, café y cacao. Para mujeres modernas y seguras que disfrutan de la dualidad entre lo dulce y lo intenso.");
        p4.setRating(4.5);
        p4.setReviews(222);
        productos.add(p4);

        ProductEntity p5 = new ProductEntity();
        p5.setName("Black Opium");
        p5.setPrice(340000.0f);
        p5.setSku("BOP001");
        p5.setCategory(paraElla);
        p5.setStock(35);
        p5.setImage("images/product-thumb-5.png");
        p5.setDescription("Aroma oriental y vibrante, con café, vainilla y flores blancas. Perfecto para noches especiales y mujeres que buscan un toque misterioso.");
        p5.setRating(4.5);
        p5.setReviews(222);
        productos.add(p5);

        ProductEntity p6 = new ProductEntity();
        p6.setName("J'adore");
        p6.setPrice(480000.0f);
        p6.setSku("JAD001");
        p6.setCategory(paraElla);
        p6.setStock(28);
        p6.setImage("images/product-thumb-6.png");
        p6.setDescription("Fragancia floral y luminosa, con notas de ylang-ylang, rosa y jazmín. Refleja la feminidad y el lujo en cada aplicación.");
        p6.setRating(4.5);
        p6.setReviews(222);
        productos.add(p6);

        ProductEntity p7 = new ProductEntity();
        p7.setName("Sì (Giorgio Armani)");
        p7.setPrice(380000.0f);
        p7.setSku("SGA001");
        p7.setCategory(paraElla);
        p7.setStock(32);
        p7.setImage("images/product-thumb-7.png");
        p7.setDescription("Notas de grosella negra, rosa y vainilla. Un perfume elegante y moderno para mujeres independientes y sofisticadas.");
        p7.setRating(4.5);
        p7.setReviews(222);
        productos.add(p7);

        ProductEntity p8 = new ProductEntity();
        p8.setName("Alien (Mugler)");
        p8.setPrice(350000.0f);
        p8.setSku("ALN001");
        p8.setCategory(paraElla);
        p8.setStock(25);
        p8.setImage("images/product-thumb-8.png");
        p8.setDescription("Fragancia misteriosa y envolvente, con jazmín sambac y ámbar blanco. Ideal para quienes buscan un aroma único y fuera de lo común.");
        p8.setRating(4.5);
        p8.setReviews(222);
        productos.add(p8);

        ProductEntity p9 = new ProductEntity();
        p9.setName("Portrait of a Lady");
        p9.setPrice(400000.0f);
        p9.setSku("POL001");
        p9.setCategory(paraElla);
        p9.setStock(20);
        p9.setImage("images/product-thumb-9.png");
        p9.setDescription("Notas orientales con rosa de Damasco, ámbar y sándalo. Un perfume lujoso y seductor para mujeres con carácter.");
        p9.setRating(4.5);
        p9.setReviews(188);
        productos.add(p9);

        ProductEntity p10 = new ProductEntity();
        p10.setName("Parfums de Marly Valaya");
        p10.setPrice(390000.0f);
        p10.setSku("PDM001");
        p10.setCategory(paraElla);
        p10.setStock(22);
        p10.setImage("images/product-thumb-10.png");
        p10.setDescription("Aroma fresco y sofisticado con notas de iris, magnolia y frambuesa. Perfecto para ocasiones especiales.");
        p10.setRating(4.5);
        p10.setReviews(156);
        productos.add(p10);

        ProductEntity p11 = new ProductEntity();
        p11.setName("360 para mujer");
        p11.setPrice(210000.0f);
        p11.setSku("360001");
        p11.setCategory(paraElla);
        p11.setStock(60);
        p11.setImage("images/product-thumb-11.png");
        p11.setDescription("Fragancia fresca y juvenil, con notas cítricas y florales. Accesible y versátil para cualquier momento del día.");
        p11.setRating(4.0);
        p11.setReviews(134);
        productos.add(p11);

        // Crear productos para él
        ProductEntity p12 = new ProductEntity();
        p12.setName("Le Male Elixir");
        p12.setPrice(400000.0f);
        p12.setSku("LME001");
        p12.setCategory(paraEl);
        p12.setStock(35);
        p12.setImage("images/product-thumb-12.png");
        p12.setDescription("Aroma masculino con notas de vainilla, bergamota y cedro. Elegante y versátil para cualquier ocasión.");
        p12.setRating(4.5);
        p12.setReviews(195);
        productos.add(p12);

        ProductEntity p13 = new ProductEntity();
        p13.setName("bleu de chanel");
        p13.setPrice(1000000.0f);
        p13.setSku("BDC001");
        p13.setCategory(paraEl);
        p13.setStock(20);
        p13.setImage("images/product-thumb-13.png");
        p13.setDescription("Clásico absoluto masculino con notas de limón, incienso y maderas. Premium para hombres sofisticados.");
        p13.setRating(5.0);
        p13.setReviews(312);
        productos.add(p13);

        ProductEntity p14 = new ProductEntity();
        p14.setName("One Millon");
        p14.setPrice(480000.0f);
        p14.setSku("OMN001");
        p14.setCategory(paraEl);
        p14.setStock(40);
        p14.setImage("images/product-thumb-14.png");
        p14.setDescription("Fragancia oriental y seductora, con notas de ambroxano y vainilla. Para hombres audaces.");
        p14.setRating(4.5);
        p14.setReviews(267);
        productos.add(p14);

        ProductEntity p15 = new ProductEntity();
        p15.setName("Dior Sauvage");
        p15.setPrice(370000.0f);
        p15.setSku("DIS001");
        p15.setCategory(paraEl);
        p15.setStock(45);
        p15.setImage("images/product-thumb-15.png");
        p15.setDescription("Aroma fresco y limpio con notas de pimienta, ambroxano y maderas. Versátil e irresistible.");
        p15.setRating(4.5);
        p15.setReviews(289);
        productos.add(p15);

        ProductEntity p16 = new ProductEntity();
        p16.setName("Creed Aventus");
        p16.setPrice(450000.0f);
        p16.setSku("CRA001");
        p16.setCategory(paraEl);
        p16.setStock(30);
        p16.setImage("images/product-thumb-16.png");
        p16.setDescription("Fragancia épica con notas de piña, manzana y maderas. Símbolo de éxito y poder.");
        p16.setRating(4.8);
        p16.setReviews(401);
        productos.add(p16);

        ProductEntity p17 = new ProductEntity();
        p17.setName("Acqua Di Gio Parfum");
        p17.setPrice(400000.0f);
        p17.setSku("ADG001");
        p17.setCategory(paraEl);
        p17.setStock(38);
        p17.setImage("images/product-thumb-17.png");
        p17.setDescription("Aroma fresco y acuático con notas de limón y cedro. Perfecto para el día a día.");
        p17.setRating(4.5);
        p17.setReviews(234);
        productos.add(p17);

        ProductEntity p18 = new ProductEntity();
        p18.setName("Le Labo Santal 33");
        p18.setPrice(450000.0f);
        p18.setSku("LLS001");
        p18.setCategory(paraEl);
        p18.setStock(25);
        p18.setImage("images/product-thumb-18.png");
        p18.setDescription("Fragancia de sándalo puro y sofisticado. Para hombres que aprecian la simplicidad elegante.");
        p18.setRating(4.7);
        p18.setReviews(178);
        productos.add(p18);

        ProductEntity p19 = new ProductEntity();
        p19.setName("Afnan 9pm");
        p19.setPrice(320000.0f);
        p19.setSku("AFN001");
        p19.setCategory(paraEl);
        p19.setStock(50);
        p19.setImage("images/product-thumb-19.png");
        p19.setDescription("Aroma oriental especiado con notas de café y ámbar. Perfecto para noches sofisticadas.");
        p19.setRating(4.4);
        p19.setReviews(145);
        productos.add(p19);

        ProductEntity p20 = new ProductEntity();
        p20.setName("Boss");
        p20.setPrice(390000.0f);
        p20.setSku("BOS001");
        p20.setCategory(paraEl);
        p20.setStock(42);
        p20.setImage("images/product-thumb-20.png");
        p20.setDescription("Clásico masculino con notas de jazmín, miel y maderas. Sofisticado y seductor.");
        p20.setRating(4.5);
        p20.setReviews(203);
        productos.add(p20);

        ProductEntity p21 = new ProductEntity();
        p21.setName("Eau De Cedrat");
        p21.setPrice(310000.0f);
        p21.setSku("EDC001");
        p21.setCategory(paraEl);
        p21.setStock(55);
        p21.setImage("images/product-thumb-21.png");
        p21.setDescription("Aroma fresco y limpio con notas de cítricos y cedro. Ligero y energético.");
        p21.setRating(4.3);
        p21.setReviews(121);
        productos.add(p21);

        // Guardar todos los productos
        productRepository.saveAll(productos);
        System.out.println("✓ Datos iniciales insertados: " + productos.size() + " productos");
    }
}

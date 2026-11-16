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
        productos.add(p1);

        ProductEntity p2 = new ProductEntity();
        p2.setName("Flowerbomb");
        p2.setPrice(230000.0f);
        p2.setSku("FLB001");
        p2.setCategory(paraElla);
        p2.setStock(45);
        productos.add(p2);

        ProductEntity p3 = new ProductEntity();
        p3.setName("Chanel No 5");
        p3.setPrice(450000.0f);
        p3.setSku("CNL005");
        p3.setCategory(paraElla);
        p3.setStock(30);
        productos.add(p3);

        ProductEntity p4 = new ProductEntity();
        p4.setName("Good Girl");
        p4.setPrice(420000.0f);
        p4.setSku("GGL001");
        p4.setCategory(paraElla);
        p4.setStock(40);
        productos.add(p4);

        ProductEntity p5 = new ProductEntity();
        p5.setName("Black Opium");
        p5.setPrice(340000.0f);
        p5.setSku("BOP001");
        p5.setCategory(paraElla);
        p5.setStock(35);
        productos.add(p5);

        ProductEntity p6 = new ProductEntity();
        p6.setName("J'adore");
        p6.setPrice(480000.0f);
        p6.setSku("JAD001");
        p6.setCategory(paraElla);
        p6.setStock(28);
        productos.add(p6);

        ProductEntity p7 = new ProductEntity();
        p7.setName("Sì (Giorgio Armani)");
        p7.setPrice(380000.0f);
        p7.setSku("SGA001");
        p7.setCategory(paraElla);
        p7.setStock(32);
        productos.add(p7);

        ProductEntity p8 = new ProductEntity();
        p8.setName("Alien (Mugler)");
        p8.setPrice(350000.0f);
        p8.setSku("ALN001");
        p8.setCategory(paraElla);
        p8.setStock(25);
        productos.add(p8);

        ProductEntity p9 = new ProductEntity();
        p9.setName("Portrait of a Lady");
        p9.setPrice(400000.0f);
        p9.setSku("POL001");
        p9.setCategory(paraElla);
        p9.setStock(20);
        productos.add(p9);

        ProductEntity p10 = new ProductEntity();
        p10.setName("Parfums de Marly Valaya");
        p10.setPrice(390000.0f);
        p10.setSku("PDM001");
        p10.setCategory(paraElla);
        p10.setStock(22);
        productos.add(p10);

        ProductEntity p11 = new ProductEntity();
        p11.setName("360 para mujer");
        p11.setPrice(210000.0f);
        p11.setSku("360001");
        p11.setCategory(paraElla);
        p11.setStock(60);
        productos.add(p11);

        // Crear productos para él
        ProductEntity p12 = new ProductEntity();
        p12.setName("Le Male Elixir");
        p12.setPrice(400000.0f);
        p12.setSku("LME001");
        p12.setCategory(paraEl);
        p12.setStock(35);
        productos.add(p12);

        ProductEntity p13 = new ProductEntity();
        p13.setName("bleu de chanel");
        p13.setPrice(1000000.0f);
        p13.setSku("BDC001");
        p13.setCategory(paraEl);
        p13.setStock(20);
        productos.add(p13);

        ProductEntity p14 = new ProductEntity();
        p14.setName("One Millon");
        p14.setPrice(480000.0f);
        p14.setSku("OMN001");
        p14.setCategory(paraEl);
        p14.setStock(40);
        productos.add(p14);

        ProductEntity p15 = new ProductEntity();
        p15.setName("Dior Sauvage");
        p15.setPrice(370000.0f);
        p15.setSku("DIS001");
        p15.setCategory(paraEl);
        p15.setStock(45);
        productos.add(p15);

        ProductEntity p16 = new ProductEntity();
        p16.setName("Creed Aventus");
        p16.setPrice(450000.0f);
        p16.setSku("CRA001");
        p16.setCategory(paraEl);
        p16.setStock(30);
        productos.add(p16);

        ProductEntity p17 = new ProductEntity();
        p17.setName("Acqua Di Gio Parfum");
        p17.setPrice(400000.0f);
        p17.setSku("ADG001");
        p17.setCategory(paraEl);
        p17.setStock(38);
        productos.add(p17);

        ProductEntity p18 = new ProductEntity();
        p18.setName("Le Labo Santal 33");
        p18.setPrice(450000.0f);
        p18.setSku("LLS001");
        p18.setCategory(paraEl);
        p18.setStock(25);
        productos.add(p18);

        ProductEntity p19 = new ProductEntity();
        p19.setName("Afnan 9pm");
        p19.setPrice(320000.0f);
        p19.setSku("AFN001");
        p19.setCategory(paraEl);
        p19.setStock(50);
        productos.add(p19);

        ProductEntity p20 = new ProductEntity();
        p20.setName("Boss");
        p20.setPrice(390000.0f);
        p20.setSku("BOS001");
        p20.setCategory(paraEl);
        p20.setStock(42);
        productos.add(p20);

        ProductEntity p21 = new ProductEntity();
        p21.setName("Eau De Cedrat");
        p21.setPrice(310000.0f);
        p21.setSku("EDC001");
        p21.setCategory(paraEl);
        p21.setStock(55);
        productos.add(p21);

        // Guardar todos los productos
        productRepository.saveAll(productos);
        System.out.println("✓ Datos iniciales insertados: " + productos.size() + " productos");
    }
}

-- Insertar o actualizar productos de "Para él" (categoria_id = 2)

-- Limpiar tabla si es necesario (comentar si ya tiene datos que quieres mantener)
-- DELETE FROM order_items WHERE product_id IN (SELECT id FROM products WHERE category_id = 2);
-- DELETE FROM products WHERE category_id = 2;

-- Verificar si ya existen y actualizar o insertar
INSERT INTO products (name, price, description, stock, image, category_id, sku, rating, reviews, is_active, created_at, updated_at) 
VALUES 
('Le Male Elixir', 400000, 'Aroma masculino y seductor, con notas de lavanda, menta y vainilla. Para hombres que buscan destacar con personalidad y frescura.', 60, 'images/product-thumb-12.png', 2, 'LME001', 4.5, 222, true, NOW(), NOW()),
('Bleu de Chanel', 1000000, 'Fragancia fresca y amaderada, con notas de cítricos y sándalo. Elegancia y libertad para el hombre moderno.', 35, 'images/product-thumb-13.png', 2, 'BDC001', 4.5, 222, true, NOW(), NOW()),
('One Million', 480000, 'Aroma intenso y especiado, con mandarina, canela y cuero. Para hombres audaces y con espíritu ganador.', 40, 'images/product-thumb-14.png', 2, 'OMN001', 4.5, 222, true, NOW(), NOW()),
('Dior Sauvage', 370000, 'Fragancia fresca y salvaje, con bergamota y ambroxan. Inspirada en la naturaleza y la libertad masculina.', 45, 'images/product-thumb-15.png', 2, 'DIS001', 4.5, 222, true, NOW(), NOW()),
('Creed Aventus', 450000, 'Perfume sofisticado y potente, con piña, musgo y madera. Para líderes y hombres de éxito.', 30, 'images/product-thumb-16.png', 2, 'CRA001', 4.5, 222, true, NOW(), NOW()),
('Acqua Di Gio Parfum', 400000, 'Aroma marino y fresco, con notas de bergamota y pachulí. Perfecto para el hombre activo y elegante.', 38, 'images/product-thumb-17.png', 2, 'ADG001', 4.5, 222, true, NOW(), NOW()),
('Le Labo Santal 33', 450000, 'Fragancia unisex, amaderada y especiada, con sándalo y cardamomo. Para quienes buscan un aroma distintivo y moderno.', 25, 'images/product-thumb-18.png', 2, 'LLS001', 4.5, 222, true, NOW(), NOW()),
('Afnan 9pm', 320000, 'Aroma oriental y dulce, con notas de manzana, lavanda y vainilla. Ideal para noches especiales y hombres modernos.', 50, 'images/product-thumb-19.png', 2, 'AFN001', 4.5, 180, true, NOW(), NOW()),
('Boss', 390000, 'Fragancia fresca y elegante, con notas de manzana, canela y sándalo. Para hombres seguros y sofisticados.', 42, 'images/product-thumb-20.png', 2, 'BOS001', 4.5, 210, true, NOW(), NOW()),
('Eau De Cedrat', 310000, 'Aroma cítrico y vibrante, con notas de cedro y menta. Perfecto para el hombre activo y enérgico.', 55, 'images/product-thumb-21.png', 2, 'EDC001', 4.5, 95, true, NOW(), NOW())
ON CONFLICT (sku) DO UPDATE SET
  name = EXCLUDED.name,
  price = EXCLUDED.price,
  description = EXCLUDED.description,
  stock = EXCLUDED.stock,
  image = EXCLUDED.image,
  rating = EXCLUDED.rating,
  reviews = EXCLUDED.reviews,
  updated_at = NOW();

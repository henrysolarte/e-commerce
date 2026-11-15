-- Insertar categorías
INSERT INTO categories (name, description, is_active) 
VALUES 
  ('Electrónica', 'Productos electrónicos', true),
  ('Ropa', 'Prendas de vestir', true),
  ('Libros', 'Libros y publicaciones', true)
ON CONFLICT (name) DO NOTHING;

-- Insertar productos
INSERT INTO products (name, sku, price, stock, is_active, category_id) 
VALUES 
  ('Laptop Dell XPS', 'DELL-XPS-001', 999.99, 50, true, (SELECT id FROM categories WHERE name='Electrónica' LIMIT 1)),
  ('Mouse Logitech', 'LOG-MOUSE-01', 49.99, 100, true, (SELECT id FROM categories WHERE name='Electrónica' LIMIT 1)),
  ('Teclado Mecánico', 'MECH-KEY-01', 129.99, 75, true, (SELECT id FROM categories WHERE name='Electrónica' LIMIT 1)),
  ('Camiseta Básica', 'CAMI-001', 29.99, 200, true, (SELECT id FROM categories WHERE name='Ropa' LIMIT 1)),
  ('Pantalón Jean', 'JEAN-001', 59.99, 150, true, (SELECT id FROM categories WHERE name='Ropa' LIMIT 1)),
  ('Clean Code', 'BOOK-CC-001', 39.99, 30, true, (SELECT id FROM categories WHERE name='Libros' LIMIT 1)),
  ('Design Patterns', 'BOOK-DP-001', 49.99, 25, true, (SELECT id FROM categories WHERE name='Libros' LIMIT 1));

-- Verificar
SELECT 'Categorías insertadas:' as info, COUNT(*) as total FROM categories;
SELECT 'Productos insertados:' as info, COUNT(*) as total FROM products;

-- Limpiar la tabla users completamente
DELETE FROM users WHERE 1=1;

-- Opcional: resetear el secuencial si existe
ALTER SEQUENCE IF EXISTS users_id_seq RESTART WITH 1;

-- =====================================================
-- SISTEMA DE GESTIÓN DE ALMACÉN - TECNOANDES PERÚ S.A.C.
-- Base de datos: sistema_almacen_tecnoAndes
-- =====================================================

DROP DATABASE IF EXISTS sistema_almacen_tecnoAndes;
CREATE DATABASE sistema_almacen_tecnoAndes
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sistema_almacen_tecnoAndes;

-- =====================================================
-- 1. TABLAS DE SEGURIDAD
-- =====================================================

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(100),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB;

CREATE TABLE usuario_rol (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    fecha_asignacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- =====================================================
-- 2. TABLAS DE CATÁLOGOS
-- =====================================================

CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE proveedores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    razon_social VARCHAR(150) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    email VARCHAR(100),
    contacto VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_ruc (ruc)
) ENGINE=InnoDB;

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dni_ruc VARCHAR(11) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    email VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_dni_ruc (dni_ruc)
) ENGINE=InnoDB;

CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    categoria_id BIGINT NOT NULL,
    precio_compra DECIMAL(10,2) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 5,
    unidad_medida VARCHAR(20) DEFAULT 'UNIDAD',
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    INDEX idx_codigo (codigo),
    INDEX idx_categoria (categoria_id),
    INDEX idx_stock (stock)
) ENGINE=InnoDB;

-- =====================================================
-- 3. TABLAS DE MOVIMIENTOS
-- =====================================================

CREATE TABLE entradas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    proveedor_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0,
    estado VARCHAR(20) DEFAULT 'REGISTRADO',
    observaciones VARCHAR(255),
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    INDEX idx_fecha (fecha),
    INDEX idx_proveedor (proveedor_id)
) ENGINE=InnoDB;

CREATE TABLE detalle_entrada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entrada_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (entrada_id) REFERENCES entradas(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    INDEX idx_entrada (entrada_id),
    INDEX idx_producto (producto_id)
) ENGINE=InnoDB;

CREATE TABLE salidas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_documento VARCHAR(20) NOT NULL UNIQUE,
    cliente_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0,
    estado VARCHAR(20) DEFAULT 'REGISTRADO',
    observaciones VARCHAR(255),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    INDEX idx_fecha (fecha),
    INDEX idx_cliente (cliente_id)
) ENGINE=InnoDB;

CREATE TABLE detalle_salida (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    salida_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (salida_id) REFERENCES salidas(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    INDEX idx_salida (salida_id),
    INDEX idx_producto (producto_id)
) ENGINE=InnoDB;

CREATE TABLE movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT NOT NULL,
    tipo VARCHAR(10) NOT NULL COMMENT 'ENTRADA o SALIDA',
    cantidad INT NOT NULL,
    stock_anterior INT NOT NULL,
    stock_nuevo INT NOT NULL,
    referencia_id BIGINT COMMENT 'ID de entrada o salida',
    referencia_tipo VARCHAR(20) COMMENT 'ENTRADA o SALIDA',
    usuario_id BIGINT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    observacion VARCHAR(255),
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    INDEX idx_producto (producto_id),
    INDEX idx_fecha (fecha),
    INDEX idx_tipo (tipo)
) ENGINE=InnoDB;

-- =====================================================
-- 4. DATOS INICIALES
-- =====================================================

INSERT INTO roles (nombre, descripcion) VALUES 
('ROLE_ADMIN', 'Administrador del sistema - Acceso total'),
('ROLE_USUARIO', 'Usuario estándar - Acceso a módulos operativos');

INSERT INTO usuarios (username, password, email, nombre_completo, telefono, activo) 
VALUES (
    'admin', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi',
    'admin@tecnoandes.com', 
    'Administrador del Sistema',
    '999888777',
    TRUE
);

INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);

INSERT INTO categorias (nombre, descripcion) VALUES 
('Laptops', 'Computadoras portátiles de todas las marcas'),
('Computadoras de Escritorio', 'PCs de escritorio y todo en uno'),
('Monitores', 'Pantallas y monitores de todas las pulgadas'),
('Periféricos', 'Teclados, mouse, audífonos, parlantes'),
('Componentes Internos', 'Procesadores, RAM, discos, tarjetas'),
('Accesorios', 'Cables, adaptadores, fundas, bases');

INSERT INTO proveedores (ruc, razon_social, direccion, telefono, email, contacto) VALUES 
('20123456789', 'Distribuidora Tech Perú S.A.C.', 'Av. Wilson 1234, Lima', '014567890', 'ventas@techperu.com', 'Carlos Ramírez'),
('20987654321', 'Importaciones Digitales E.I.R.L.', 'Jr. Paruro 567, Lima', '014321098', 'contacto@impdigital.com', 'Ana Torres');

INSERT INTO clientes (dni_ruc, nombre, direccion, telefono, email) VALUES 
('12345678', 'Juan Pérez García', 'Av. Arequipa 123, Lima', '987654321', 'juan.perez@gmail.com'),
('87654321', 'María López Fernández', 'Calle Los Pinos 456, Surco', '912345678', 'maria.lopez@gmail.com'),
('20111222333', 'Empresa ABC S.A.C.', 'Av. Javier Prado 789, San Isidro', '014445555', 'compras@abc.com');

INSERT INTO productos (codigo, nombre, descripcion, categoria_id, precio_compra, precio_venta, stock, stock_minimo, unidad_medida) VALUES 
('LAP-001', 'Laptop HP Pavilion 15', 'Laptop HP 15.6" Core i5 8GB RAM 512GB SSD', 1, 1800.00, 2400.00, 10, 3, 'UNIDAD'),
('LAP-002', 'Laptop Lenovo IdeaPad 3', 'Laptop Lenovo 15.6" Ryzen 5 8GB RAM 256GB SSD', 1, 1500.00, 2000.00, 8, 3, 'UNIDAD'),
('MON-001', 'Monitor LG 24" Full HD', 'Monitor LG 24MK430H 24" IPS Full HD', 3, 450.00, 650.00, 15, 5, 'UNIDAD'),
('MON-002', 'Monitor Samsung 27" Curvo', 'Monitor Samsung C27F390 27" Curvo Full HD', 3, 700.00, 950.00, 5, 2, 'UNIDAD'),
('PER-001', 'Teclado Mecánico Redragon', 'Teclado Mecánico Redragon Kumara K552 RGB', 4, 120.00, 180.00, 25, 10, 'UNIDAD'),
('PER-002', 'Mouse Logitech G203', 'Mouse Gamer Logitech G203 Prodigy 8000 DPI', 4, 80.00, 130.00, 30, 10, 'UNIDAD'),
('COM-001', 'Memoria RAM Kingston 8GB', 'Memoria RAM DDR4 8GB 2666MHz Kingston', 5, 150.00, 220.00, 20, 5, 'UNIDAD'),
('COM-002', 'Disco SSD Kingston 480GB', 'Disco SSD Kingston A400 480GB SATA III', 5, 180.00, 260.00, 12, 5, 'UNIDAD'),
('ACC-001', 'Cable HDMI 1.5m', 'Cable HDMI 2.0 4K 1.5 metros', 6, 15.00, 30.00, 50, 20, 'UNIDAD'),
('ACC-002', 'Base Refrigerante Laptop', 'Base refrigerante para laptop 5 ventiladores', 6, 45.00, 75.00, 18, 8, 'UNIDAD');

-- =====================================================
-- 5. VISTAS
-- =====================================================

CREATE VIEW v_stock_productos AS
SELECT 
    p.id,
    p.codigo,
    p.nombre AS producto,
    c.nombre AS categoria,
    p.stock,
    p.stock_minimo,
    p.precio_compra,
    p.precio_venta,
    CASE 
        WHEN p.stock <= p.stock_minimo THEN 'STOCK BAJO'
        WHEN p.stock = 0 THEN 'SIN STOCK'
        ELSE 'DISPONIBLE'
    END AS estado_stock
FROM productos p
INNER JOIN categorias c ON p.categoria_id = c.id
WHERE p.activo = TRUE;

CREATE VIEW v_kardex AS
SELECT 
    m.id,
    p.codigo AS codigo_producto,
    p.nombre AS producto,
    m.tipo,
    m.cantidad,
    m.stock_anterior,
    m.stock_nuevo,
    m.fecha,
    u.nombre_completo AS usuario,
    m.observacion
FROM movimientos m
INNER JOIN productos p ON m.producto_id = p.id
INNER JOIN usuarios u ON m.usuario_id = u.id
ORDER BY m.fecha DESC;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
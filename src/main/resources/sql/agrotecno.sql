CREATE DATABASE agroTecno_db;
GO

USE agroTecno_db;
GO


-- 2. Create Supply Table
CREATE TABLE supply (
    supply_id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NULL,
    current_stock DECIMAL(12,2) NOT NULL,
    minimum_stock DECIMAL(12,2) NOT NULL,
    unit VARCHAR(5) NOT NULL,
    expiration_date DATE NULL,
    location VARCHAR(MAX) NULL,
    status BIT NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL,
    restored_at DATETIME NULL
);

-- 3. Create Customer Table
CREATE TABLE customer (
    customer_id INT PRIMARY KEY IDENTITY(1,1),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    document_type CHAR(3) NOT NULL,
    document_number VARCHAR(15) NOT NULL UNIQUE,
    phone CHAR(9),
    email VARCHAR(150),
    registration_date DATETIME NOT NULL DEFAULT GETDATE(),
    status BIT NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL,
    restored_at DATETIME NULL
);

-- 4. Create Formula Table
CREATE TABLE formula (
    formula_id INT IDENTITY(1,1) NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(MAX) NULL,
    standard_batch DECIMAL(12,2) NULL,
    unit CHAR(1) NULL,
    production_time INT NULL,
    preparation_cost DECIMAL(12,2) NULL,
    suggested_price DECIMAL(12,2) NULL,
    status BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 DEFAULT GETUTCDATE(),
    updated_at DATETIME2 DEFAULT GETUTCDATE(),
    deleted_at DATETIME2 NULL,
    restored_at DATETIME2 NULL,
    CONSTRAINT PK_formula PRIMARY KEY (formula_id)
);

-- 5. Insert Test Data
INSERT INTO supply (name, description, current_stock, minimum_stock, unit, expiration_date, location, status)
VALUES
    ('Semilla Maíz', 'Semilla de maíz de alta calidad para siembra', 100.50, 20.00, 'KG', '2026-12-31', 'Almacén A', 1),
    ('Fertilizante NPK', 'Fertilizante nitrogenado, fosfórico y potásico para cultivos', 250.00, 50.00, 'KG', '2025-06-30', 'Almacén B', 1),
    ('Pesticida Orgánico', 'Pesticida de origen orgánico sin químicos sintéticos', 45.75, 10.00, 'LT', '2026-03-15', 'Bodega C', 1),
    ('Herbicida', 'Herbicida para control de malezas no deseadas', 180.25, 30.00, 'LT', '2025-09-20', 'Bodega D', 1),
    ('Cal Agrícola', 'Cal para corrección de pH del suelo', 500.00, 100.00, 'KG', NULL, 'Almacén E', 1),
    ('Semilla Alfalfa', 'Semilla certificada para producción de forraje de alta calidad', 75.25, 15.00, 'KG', '2026-10-15', 'Almacén A', 1),
    ('Fungicida Cúprico', 'Preventivo contra hongos y bacterias en cultivos variados', 60.00, 12.00, 'LT', '2025-11-30', 'Bodega C', 1),
    ('Sustrato Orgánico', 'Mezcla enriquecida para germinación y desarrollo de plántulas', 350.00, 80.00, 'KG', NULL, 'Almacén E', 1),
    ('Abono de Cordero', 'Abono orgánico natural deshidratado y estabilizado', 400.00, 100.00, 'KG', NULL, 'Almacén B', 1),
    ('Bioestimulante Foliar', 'Concentrado de aminoácidos para resistencia al estrés hídrico', 25.50, 5.00, 'LT', '2026-05-20', 'Bodega D', 1);

INSERT INTO customer (first_name, last_name, document_type, document_number, phone, email, registration_date, status)
VALUES
    ('Juan', 'Pérez', 'DNI', '12345678', '987654321', 'juan.perez@example.com', '2026-01-10 08:00:00', '1'),
    ('María', 'González', 'DNI', '87654321', '987654322', 'maria.gonzalez@example.com', '2026-02-15 09:30:00', '1'),
    ('Carlos', 'Ramírez', 'DNI', '11223344', '987654323', 'carlos.ramirez@example.com', '2026-03-05 10:15:00', '1'),
    ('Ana', 'Lopez', 'DNI', '44332211', '987654324', 'ana.lopez@example.com', '2026-03-20 14:00:00', '1'),
    ('Luis', 'Martínez', 'DNI', '55667788', '987654325', 'luis.martinez@example.com', '2026-04-01 11:45:00', '1'),
    ('Elena', 'Torres', 'DNI', '99887766', '987654326', 'elena.torres@example.com', '2026-04-12 15:20:00', '1'),
    ('Jorge', 'Mendoza', 'DNI', '22446688', '987654327', 'jorge.mendoza@example.com', '2026-04-25 10:30:00', '1'),
    ('Sofía', 'Castro', 'DNI', '33557799', '987654328', 'sofia.castro@example.com', '2026-05-02 08:45:00', '1'),
    ('Pedro', 'Sánchez', 'DNI', '66778899', '987654329', 'pedro.sanchez@example.com', '2026-05-10 16:10:00', '1'),
    ('Lucía', 'Benítez', 'DNI', '44556677', '987654330', 'lucia.benitez@example.com', '2026-05-18 11:15:00', '1');

INSERT INTO formula (name, description, standard_batch, production_time, preparation_cost, suggested_price, status, created_at, updated_at, deleted_at, restored_at)
VALUES
    ('Delcroix Calcio-Boro', 'Fertilizante foliar con Calcio, Boro, Zinc y Magnesio.', 100.00, 24, 15.00, 25.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Magnesio', 'Fitonutriente con 10% Mg y 13% N. Corrige clorosis.', 100.00, 24, 12.00, 20.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Potasio', 'Fertilizante con 45% K2O. Mejora el tamaño y color.', 100.00, 24, 20.00, 30.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Algas', 'Bioestimulante de Ascophyllum nodosum.', 100.00, 24, 50.00, 75.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Ferti Taurus', 'Bioactivador con 7.5% Ácidos Fúlvicos.', 100.00, 24, 10.00, 15.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Calcio-Boro V2', 'Fertilizante foliar con 20% Ca y 2% B.', 100.00, 24, 50.00, 75.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Zinc', 'Corrector de carencias de Zinc de rápida asimilación foliar.', 100.00, 24, 50.00, 75.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Ferti Taurus Max', 'Enraizador biológico con alta concentración de materia orgánica.', 100.00, 24, 15.00, 25.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Engorde', 'Fórmula balanceada NPK optimizada para la fase de fructificación.', 100.00, 24, 50.00, 75.00, 1, GETUTCDATE(), NULL, NULL, NULL),
    ('Delcroix Amino-Plus', 'Complejo de aminoácidos libres para recuperación de estrés.', 100.00, 24, 50.00, 75.00, 1, GETUTCDATE(), NULL, NULL, NULL);

SELECT * FROM supply;
SELECT * FROM customer;
SELECT * FROM formula;

-- CRUD: Supply
INSERT INTO supply (name, description, current_stock, minimum_stock, unit, expiration_date, location, status)
VALUES ('New Pesticide', 'New-generation pesticide', 150.00, 25.00, 'LT', '2027-12-31', 'Storage Room F', 1);

UPDATE supply
SET current_stock = 200.00, updated_at = GETDATE()
WHERE name = 'New Pesticide';

UPDATE supply
SET status = 0, deleted_at = GETDATE()
WHERE name = 'New Pesticide';

UPDATE supply
SET status = 1, restored_at = GETDATE(), deleted_at = NULL
WHERE name = 'New Pesticide';

-- CRUD: Customer
INSERT INTO customer (first_name, last_name, document_type, document_number, phone, email, registration_date, status)
VALUES ('Robert', 'Garcia', 'DNI', '99999999', '987654331', 'robert.garcia@example.com', GETDATE(), 1);

UPDATE customer
SET email = 'robert.garcia.new@example.com', updated_at = GETDATE()
WHERE document_number = '99999999';

UPDATE customer
SET status = 0, deleted_at = GETDATE()
WHERE document_number = '99999999';

UPDATE customer
SET status = 1, restored_at = GETDATE(), deleted_at = NULL
WHERE document_number = '99999999';

-- CRUD: Formula
INSERT INTO formula (name, description, standard_batch, unit, production_time, preparation_cost, suggested_price, status, created_at, updated_at)
VALUES ('New Premium Formula', 'Improved formula with advanced components', 150.00, 'L', 48, 25.00, 35.00, 1, GETUTCDATE(), GETUTCDATE());

UPDATE formula
SET description = 'Updated premium formula with new benefits', updated_at = GETUTCDATE()
WHERE name = 'New Premium Formula';

UPDATE formula
SET status = 0, deleted_at = GETUTCDATE()
WHERE name = 'New Premium Formula';

UPDATE formula
SET status = 1, restored_at = GETUTCDATE(), deleted_at = NULL
WHERE name = 'New Premium Formula';


-- 1. CREACIÓN DE TABLAS TRANSACCIONALES (ORDENADO POR FKs)

-- Tabla de Direcciones
CREATE TABLE address (
    address_id INT IDENTITY(1,1) NOT NULL,
    department VARCHAR(30) NOT NULL,
    province VARCHAR(30) NOT NULL,
    district VARCHAR(30) NOT NULL,
    settlement_type CHAR(1) NOT NULL,
    settlement_name VARCHAR(20) NOT NULL,
    street_type CHAR(1) NOT NULL,
    street_name VARCHAR(20) NOT NULL,
    reference TEXT NULL,
    CONSTRAINT address_pk PRIMARY KEY (address_id)
);

-- Tabla de Parcelas Agrícolas
CREATE TABLE farm_plot (
    farm_id INT IDENTITY(1,1) NOT NULL,
    name VARCHAR(40) NOT NULL,
    hectares DECIMAL(10,2) NULL,
    customer_id INT NOT NULL,
    address_id INT NOT NULL,
    status BIT NOT NULL DEFAULT 1,
    created_at DATETIME2 DEFAULT GETUTCDATE(),
    updated_at DATETIME2 DEFAULT GETUTCDATE(),
    deleted_at DATETIME2 NULL,
    restored_at DATETIME2 NULL,
    CONSTRAINT farm_plot_pk PRIMARY KEY (farm_id),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    FOREIGN KEY (address_id) REFERENCES address (address_id)
);

-- Tabla de Cobros/Pagos principales
CREATE TABLE payment_collection (
    collection_id INT IDENTITY(1,1) PRIMARY KEY,
    total_amount DECIMAL(12,8) NOT NULL,
    installments_count INT NOT NULL DEFAULT 1,
    payment_status CHAR(1) NOT NULL DEFAULT 'P',
    registration_date DATETIME NOT NULL DEFAULT GETDATE(),
    order_id INT NULL
);

-- Detalles del Cobro
CREATE TABLE collection_detail (
    collection_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    collection_id INT NOT NULL,
    line_number INT NOT NULL,
    description VARCHAR(150) NOT NULL,
    quantity DECIMAL(12,2) NOT NULL,
    unit_price DECIMAL(12,8) NOT NULL,
    subtotal DECIMAL(12,8) NOT NULL,
    CONSTRAINT FK_collection_detail_collection FOREIGN KEY (collection_id)
        REFERENCES payment_collection(collection_id)
);

-- Cuotas del Cobro
CREATE TABLE installment (
    installment_id INT IDENTITY(1,1) NOT NULL,
    amount_paid DECIMAL(12,2) NOT NULL,
    payment_type VARCHAR(20) NULL,
    payment_date DATETIME NOT NULL,
    observation TEXT NULL,
    collection_id INT NOT NULL,
    deleted_at DATETIME NULL,
    CONSTRAINT installment_pk PRIMARY KEY (installment_id),
    FOREIGN KEY (collection_id) REFERENCES payment_collection (collection_id)
);

-- Fórmulas de Mezcla Base
CREATE TABLE mixture (
    mixture_id INT IDENTITY(1,1) PRIMARY KEY,
    formula_id INT NOT NULL,
    default_quantity DECIMAL(12,2) NOT NULL,
    percentage_quantity DECIMAL(12,2) NOT NULL DEFAULT 0,
    production_time INT NULL,
    status CHAR(1) NOT NULL DEFAULT 'A',
    registration_date DATETIME NOT NULL DEFAULT GETDATE(),
    total_cost DECIMAL(12,8) NOT NULL,
    CONSTRAINT FK_mixture_formula FOREIGN KEY (formula_id)
        REFERENCES formula(formula_id)
);

-- Detalles de los Insumos de la Mezcla
CREATE TABLE mixture_detail (
    mixture_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    mixture_id INT NOT NULL,
    supply_id INT NOT NULL,
    line_number INT NOT NULL,
    description VARCHAR(150) NOT NULL,
    quantity DECIMAL(12,2) NOT NULL,
    unit_price DECIMAL(12,8) NOT NULL,
    subtotal DECIMAL(12,8) NOT NULL,
    CONSTRAINT FK_mixture_detail_mixture FOREIGN KEY (mixture_id)
        REFERENCES mixture(mixture_id),
    CONSTRAINT FK_mixture_detail_supply FOREIGN KEY (supply_id)
        REFERENCES supply(supply_id)
);

-- Órdenes de Venta
CREATE TABLE sales_order (
    order_id INT IDENTITY(1,1) NOT NULL,
    referral_guide VARCHAR(20) NULL,
    entry_date DATETIME2 NOT NULL,
    delivery_date DATETIME2 NULL,
    order_status CHAR(1) NULL,
    status BIT NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT PK_sales_order PRIMARY KEY CLUSTERED (order_id),
    CONSTRAINT FK_sales_order_customer FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
);

-- Detalle de la Orden de Venta
CREATE TABLE order_detail (
    order_detail_id INT IDENTITY(1,1) NOT NULL,
    order_id INT NOT NULL,
    formula_id INT NOT NULL,
    quantity INT NOT NULL,
    sale_price DECIMAL(18, 2) NOT NULL,
    CONSTRAINT PK_order_detail PRIMARY KEY CLUSTERED (order_detail_id),
    CONSTRAINT FK_order_detail_order FOREIGN KEY (order_id)
        REFERENCES sales_order (order_id)
        ON DELETE CASCADE,
    CONSTRAINT FK_order_detail_formula FOREIGN KEY (formula_id)
        REFERENCES formula (formula_id)
);

-- Órdenes de Producción
CREATE TABLE production_order (
    production_order_id INT IDENTITY(1,1) NOT NULL,
    requested_quantity DECIMAL(10,2) NOT NULL,
    referral_guide VARCHAR(20) NULL,
    entry_date DATETIME NOT NULL,
    delivery_date DATETIME NULL,
    order_status CHAR(1) NOT NULL,
    status CHAR(1) NOT NULL,
    formula_id INT NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT production_order_pk PRIMARY KEY (production_order_id),
    FOREIGN KEY (formula_id) REFERENCES formula (formula_id),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

-- Inventario de Productos Terminados (CORREGIDO PARA JAVA)
CREATE TABLE product_inventory (
    product_inventory_id INT PRIMARY KEY IDENTITY(1,1),
    formula_formula_id INT NOT NULL,
    gallon_capacity INT NOT NULL,
    stock_quantity INT NOT NULL,
    status BIT NOT NULL DEFAULT 1, -- <- ¡ASEGÚRATE DE QUE TENGA EL DEFAULT 1!
    last_update DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_product_inventory_formula FOREIGN KEY (formula_formula_id) REFERENCES formula(formula_id)
);

-- Visitas y Monitoreo (Meetings)
CREATE TABLE meeting (
    meeting_id INT IDENTITY(1,1) NOT NULL,
    meeting_date DATE NOT NULL,
    meeting_time TIME NOT NULL,
    fruit_quality VARCHAR(50) NULL,
    observation TEXT NULL,
    visit_status CHAR(1) NOT NULL,
    production_order_id INT NOT NULL,
    customer_id INT NOT NULL,
    farm_id INT NOT NULL,
    CONSTRAINT meeting_pk PRIMARY KEY (meeting_id),
    FOREIGN KEY (production_order_id) REFERENCES production_order (production_order_id),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    FOREIGN KEY (farm_id) REFERENCES farm_plot (farm_id)
);
GO

-- CREACIÓN DE LA TABLA DE USUARIOS (NO TRANSACCIONAL / AUTENTICACIÓN)
CREATE TABLE users (
    user_id INT IDENTITY(1,1) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    status BIT NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT PK_users PRIMARY KEY CLUSTERED (user_id),
    CONSTRAINT CHK_password_length CHECK (LEN(password_hash) >= 12)
);
GO


-- 2. INSERCIÓN DE DATOS DE PRUEBA


-- Direcciones
INSERT INTO address (department, province, district, settlement_type, settlement_name, street_type, street_name, reference)
VALUES
    ('Lima', 'Lima', 'San Isidro', 'A', 'Santa Rosa', 'S', 'Street 1', 'Near the park'),
    ('Lima', 'Lima', 'Miraflores', 'A', 'Costa Verde', 'A', 'Coastal Ave.', 'In front of the sea'),
    ('Arequipa', 'Arequipa', 'Arequipa', 'R', 'Rural Area', 'R', 'Rural Road', 'Agricultural area'),
    ('Cusco', 'Cusco', 'Cusco', 'R', 'Andean Area', 'R', 'Mountain Path', 'Highlands'),
    ('Piura', 'Piura', 'Piura', 'A', 'Miraflores', 'S', 'Street 2', 'Downtown area'),
    ('Ica', 'Ica', 'Ica', 'R', 'Agricultural Area', 'A', 'Main Ave.', 'Fertile valley'),
    ('Junin', 'Junin', 'Huancayo', 'R', 'Chupaca', 'R', 'Chupaca Road', 'High plateau'),
    ('Puno', 'Puno', 'Puno', 'R', 'Quechua Area', 'R', 'Lake Road', 'Near the lake'),
    ('La Libertad', 'La Libertad', 'Trujillo', 'A', 'Industrial Area', 'S', 'Street 3', 'Industrial zone'),
    ('Ancash', 'Ancash', 'Huaraz', 'R', 'Mountain Area', 'R', 'Mountain Road', 'Mountain zone');

-- Parcelas (Depende de customer y address)
INSERT INTO farm_plot (name, hectares, customer_id, address_id)
VALUES
    ('Saint John Farm', 15.50, 1, 1),
    ('Flower Crop Field', 22.75, 2, 2),
    ('Corn Plot', 18.30, 3, 3),
    ('Andean Farm', 25.00, 4, 4),
    ('Northern Farm', 30.50, 5, 5),
    ('Fertile Crop Field', 20.00, 6, 6),
    ('High Plateau Plot', 28.75, 7, 7),
    ('Lake Farm', 16.25, 8, 8),
    ('Industrial Farm', 35.00, 9, 9),
    ('Mountain Crop Field', 19.50, 10, 10);

-- Finanzas de Transacciones
INSERT INTO payment_collection (total_amount, installments_count, payment_status, registration_date, order_id)
VALUES
    (150.50000000, 1, 'P', GETDATE(), 7),
    (320.75000000, 3, 'P', GETDATE(), 8);

INSERT INTO collection_detail (collection_id, line_number, description, quantity, unit_price, subtotal)
VALUES
    (1, 1, 'Corn seed', 2, 75.25000000, 150.50000000);

INSERT INTO installment (amount_paid, payment_type, payment_date, observation, collection_id)
VALUES
    (150.50, 'Cash', '2026-05-24 14:00:00', 'First installment payment', 1),
    (320.75, 'Card', '2026-05-24 14:30:00', 'Full payment', 2),
    (150.50, 'Cash', '2026-05-23 15:00:00', 'Advance payment', 1),
    (250.00, 'Bank transfer', '2026-05-22 16:00:00', 'Second installment payment', 2),
    (175.25, 'Card', '2026-05-21 17:00:00', 'Partial payment', 1),
    (320.75, 'Cash', '2026-05-20 18:00:00', 'Remaining balance payment', 2),
    (100.00, 'Bank transfer', '2026-05-25 09:00:00', 'Account deposit', 1),
    (150.50, 'Card', '2026-05-26 10:00:00', 'Monthly installment', 2),
    (200.00, 'Cash', '2026-05-27 11:00:00', 'Additional payment', 1),
    (320.75, 'Bank transfer', '2026-05-28 12:00:00', 'Final balance', 2);

-- Fórmulas de mezclas y detalles (Dependen de formula y supply)
INSERT INTO mixture (formula_id, default_quantity, percentage_quantity, production_time, status, registration_date, total_cost)
VALUES
    (1, 100.00, 10.00, 24, 'A', GETDATE(), 500.50000000),
    (2, 150.00, 5.00, 24, 'A', GETDATE(), 750.75000000);

INSERT INTO mixture_detail (mixture_id, supply_id, line_number, description, quantity, unit_price, subtotal)
VALUES
    (1, 1, 1, 'Corn seed for mixture', 2.50, 100.20000000, 250.50000000),
    (1, 2, 2, 'NPK fertilizer', 1.50, 166.66666667, 250.00000000),
    (2, 3, 1, 'Organic pesticide', 3.00, 150.25000000, 450.75000000),
    (2, 4, 2, 'Herbicide', 2.00, 150.00000000, 300.00000000);

-- Órdenes de Venta y sus detalles
INSERT INTO sales_order (referral_guide, entry_date, delivery_date, order_status, status, customer_id)
VALUES
    ('GRE-2026-0001', '2026-05-24 10:00:00', '2026-05-28 15:30:00', 'P', 1, 1),
    ('GRE-2026-0002', '2026-05-24 11:15:00', '2026-05-29 09:00:00', 'E', 1, 2),
    ('GRE-2026-0003', '2026-05-24 14:45:00', NULL, 'C', 0, 3);

INSERT INTO order_detail (order_id, formula_id, quantity, sale_price)
VALUES
    (1, 1, 50, 120.50),
    (1, 2, 30, 85.00),
    (2, 1, 100, 115.00),
    (2, 3, 15, 210.00),
    (3, 2, 10, 85.00);

-- Inventario de Productos (Corregido con presentaciones válidas: 1 Litro y 20 Litros)

INSERT INTO product_inventory (formula_formula_id, gallon_capacity, stock_quantity, status, last_update)
VALUES
    (1, 1, 500, 1, '2026-05-24 10:00:00'),
    (2, 1, 450, 1, '2026-05-24 10:15:00'),
    (3, 20, 300, 1, '2026-05-24 10:30:00'),
    (4, 1, 600, 1, '2026-05-24 10:45:00'),
    (5, 20, 250, 1, '2026-05-24 11:00:00'),
    (6, 1, 550, 1, '2026-05-24 11:15:00'),
    (7, 20, 320, 1, '2026-05-24 11:30:00'),
    (8, 1, 480, 1, '2026-05-24 11:45:00'),
    (9, 20, 380, 1, '2026-05-24 12:00:00'),
    (10, 1, 620, 1, '2026-05-24 12:15:00');
GO

-- Órdenes de Producción (OBLIGATORIO antes de 'meeting')
INSERT INTO production_order (requested_quantity, referral_guide, entry_date, delivery_date, order_status, status, formula_id, customer_id)
VALUES
    (500.00,  'GR-2026-001', '2026-06-01 08:00:00', '2026-06-05 17:00:00', 'C', '1', 1, 1),
    (1200.00, 'GR-2026-002', '2026-06-02 09:30:00', '2026-06-07 14:00:00', 'C', '1', 2, 2),
    (350.00,  'GR-2026-003', '2026-06-03 11:15:00', '2026-06-09 12:00:00', 'P', '1', 3, 3),
    (800.00,  'GR-2026-004', '2026-06-04 14:00:00', '2026-06-12 16:30:00', 'P', '1', 4, 4),
    (1500.00, 'GR-2026-005', '2026-06-05 07:45:00', NULL,                  'A', '1', 5, 5),
    (250.00,  'GR-2026-006', '2026-06-06 10:00:00', '2026-06-10 11:00:00', 'C', '1', 1, 2),
    (900.00,  'GR-2026-007', '2026-06-07 13:20:00', NULL,                  'P', '1', 2, 4),
    (600.00,  'GR-2026-008', '2026-06-08 15:45:00', NULL,                  'A', '1', 3, 1),
    (450.00,  'GR-2026-009', '2026-06-09 09:00:00', NULL,                  'A', '1', 4, 3),
    (2000.00, 'GR-2026-010', '2026-06-10 11:30:00', NULL,                  'A', '1', 5, 2);

-- Monitoreo / Reuniones (Ya no fallará la llave foránea de production_order_id)
INSERT INTO meeting (meeting_date, meeting_time, fruit_quality, observation, visit_status, production_order_id, customer_id, farm_id)
VALUES
    ('2026-05-25', '09:00:00', 'Excellent', 'Crop is in good condition and ready for harvest', 'C', 1, 1, 1),
    ('2026-05-26', '10:30:00', 'Good', 'Normal plant development', 'C', 2, 2, 2),
    ('2026-05-27', '11:00:00', 'Fair', 'More irrigation is required in the north area', 'P', 3, 3, 3),
    ('2026-05-28', '14:00:00', 'Excellent', 'Optimal forage growth', 'C', 4, 4, 4),
    ('2026-05-29', '08:30:00', 'Good', 'Minimal pest presence', 'C', 5, 5, 5),
    ('2026-05-30', '15:00:00', 'Poor', 'Fungi were detected and immediate treatment is required', 'A', 6, 6, 6),
    ('2026-05-31', '09:30:00', 'Excellent', 'Harvest prepared for next week', 'C', 7, 7, 7),
    ('2026-05-20', '13:00:00', 'Good', 'Irrigation system working correctly', 'C', 8, 8, 8),
    ('2026-05-21', '16:45:00', 'Excellent', 'Fertilizer application showing good results', 'C', 9, 9, 9),
    ('2026-05-22', '10:00:00', 'Fair', 'Supply inventory verification', 'P', 10, 10, 10);
GO

--INSERCIÓN DE USUARIOS DE PRUEBA (Credenciales con más de 12 caracteres)
INSERT INTO users (username, password_hash, role, status)
VALUES 
    ('admin', 'adminSecurePassword2026', 'ADMIN', 1),
    ('tecnico1', 'tecnicoClaveSegura123', 'TECHNICAL', 1);
GO

SELECT * FROM address;
SELECT * FROM customer;
SELECT * FROM supply;
SELECT * FROM formula;
SELECT * FROM farm_plot;
SELECT * FROM mixture;
SELECT * FROM mixture_detail;
SELECT * FROM sales_order;
SELECT * FROM order_detail;
SELECT * FROM product_inventory;
SELECT * FROM production_order;
SELECT * FROM payment_collection;
SELECT * FROM collection_detail;
SELECT * FROM installment;
SELECT * FROM meeting;
SELECT * FROM users;

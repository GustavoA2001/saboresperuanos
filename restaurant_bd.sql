

CREATE TABLE carrito (
  IDCarrito int(11) NOT NULL,
  Cantidad int(11) DEFAULT NULL,
  PrecioProducto decimal(12,8) DEFAULT NULL,
  IDProducto int(11) DEFAULT NULL,
  IDPedido int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO carrito (IDCarrito, Cantidad, PrecioProducto, IDProducto, IDPedido) VALUES
(1, 1, 45.00000000, 1, 1),
(2, 1, 35.00000000, 2, 2),
(3, 1, 18.00000000, 4, 3),
(4, 1, 38.00000000, 8, 2),
(5, 1, 32.00000000, 9, 2),
(6, 2, 70.00000000, 10, 2),
(7, 1, 38.00000000, 18, 4),
(8, 1, 32.00000000, 19, 4),
(9, 1, NULL, 25, 2),
(10, 1, NULL, 24, 2),
(11, 1, NULL, 17, 2),
(12, 1, NULL, 22, 5),
(13, 2, NULL, 9, 5),
(14, 1, NULL, 10, 5),
(15, 1, NULL, 12, 5),
(16, 1, NULL, 19, 6),
(17, 1, NULL, 1, 6),
(18, 2, 70.00000000, 2, 5),
(19, 2, 11.00000000, 12, 7),
(20, 1, 28.00000000, 23, 7),
(21, 1, 15.00000000, 27, 7),
(22, 4, 60.00000000, 27, 8),
(23, 1, 5.50000000, 12, 9),
(24, 4, 20.00000000, 22, 9),
(25, 2, 12.00000000, 11, 10),
(26, 2, 11.00000000, 12, 11),
(27, 1, 5.00000000, 22, 11),
(28, 1, 20.00000000, 6, 12),
(29, 1, 45.00000000, 1, 12),
(30, 2, 12.00000000, 11, 12),
(31, 1, 5.50000000, 12, 6),
(32, 2, 30.00000000, 27, 13),
(34, 1, 35.00000000, 2, 14),
(35, 2, 10.00000000, 22, 14),
(37, 1, 5.00000000, 22, 15),
(38, 2, 10.00000000, 22, 16),
(39, 4, 48.00000000, 13, 17),
(41, 6, 72.00000000, 13, 17),
(42, 6, 72.00000000, 13, 17),
(45, 2, 14.00000000, 17, 18),
(47, 2, 90.00000000, 1, 17),
(49, 2, 90.00000000, 1, 17),
(55, 2, 48.00000000, 26, 19),
(56, 3, 105.00000000, 2, 19),
(57, 3, 105.00000000, 2, 19),
(58, 5, 60.00000000, 25, 19),
(59, 2, 11.00000000, 12, 20),
(60, 1, 45.00000000, 1, 20),
(62, 2, 11.00000000, 12, 21),
(63, 2, 70.00000000, 2, 22),
(64, 1, 15.00000000, 27, 23),
(65, 2, 90.00000000, 1, 24),
(66, 1, 27.00000000, 16, 24),
(67, 1, 7.00000000, 17, 24),
(68, 2, 10.00000000, 22, 25),
(69, 1, 45.00000000, 1, 25),
(70, 1, 38.00000000, 8, 26),
(71, 1, 6.00000000, 11, 27),
(72, 1, 6.00000000, 11, 28),
(73, 1, 7.00000000, 17, 29),
(74, 1, 15.00000000, 27, 30),
(75, 1, 5.00000000, 22, 31),
(76, 2, 24.00000000, 13, 32),
(77, 1, 7.00000000, 17, 33),
(78, 2, 16.00000000, 24, 34),
(79, 1, 10.00000000, 14, 34),
(80, 1, 16.00000000, 19, 35),
(81, 1, 8.00000000, 18, 36),
(82, 2, 30.00000000, 27, 37),
(83, 2, 56.00000000, 23, 37),
(84, 1, 35.00000000, 2, 38),
(85, 1, 15.00000000, 27, 38),
(86, 2, 30.00000000, 27, 39),
(87, 2, 16.00000000, 18, 39),
(88, 2, 24.00000000, 25, 39),
(89, 1, 5.50000000, 12, 40),
(90, 4, 32.00000000, 24, 41),
(91, 1, 15.00000000, 15, 41),
(92, 1, 27.00000000, 16, 42),
(93, 1, 5.00000000, 22, 43),
(94, 2, 10.00000000, 22, 44),
(97, 1, 6.00000000, 11, 44);


CREATE TABLE categoriaproducto (
  IDCategoria int(11) NOT NULL,
  NomCategoria varchar(50) DEFAULT NULL,
  Descripcion varchar(150) DEFAULT NULL,
  nom_categoria varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO categoriaproducto (IDCategoria, NomCategoria, Descripcion, nom_categoria) VALUES
(1, 'Platillos', 'Principales platos peruanos como Lomo Saltado y Ceviche', NULL),
(2, 'Bebidas', 'Bebidas tradicionales como Chicha Morada y Pisco Sour', NULL),
(3, 'Entradas', 'Entradas como Papa a la Huancaína y Causa Limeña', NULL),
(4, 'Postres', 'Postres típicos peruanos como Mazamorra Morada y Arroz con Leche', NULL);


CREATE TABLE cliente (
  IDCliente int(11) NOT NULL,
  Nombre varchar(100) DEFAULT NULL,
  Apellido varchar(100) DEFAULT NULL,
  Usuario varchar(50) DEFAULT NULL,
  Correo varchar(100) DEFAULT NULL,
  Contrasena varchar(350) DEFAULT NULL,
  dni varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE password_reset_token (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(100) NOT NULL,
  token VARCHAR(255) NOT NULL UNIQUE,
  expiration_date DATETIME NOT NULL
);


INSERT INTO cliente (IDCliente, Nombre, Apellido, Usuario, Correo, Contrasena, dni) VALUES
(1, 'Luis', 'Alvarado', 'luis', 'luis@gmail.com', '4c96f8324e3ba54a99e78249b95daa30', '12345678'),
(2, 'gustavo', 'alegre', 'gustavo', NULL, '67daae98ed0c612857a716202f463356ffcf1a018ce140ab4a4bebc8eb274e6d', '23456789'),
(3, 'alegre', 'alegre', 'alegre', NULL, '80dbfc87f59c891ced5dd54c0b0dedd08d82f8917e8ecd256c8b8ce665e70882', '11112222'),
(4, 'Carlos', 'Garcia', 'carlosg', NULL, 'd9ecb5410511f09da8887accb3911a4786862b6523770abe4d387a8b97588801', '87654321'),
(5, 'Maria', 'Perez', 'mariap', NULL, '204d70655397ecbbb4209bc37a667d6f2d24c0fd04edd1ed17949fe2ffcb2ed0', '87654322'),
(6, 'Rosa', 'Vargas', 'rosav', NULL, '0f42564eb231b7cd945f30f00e7b100d19deeb900594d1631e2a964d481ed10b', '87654323'),
(7, 'gustavo', 'alvarado', 'gustav0', 'gustavo@gmail.com', '4c96f8324e3ba54a99e78249b95daa30', '12121212'),
(8, 'karina', 'aa', 'karina', 'karina@gmail.com', 'a37b2a637d2541a600d707648460397e', '33331111'),
(9, 'pedro', 'perez', 'pedrito', 'perezpedro@gmail.com', 'c6cc8094c2dc07b700ffcc36d64e2138', '77777777'),
(10, 'Nayeli', 'Ipanaque', 'Nayeli', 'Nayeli@gmail.com', '202cb962ac59075b964b07152d234b70', '79579493'),
(11, 'grdgdfg', 'bdxbdfg', 'matos', 'owen@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '00000001'),
(19, 'CARLOTA', 'MEZA DE RUIZ', 'guuuu', 'gus@gmail.com', '202cb962ac59075b964b07152d234b70', '00000003'),
(20, 'LLC', 'Twist Printers', 'alegre', 'alegre@gmail.com', '202cb962ac59075b964b07152d234b70', '00000000'),
(23, 'gustavo', 'alegre', 'galegre', 'galegre@gmail.com', 'c7a0a5ab25fdaf962cba424e19c670e2', '88888888'),
(28, 'gabriel', 'moreno', 'gabriel-tito', 'gabriel@gmail.com', 'df01ea5a4ef015916f926ae1418b7cc8', '55555555'),
(29, 'luis gustavo', 'alvarado alegre', 'gustavo123', 'luisalegre@gmail.com', '233eb7b67b712155db8b6dd07d17872b', '22222222'),
(30, 'gabriel tito', 'moreno', 'moreno', 'moreno@gmail.com', '782ca1470254d6c917877cd2be2693a4', '72968472');


CREATE TABLE contador (
  IDContador int(11) NOT NULL,
  Contador int(11) DEFAULT NULL,
  IDProducto int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE delivery (
  IDDelivery int(11) NOT NULL,
  Estado varchar(50) DEFAULT NULL,
  FechaDelivery datetime DEFAULT NULL,
  IDPedido int(11) DEFAULT NULL,
  IDEmpleado int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO delivery (IDDelivery, Estado, FechaDelivery, IDPedido, IDEmpleado) VALUES
(1, 'Iniciado', '2024-12-01 17:52:53', 24, NULL),
(2, 'Iniciado', '2024-12-01 23:51:40', 25, NULL),
(3, 'Iniciado', '2024-12-01 23:55:19', 26, NULL),
(4, 'Iniciado', '2024-12-02 00:00:33', 28, NULL),
(5, 'Iniciado', '2024-12-02 00:03:02', 29, NULL),
(6, 'Iniciado', '2024-12-02 00:12:22', 30, NULL),
(7, 'Iniciado', '2024-12-02 00:18:54', 31, NULL),
(8, 'Iniciado', '2024-12-02 00:20:16', 32, NULL),
(9, 'Iniciado', '2024-12-02 00:28:18', 33, NULL),
(10, 'Iniciado', '2024-12-02 00:34:22', 34, NULL),
(11, 'Iniciado', '2024-12-02 00:39:03', 35, NULL),
(12, 'Iniciado', '2024-12-02 00:50:45', 36, NULL),
(13, 'Iniciado', '2024-12-02 00:52:39', 37, NULL),
(14, 'Iniciado', '2024-12-02 16:42:40', 38, NULL),
(15, 'Iniciado', '2024-12-02 17:46:50', 39, NULL),
(16, 'Iniciado', '2024-12-03 00:34:55', 41, NULL),
(17, 'Iniciado', '2024-12-03 15:35:13', 42, NULL),
(18, 'Iniciado', '2024-12-03 23:08:49', 43, NULL);


CREATE TABLE detallecliente (
  IDDetalleCliente int(11) NOT NULL,
  Telefono varchar(9) DEFAULT NULL,
  Correo varchar(100) DEFAULT NULL,
  Foto varchar(100) DEFAULT NULL,
  IDCliente int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO detallecliente (IDDetalleCliente, Telefono, Correo, Foto, IDCliente) VALUES
(1, '987654321', 'carlosg@example.com', 'carlos.jpg', 4),
(2, '987654322', 'mariap@example.com', 'maria.jpg', 5),
(3, '987654323', 'rosav@example.com', 'rosa.jpg', 6);


CREATE TABLE detalleempleado (
  IDDetalleEmp int(11) NOT NULL,
  Telefono varchar(9) DEFAULT NULL,
  Correo varchar(100) DEFAULT NULL,
  Foto varchar(150) DEFAULT NULL,
  IDEmpleado int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE direccion (
  IDDireccion int(11) NOT NULL,
  Direccion varchar(150) DEFAULT NULL,
  Referencia varchar(150) DEFAULT NULL,
  IDDistrito int(11) DEFAULT NULL,
  IDCliente int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO direccion (IDDireccion, Direccion, Referencia, IDDistrito, IDCliente) VALUES
(1, 'Av. Pardo y Aliaga 640', 'Cerca al parque Olivar', 2, 4),
(2, 'Calle Los Sauces 123', 'Frente al parque central', 1, 5),
(3, 'Av. Grau 450', 'Al lado de la tienda principal', 3, 6),
(4, 'UTP 123', 'Al frente de la UCV', 4, 1),
(5, 'Av. Las Flores 123', 'Al lado de la UTP', 5, 7),
(6, 'Av. Rojo 321', 'Frente a una botica', 11, 7),
(7, 'Av. Los Palacios 444', 'Frente a una botica', 6, NULL),
(8, 'Av. Los Palacios 444', 'Al frente de una botica', 4, NULL),
(9, 'Av. Los Palacios 444', 'Al frente de una botica', 6, 28),
(10, 'Av. Las Flores 123', 'Al lado de la UTP', 5, 29),
(11, 'Av. Los Portales', 'Frente a una botica', 9, 30);


CREATE TABLE distrito (
  IDDistrito int(11) NOT NULL,
  Distrito varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO distrito (IDDistrito, Distrito) VALUES
(1, 'Miraflores'),
(2, 'San Isidro'),
(3, 'Barranco'),
(4, 'Surco'),
(5, 'Comas'),
(6, 'El Agustino'),
(7, 'Lince'),
(8, 'Surquilo'),
(9, 'Callao'),
(10, 'San Martín de Porres'),
(11, 'Pueblo Libre');


CREATE TABLE empleado (
  IDEmpleado int(11) NOT NULL,
  Nombre varchar(50) DEFAULT NULL,
  ApellidoPaterno varchar(50) DEFAULT NULL,
  ApellidoMaterno varchar(50) DEFAULT NULL,
  Usuario varchar(50) DEFAULT NULL,
  Contrasena varchar(150) DEFAULT NULL,
  IDRol int(11) DEFAULT NULL,
  apellido varchar(50) DEFAULT NULL,
  contraseña varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO empleado (IDEmpleado, Nombre, ApellidoPaterno, ApellidoMaterno, Usuario, Contrasena, IDRol, apellido, contraseña) VALUES
(1, 'Juan', 'Ramirez', 'Torres', 'juanr', '16a8d6e0d509a7e491d252ea37bfbb23', 1, NULL, NULL),
(2, 'Luis', 'Diaz', 'Rojas', 'luisd', 'c34b2ecc24e0f948a5e99a3c94403e29', 2, NULL, NULL),
(3, 'Miguel', 'Soto', 'Castro', 'miguels', 'password_hash', 3, NULL, NULL),
(4, 'Luis', 'Alvarado', 'Alegre', 'alegre', 'db37ceecfa6c16175dc52c0bcd215065', 4, NULL, NULL);


CREATE TABLE entrega (
  IDEntrega int(11) NOT NULL,
  HoraSalida datetime DEFAULT NULL,
  HoraEntrega datetime DEFAULT NULL,
  IDDelivery int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE factura (
  IDFactura int(11) NOT NULL,
  CostoTotal decimal(12,8) DEFAULT NULL,
  FechaPedido date DEFAULT NULL,
  costo_total decimal(12,8) DEFAULT NULL,
  fecha_pedido date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO factura (IDFactura, CostoTotal, FechaPedido, costo_total, fecha_pedido) VALUES
(1, 45.00000000, '2024-10-01', NULL, NULL),
(2, 35.00000000, '2024-10-02', NULL, NULL);


CREATE TABLE intentos (
  IDIntentos int(11) NOT NULL,
  Fallos int(11) DEFAULT NULL,
  Ingresos int(11) DEFAULT NULL,
  IDCliente int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE pago (
  IDPago int(11) NOT NULL,
  IDPedido int(11) DEFAULT NULL,
  IDTipoPago int(11) DEFAULT NULL,
  IDFactura int(11) DEFAULT NULL,
  Estado int(11) DEFAULT NULL,
  FechaPago datetime DEFAULT NULL,
  TotalPago decimal(12,5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO pago (IDPago, IDPedido, IDTipoPago, IDFactura, Estado, FechaPago, TotalPago) VALUES
(5, 20, NULL, NULL, NULL, '2024-11-29 22:49:26', 11.00000),
(6, 20, NULL, NULL, NULL, '2024-11-29 23:22:12', 56.00000),
(7, 21, NULL, NULL, NULL, '2024-12-01 00:08:53', 11.00000),
(8, 22, NULL, NULL, NULL, '2024-12-01 00:34:32', 70.00000),
(9, 23, NULL, NULL, NULL, '2024-12-01 00:39:20', 15.00000),
(10, 23, NULL, NULL, NULL, '2024-12-01 00:46:58', 15.00000),
(11, 24, NULL, NULL, NULL, '2024-12-01 17:52:53', 124.00000),
(12, 25, NULL, NULL, NULL, '2024-12-01 23:51:40', 55.00000),
(13, 26, NULL, NULL, NULL, '2024-12-01 23:55:19', 38.00000),
(14, 28, NULL, NULL, NULL, '2024-12-02 00:00:33', 6.00000),
(15, 29, NULL, NULL, NULL, '2024-12-02 00:03:02', 7.00000),
(16, 30, NULL, NULL, NULL, '2024-12-02 00:12:22', 15.00000),
(17, 31, NULL, NULL, NULL, '2024-12-02 00:18:54', 5.00000),
(18, 32, NULL, NULL, NULL, '2024-12-02 00:20:16', 24.00000),
(19, 33, NULL, NULL, NULL, '2024-12-02 00:28:18', 7.00000),
(20, 34, NULL, NULL, NULL, '2024-12-02 00:34:22', 26.00000),
(21, 35, NULL, NULL, NULL, '2024-12-02 00:39:03', 16.00000),
(22, 36, NULL, NULL, NULL, '2024-12-02 00:50:45', 8.00000),
(23, 37, NULL, NULL, NULL, '2024-12-02 00:52:39', 86.00000),
(24, 38, NULL, NULL, NULL, '2024-12-02 16:42:40', 50.00000),
(25, 39, NULL, NULL, NULL, '2024-12-02 17:46:50', 70.00000),
(26, 41, NULL, NULL, NULL, '2024-12-03 00:34:55', 47.00000),
(27, 42, NULL, NULL, NULL, '2024-12-03 15:35:13', 27.00000),
(28, 43, NULL, NULL, NULL, '2024-12-03 23:08:49', 5.00000);


CREATE TABLE pedido (
  IDPedido int(11) NOT NULL,
  MontoFinal decimal(12,8) DEFAULT NULL,
  IDCliente int(11) DEFAULT NULL,
  Estado varchar(10) DEFAULT NULL,
  FechaPedido datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO pedido (IDPedido, MontoFinal, IDCliente, Estado, FechaPedido) VALUES
(1, 45.00000000, 4, 'Completado', '2024-10-01 12:30:00'),
(2, 35.00000000, 7, 'Activo', '2024-10-02 14:00:00'),
(3, 18.00000000, 6, 'En prepara', '2024-10-03 13:45:00'),
(4, 100.00000000, 7, 'Completado', '2024-10-01 12:30:00'),
(5, 140.00000000, 8, NULL, '2024-11-21 06:40:40'),
(6, 5.50000000, 7, 'Activo', '2024-11-21 07:23:53'),
(7, 65.00000000, 8, 'Activo', '2024-11-21 07:45:21'),
(8, 240.00000000, NULL, 'Activo', '2024-11-21 10:50:38'),
(9, 85.50000000, 9, 'Activo', '2024-11-21 10:52:46'),
(10, 24.00000000, NULL, 'Activo', '2024-11-21 11:37:13'),
(11, 27.00000000, 10, 'Activo', '2024-11-21 11:37:59'),
(12, 89.00000000, 11, 'Activo', '2024-11-21 11:43:28'),
(13, 75.00000000, 7, 'Activo', '2024-11-22 11:46:08'),
(14, 55.00000000, 7, 'Activo', '2024-11-25 21:12:48'),
(15, 5.00000000, 7, 'Activo', '2024-11-26 00:08:23'),
(16, 20.00000000, 7, 'Pagado', '2024-11-27 16:38:49'),
(17, 1596.00000000, 7, 'Activo', '2024-11-28 01:09:48'),
(18, 56.00000000, 28, 'Activo', '2024-11-28 01:17:47'),
(19, 1026.00000000, 30, 'Activo', '2024-11-28 11:18:15'),
(20, 112.00000000, 7, 'pagado', '2024-11-29 01:02:06'),
(21, 22.00000000, 7, 'Pagado', '2024-12-01 00:08:26'),
(22, 140.00000000, 7, 'Pagado', '2024-12-01 00:33:44'),
(23, 15.00000000, 7, 'Pagado', '2024-12-01 00:38:41'),
(24, 214.00000000, 30, 'Pagado', '2024-12-01 17:51:53'),
(25, 65.00000000, 30, 'Pagado', '2024-12-01 23:42:04'),
(26, 38.00000000, 30, 'Pagado', '2024-12-01 23:54:58'),
(27, 6.00000000, 30, 'Activo', '2024-12-01 23:59:58'),
(28, 6.00000000, 30, 'Pagado', '2024-12-02 00:00:11'),
(29, 7.00000000, 7, 'Pagado', '2024-12-02 00:02:39'),
(30, 15.00000000, 30, 'Pagado', '2024-12-02 00:12:06'),
(31, 5.00000000, 30, 'Pagado', '2024-12-02 00:18:35'),
(32, 48.00000000, 30, 'Pagado', '2024-12-02 00:19:41'),
(33, 7.00000000, 30, 'Pagado', '2024-12-02 00:27:59'),
(34, 42.00000000, 30, 'Pagado', '2024-12-02 00:33:48'),
(35, 16.00000000, 30, 'Pagado', '2024-12-02 00:38:42'),
(36, 8.00000000, 30, 'Pagado', '2024-12-02 00:50:30'),
(37, 172.00000000, 30, 'Pagado', '2024-12-02 00:52:10'),
(38, 50.00000000, 30, 'Pagado', '2024-12-02 16:42:00'),
(39, 140.00000000, 30, 'Pagado', '2024-12-02 17:45:42'),
(40, 5.50000000, 7, 'Activo', '2024-12-02 17:54:43'),
(41, 143.00000000, 30, 'Pagado', '2024-12-03 00:34:14'),
(42, 27.00000000, 7, 'Pagado', '2024-12-03 15:33:22'),
(43, 5.00000000, 30, 'Pagado', '2024-12-03 23:08:01'),
(44, 16.00000000, 30, 'Activo', '2024-12-04 15:23:53');


CREATE TABLE producto (
  IDProducto int(11) NOT NULL,
  NomProducto varchar(100) DEFAULT NULL,
  PrecioUnitario decimal(12,8) DEFAULT NULL,
  FotoProducto varchar(250) DEFAULT NULL,
  descripcion varchar(255) DEFAULT NULL,
  Cantidad int(11) DEFAULT NULL,
  FechaProducto datetime DEFAULT NULL,
  IDCategoria int(11) DEFAULT NULL,
  IDTipo int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO producto (IDProducto, NomProducto, PrecioUnitario, FotoProducto, descripcion, Cantidad, FechaProducto, IDCategoria, IDTipo) VALUES
(1, 'Lomo Saltado', 45.00000000, 'lomosaltado', 'Lomo saltado de carne con papas fritas, cebolla y tomate', 100, NULL, 1, NULL),
(2, 'Ceviche de Pescado', 35.00000000, 'ceviche', 'Ceviche de pescado fresco con limón y cebolla', 100, NULL, 1, NULL),
(3, 'Papa a la Huancaína', 15.00000000, NULL, 'Rodajas de papa bañadas en salsa de queso y ají amarillo', 100, NULL, 3, NULL),
(4, 'Causa Limeña', 18.00000000, NULL, 'Causa de papa con relleno de pollo y palta', 100, NULL, 3, NULL),
(5, 'Chicha Morada', 8.00000000, NULL, 'Bebida de maíz morado y frutas', 200, NULL, 2, NULL),
(6, 'Pisco Sour', 20.00000000, NULL, 'Cóctel peruano hecho con pisco, limón y clara de huevo', 100, NULL, 2, NULL),
(7, 'Mazamorra Morada', 10.00000000, NULL, 'Postre peruano de maíz morado y frutas', 50, NULL, 4, NULL),
(8, 'Seco de Cabrito', 38.00000000, 'secocabrito', 'Plato típico peruano de carne de cabrito guisado con frejoles y arroz', 45, '2024-11-15 00:24:28', 1, NULL),
(9, 'Ají de Langostinos', 32.00000000, 'ajidelangostinos', 'Ají amarillo con langostinos, acompañado de arroz blanco', 50, '2024-11-15 00:24:28', 1, NULL),
(10, 'Tiradito de Pescado', 35.00000000, 'tiradito', 'Pescado crudo marinado en limón, ají amarillo y servido con cebollas y cilantro', 60, '2024-11-15 00:24:28', 1, NULL),
(11, 'Chicha de Jora', 6.00000000, 'chichadejora', 'Bebida ancestral peruana fermentada de maíz, con un sabor único y refrescante', 120, '2024-11-15 00:24:28', 2, NULL),
(12, 'Café con Leche', 5.50000000, 'cafeconleche', 'Café peruano de altura, acompañado de leche evaporada', 150, '2024-11-15 00:24:28', 2, NULL),
(13, 'Mote de Queso', 12.00000000, 'motequeso', 'Plato peruano hecho a base de mote de maíz, queso fresco y hierbas aromáticas', 60, '2024-11-15 00:24:28', 1, NULL),
(14, 'Chocotejas', 10.00000000, 'chocotejas', 'Deliciosas bombones rellenos de dulce de leche o manjarblanco, cubiertos de chocolate', 80, '2024-11-15 00:24:28', 4, NULL),
(15, 'Torta de Alcayota', 15.00000000, 'tortaalcayota', 'Postre peruano elaborado con alcayota, un dulce de calabaza con azúcar y especias', 50, '2024-11-15 00:24:28', 4, NULL),
(16, 'Sancochado', 27.00000000, 'sancochado', 'Sopa espesa de carne de res, papas, zanahorias, maíz y hierbas', 40, '2024-11-15 00:24:28', 1, NULL),
(17, 'Te de Muña', 7.00000000, 'temuna', 'Infusión de muña, una planta aromática andina, conocida por sus propiedades digestivas', 100, '2024-11-15 00:24:28', 2, NULL),
(18, 'Choclo con Queso', 8.00000000, 'chocloqueso', 'Mazorca de maíz peruano acompañada de queso fresco', 150, '2024-11-15 00:24:28', 1, NULL),
(19, 'Keke de Pera', 16.00000000, 'kekedapera', 'Delicioso pastel de pera, un postre tradicional peruano', 60, '2024-11-15 00:24:28', 4, NULL),
(20, 'Arroz con Mariscos', 38.00000000, 'arrozconmariscos', 'Plato peruano de arroz con mariscos frescos, sazonado con ají amarillo y hierbas', 40, '2024-11-15 00:24:28', 1, NULL),
(21, 'Choritos a la Chalaca', 25.00000000, 'choritoschalaca', 'Mejillones frescos acompañados de cebolla, tomate, ají y cilantro', 50, '2024-11-15 00:24:28', 1, NULL),
(22, 'Peruanito', 5.00000000, 'peruanito', 'Refresco natural de frutas de temporada con un toque de limón y hierba buena', 100, '2024-11-15 00:24:28', 2, NULL),
(23, 'Cecina de Cerdo', 28.00000000, 'cecinacerdo', 'Plato tradicional amazónico, carne de cerdo ahumada y frita, acompañada de yuca', 40, '2024-11-15 00:24:28', 1, NULL),
(24, 'Helado de Lucuma', 8.00000000, 'heladolucuma', 'Helado artesanal de lúcuma, una fruta autóctona de Perú', 80, '2024-11-15 00:24:28', 4, NULL),
(25, 'Arroz con Leche', 12.00000000, 'arrozconleche', 'Postre tradicional de arroz con leche, preparado con canela y clavo de olor', 60, '2024-11-15 00:24:28', 4, NULL),
(26, 'Sopa Seca', 24.00000000, 'sopaseca', 'Plato peruano de fideos al estilo de arroz con carne y verduras', 50, '2024-11-15 00:24:28', 1, NULL),
(27, 'Tequila Sour', 15.00000000, 'tequilasour', 'Cóctel inspirado en el Pisco Sour, pero preparado con tequila mexicano', 80, '2024-11-15 00:24:28', 2, NULL);


CREATE TABLE roles (
  IDRol int(11) NOT NULL,
  NomRol varchar(50) DEFAULT NULL,
  Descripcion varchar(100) DEFAULT NULL,
  nom_rol varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO roles (IDRol, NomRol, Descripcion, nom_rol) VALUES
(1, 'Administrador', 'Gestiona el restaurante', NULL),
(2, 'Mesero', 'Atiende a los clientes en las mesas', NULL),
(3, 'Cocinero', 'Encargado de preparar los platos', NULL),
(4, 'Delivery', 'Encargado de recibir recoger el pedido y entregarlo', NULL);

CREATE TABLE tarjetapago (
  IDTarjeta int(11) NOT NULL,
  NumeroTarjeta varchar(19) DEFAULT NULL,
  FechaVencimiento varchar(5) DEFAULT NULL,
  CodigoSeguridad varchar(4) DEFAULT NULL,
  NombreTitular varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO tarjetapago (IDTarjeta, NumeroTarjeta, FechaVencimiento, CodigoSeguridad, NombreTitular) VALUES
(1, '4111 1111 1111 1111', '12/25', '123', 'Juan Perez'),
(2, '5111 2222 3333 4444', '11/24', '456', 'Maria Gonzalez'),
(3, '3412 5678 9012 345', '05/26', '7890', 'Carlos Rodriguez'),
(4, '6011 1111 2222 3333', '08/23', '234', 'Ana Lopez'),
(5, '4111 2233 4455 6677', '03/25', '321', 'Luis Martinez'),
(6, '3488 1234 5678 901', '07/27', '5678', 'Elena Fernandez'),
(7, '4111 5555 6666 7777', '06/26', '890', 'Pedro Sanchez'),
(8, '3714 1234 5678 910', '09/24', '3456', 'Lucia Hernandez'),
(9, '6011 9876 5432 1098', '02/27', '654', 'Javier Perez'),
(10, '4111 2233 3344 5566', '10/26', '789', 'Marta Diaz');


CREATE TABLE tipopago (
  IDTipoPago int(11) NOT NULL,
  TipoPago varchar(100) DEFAULT NULL,
  tipo_pago varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO tipopago (IDTipoPago, TipoPago, tipo_pago) VALUES
(1, 'Efectivo', NULL),
(2, 'Tarjeta de Crédito', NULL),
(3, 'Tarjeta de Débito', NULL);

CREATE TABLE tipoproducto (
  IDTipo int(11) NOT NULL,
  NomTipo varchar(50) DEFAULT NULL,
  Descripcion varchar(150) DEFAULT NULL,
  IDCategoria int(11) DEFAULT NULL,
  nom_tipo varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE carrito
  ADD PRIMARY KEY (IDCarrito),
  ADD KEY IDProducto (IDProducto),
  ADD KEY fk_carrito_pedido (IDPedido);

ALTER TABLE categoriaproducto
  ADD PRIMARY KEY (IDCategoria);

ALTER TABLE cliente
  ADD PRIMARY KEY (IDCliente),
  ADD UNIQUE KEY dni (dni);

ALTER TABLE contador
  ADD PRIMARY KEY (IDContador),
  ADD KEY IDProducto (IDProducto);

ALTER TABLE delivery
  ADD PRIMARY KEY (IDDelivery),
  ADD KEY delivery_ibfk_1 (IDPedido),
  ADD KEY delivery_ibfk_2 (IDEmpleado);

ALTER TABLE detallecliente
  ADD PRIMARY KEY (IDDetalleCliente),
  ADD KEY IDCliente (IDCliente);

ALTER TABLE detalleempleado
  ADD PRIMARY KEY (IDDetalleEmp),
  ADD KEY detalleempleado_ibfk_1 (IDEmpleado);

ALTER TABLE direccion
  ADD PRIMARY KEY (IDDireccion),
  ADD KEY IDCliente (IDCliente),
  ADD KEY direccion_ibfk_2 (IDDistrito);

ALTER TABLE distrito
  ADD PRIMARY KEY (IDDistrito);

ALTER TABLE empleado
  ADD PRIMARY KEY (IDEmpleado),
  ADD KEY empleado_ibfk_1 (IDRol);

ALTER TABLE entrega
  ADD PRIMARY KEY (IDEntrega),
  ADD KEY entrega_ibfk_1 (IDDelivery);

ALTER TABLE factura
  ADD PRIMARY KEY (IDFactura);

ALTER TABLE intentos
  ADD PRIMARY KEY (IDIntentos),
  ADD KEY IDCliente (IDCliente);

ALTER TABLE pago
  ADD PRIMARY KEY (IDPago),
  ADD KEY IDTipoPago (IDTipoPago),
  ADD KEY IDFactura (IDFactura),
  ADD KEY pago_ibfk_1 (IDPedido);

ALTER TABLE pedido
  ADD PRIMARY KEY (IDPedido),
  ADD KEY IDCliente (IDCliente);

ALTER TABLE producto
  ADD PRIMARY KEY (IDProducto),
  ADD KEY IDCategoria (IDCategoria),
  ADD KEY IDTipo (IDTipo);

ALTER TABLE roles
  ADD PRIMARY KEY (IDRol);

ALTER TABLE tarjetapago
  ADD PRIMARY KEY (IDTarjeta),
  ADD UNIQUE KEY NumeroTarjeta (NumeroTarjeta);

ALTER TABLE tipopago
  ADD PRIMARY KEY (IDTipoPago);

ALTER TABLE tipoproducto
  ADD PRIMARY KEY (IDTipo),
  ADD KEY IDCategoria (IDCategoria);

ALTER TABLE carrito
  MODIFY IDCarrito int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

ALTER TABLE cliente
  MODIFY IDCliente int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

ALTER TABLE delivery
  MODIFY IDDelivery int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

ALTER TABLE detallecliente
  MODIFY IDDetalleCliente int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE direccion
  MODIFY IDDireccion int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE distrito
  MODIFY IDDistrito int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE empleado
  MODIFY IDEmpleado int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE entrega
  MODIFY IDEntrega int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE pago
  MODIFY IDPago int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

ALTER TABLE pedido
  MODIFY IDPedido int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

ALTER TABLE roles
  MODIFY IDRol int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE tarjetapago
  MODIFY IDTarjeta int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE tipopago
  MODIFY IDTipoPago int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE carrito
  ADD CONSTRAINT carrito_ibfk_1 FOREIGN KEY (IDProducto) REFERENCES producto (IDProducto),
  ADD CONSTRAINT fk_carrito_pedido FOREIGN KEY (IDPedido) REFERENCES pedido (IDPedido);

ALTER TABLE contador
  ADD CONSTRAINT contador_ibfk_1 FOREIGN KEY (IDProducto) REFERENCES producto (IDProducto);

ALTER TABLE delivery
  ADD CONSTRAINT delivery_ibfk_1 FOREIGN KEY (IDPedido) REFERENCES pedido (IDPedido),
  ADD CONSTRAINT delivery_ibfk_2 FOREIGN KEY (IDEmpleado) REFERENCES empleado (IDEmpleado);

ALTER TABLE detallecliente
  ADD CONSTRAINT detallecliente_ibfk_1 FOREIGN KEY (IDCliente) REFERENCES cliente (IDCliente);

ALTER TABLE detalleempleado
  ADD CONSTRAINT detalleempleado_ibfk_1 FOREIGN KEY (IDEmpleado) REFERENCES empleado (IDEmpleado);

ALTER TABLE direccion
  ADD CONSTRAINT direccion_ibfk_1 FOREIGN KEY (IDCliente) REFERENCES cliente (IDCliente),
  ADD CONSTRAINT direccion_ibfk_2 FOREIGN KEY (IDDistrito) REFERENCES distrito (IDDistrito);

ALTER TABLE empleado
  ADD CONSTRAINT empleado_ibfk_1 FOREIGN KEY (IDRol) REFERENCES roles (IDRol);

ALTER TABLE entrega
  ADD CONSTRAINT entrega_ibfk_1 FOREIGN KEY (IDDelivery) REFERENCES delivery (IDDelivery);

ALTER TABLE intentos
  ADD CONSTRAINT intentos_ibfk_1 FOREIGN KEY (IDCliente) REFERENCES cliente (IDCliente);

ALTER TABLE pago
  ADD CONSTRAINT pago_ibfk_1 FOREIGN KEY (IDPedido) REFERENCES pedido (IDPedido),
  ADD CONSTRAINT pago_ibfk_3 FOREIGN KEY (IDFactura) REFERENCES factura (IDFactura);

ALTER TABLE pedido
  ADD CONSTRAINT pedido_ibfk_1 FOREIGN KEY (IDCliente) REFERENCES cliente (IDCliente);

ALTER TABLE producto
  ADD CONSTRAINT producto_ibfk_1 FOREIGN KEY (IDCategoria) REFERENCES categoriaproducto (IDCategoria),
  ADD CONSTRAINT producto_ibfk_2 FOREIGN KEY (IDTipo) REFERENCES tipoproducto (IDTipo);

ALTER TABLE tipoproducto
  ADD CONSTRAINT tipoproducto_ibfk_1 FOREIGN KEY (IDCategoria) REFERENCES categoriaproducto (IDCategoria);
COMMIT;
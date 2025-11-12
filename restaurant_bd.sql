
--
-- Base de datos: `restaurantedb_sp`
--

-- --------------------------------------------------------
CREATE TABLE `carrito` (
  `IDCarrito` int(11) NOT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  `PrecioProducto` decimal(10,2) DEFAULT NULL,
  `IDProducto` int(11) DEFAULT NULL,
  `IDPedido` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `categoriaproducto` (
  `IDCategoria` int(11) NOT NULL,
  `NomCategoria` varchar(50) DEFAULT NULL,
  `Descripcion` varchar(100) DEFAULT NULL,
  `EstadoCategoria` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `cliente` (
  `IDCliente` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Apellido` varchar(50) DEFAULT NULL,
  `Usuario` varchar(50) DEFAULT NULL,
  `Correo` varchar(100) DEFAULT NULL,
  `Contrasena` varchar(100) DEFAULT NULL,
  `DNI` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `delivery` (
  `IDDelivery` int(11) NOT NULL,
  `CostoDelivery` decimal(10,2) DEFAULT NULL,
  `Estado` varchar(50) DEFAULT NULL,
  `FechaDelivery` datetime DEFAULT NULL,
  `FechaPedido` datetime DEFAULT NULL,
  `IDPedido` int(11) DEFAULT NULL,
  `IDEmpleado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `detallecliente` (
  `IDDetalleCliente` int(11) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Foto` varchar(100) DEFAULT NULL,
  `IDCliente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `detalleempleado` (
  `IDDetalleEmpleado` int(11) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Correo` varchar(200) DEFAULT NULL,
  `Foto` varchar(100) DEFAULT NULL,
  `IDEmpleado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `direccion` (
  `IDDireccion` int(11) NOT NULL,
  `DireccionCompleta` varchar(150) DEFAULT NULL,
  `Referencia` varchar(100) DEFAULT NULL,
  `IDCliente` int(11) DEFAULT NULL,
  `IDDistrito` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `distrito` (
  `IDDistrito` int(11) NOT NULL,
  `Distrito` varchar(100) DEFAULT NULL,
  `EstadoDistrito` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `empleado` (
  `IDEmpleado` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `ApellidoPaterno` varchar(50) DEFAULT NULL,
  `ApellidoMaterno` varchar(50) DEFAULT NULL,
  `Usuario` varchar(50) DEFAULT NULL,
  `Contrasena` varchar(150) DEFAULT NULL,
  `IDRol` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `entrega` (
  `IDEntrega` int(11) NOT NULL,
  `HoraSalida` datetime DEFAULT NULL,
  `HoraEntrega` datetime DEFAULT NULL,
  `IDDelivery` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `fotoproducto` (
  `IDFoto` int(11) NOT NULL,
  `IDProdHistoria` int(11) DEFAULT NULL,
  `FotoPrincipal` varchar(100) DEFAULT NULL,
  `FotoDos` varchar(100) DEFAULT NULL,
  `FotoTres` varchar(100) DEFAULT NULL,
  `FotoCuatro` varchar(100) DEFAULT NULL,
  `FotoCinco` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `pago` (
  `IDPago` int(11) NOT NULL,
  `PagoTotal` decimal(10,2) DEFAULT NULL,
  `FechaPago` datetime DEFAULT NULL,
  `IDPedido` int(11) DEFAULT NULL,
  `IDTarjeta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `pedido` (
  `IDPedido` int(11) NOT NULL,
  `FechaPedido` datetime DEFAULT NULL,
  `EstadoPedido` varchar(50) DEFAULT NULL,
  `MontoFinal` decimal(10,2) DEFAULT 0.00,
  `IGV` decimal(10,2) DEFAULT 0.00,
  `IDCliente` int(11) DEFAULT NULL,
  `IDDireccion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `producto` (
  `IDProducto` int(11) NOT NULL,
  `IDProdHistoria` int(11) DEFAULT NULL,
  `PrecioUnitario` decimal(10,2) DEFAULT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  `FechaProducto` datetime DEFAULT NULL,
  `EnCarta` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `productohistoria` (
  `IDProdHistoria` int(11) NOT NULL,
  `NomProducto` varchar(100) DEFAULT NULL,
  `Descripcion` varchar(100) DEFAULT NULL,
  `EstadoProducto` varchar(50) DEFAULT NULL,
  `IDCategoria` int(11) DEFAULT NULL,
  `EnCarta` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `roles` (
  `IDRol` int(11) NOT NULL,
  `NomRol` varchar(50) DEFAULT NULL,
  `Descripcion` varchar(100) DEFAULT NULL,
  `EstadoRoles` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- --------------------------------------------------------
CREATE TABLE `tarjetapago` (
  `IDTarjeta` int(11) NOT NULL,
  `NumeroTarjeta` varchar(20) DEFAULT NULL,
  `FechaVencimiento` varchar(8) DEFAULT NULL,
  `CodigoSeguridad` varchar(5) DEFAULT NULL,
  `NombreTitular` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


ALTER TABLE `carrito`
  ADD PRIMARY KEY (`IDCarrito`),
  ADD KEY `IDProducto` (`IDProducto`),
  ADD KEY `IDPedido` (`IDPedido`);

ALTER TABLE `categoriaproducto`
  ADD PRIMARY KEY (`IDCategoria`);

ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IDCliente`);

ALTER TABLE `delivery`
  ADD PRIMARY KEY (`IDDelivery`),
  ADD KEY `IDPedido` (`IDPedido`),
  ADD KEY `IDEmpleado` (`IDEmpleado`);

ALTER TABLE `detallecliente`
  ADD PRIMARY KEY (`IDDetalleCliente`),
  ADD KEY `IDCliente` (`IDCliente`);

ALTER TABLE `detalleempleado`
  ADD PRIMARY KEY (`IDDetalleEmpleado`),
  ADD KEY `IDEmpleado` (`IDEmpleado`);

ALTER TABLE `direccion`
  ADD PRIMARY KEY (`IDDireccion`),
  ADD KEY `IDCliente` (`IDCliente`),
  ADD KEY `IDDistrito` (`IDDistrito`);

ALTER TABLE `distrito`
  ADD PRIMARY KEY (`IDDistrito`);

ALTER TABLE `empleado`
  ADD PRIMARY KEY (`IDEmpleado`),
  ADD KEY `IDRol` (`IDRol`);

ALTER TABLE `entrega`
  ADD PRIMARY KEY (`IDEntrega`),
  ADD KEY `IDDelivery` (`IDDelivery`);

ALTER TABLE `fotoproducto`
  ADD PRIMARY KEY (`IDFoto`),
  ADD KEY `fk_foto_historia` (`IDProdHistoria`);

ALTER TABLE `pago`
  ADD PRIMARY KEY (`IDPago`),
  ADD KEY `IDPedido` (`IDPedido`),
  ADD KEY `pago_ibfk_2` (`IDTarjeta`);

ALTER TABLE `pedido`
  ADD PRIMARY KEY (`IDPedido`),
  ADD KEY `IDCliente` (`IDCliente`),
  ADD KEY `IDDireccion` (`IDDireccion`);

ALTER TABLE `producto`
  ADD PRIMARY KEY (`IDProducto`),
  ADD KEY `fk_producto_historia` (`IDProdHistoria`);

ALTER TABLE `productohistoria`
  ADD PRIMARY KEY (`IDProdHistoria`),
  ADD KEY `fk_historia_categoria` (`IDCategoria`);

ALTER TABLE `roles`
  ADD PRIMARY KEY (`IDRol`);

ALTER TABLE `tarjetapago`
  ADD PRIMARY KEY (`IDTarjeta`);


ALTER TABLE `carrito`
  MODIFY `IDCarrito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=206;

ALTER TABLE `categoriaproducto`
  MODIFY `IDCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `cliente`
  MODIFY `IDCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `delivery`
  MODIFY `IDDelivery` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97;

ALTER TABLE `detallecliente`
  MODIFY `IDDetalleCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `detalleempleado`
  MODIFY `IDDetalleEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

ALTER TABLE `direccion`
  MODIFY `IDDireccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

ALTER TABLE `distrito`
  MODIFY `IDDistrito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE `empleado`
  MODIFY `IDEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

ALTER TABLE `entrega`
  MODIFY `IDEntrega` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

ALTER TABLE `fotoproducto`
  MODIFY `IDFoto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

ALTER TABLE `pago`
  MODIFY `IDPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

ALTER TABLE `pedido`
  MODIFY `IDPedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

ALTER TABLE `producto`
  MODIFY `IDProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

ALTER TABLE `productohistoria`
  MODIFY `IDProdHistoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

ALTER TABLE `roles`
  MODIFY `IDRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `tarjetapago`
  MODIFY `IDTarjeta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


ALTER TABLE `carrito`
  ADD CONSTRAINT `carrito_ibfk_1` FOREIGN KEY (`IDProducto`) REFERENCES `producto` (`IDProducto`),
  ADD CONSTRAINT `carrito_ibfk_2` FOREIGN KEY (`IDPedido`) REFERENCES `pedido` (`IDPedido`);

ALTER TABLE `delivery`
  ADD CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`IDPedido`) REFERENCES `pedido` (`IDPedido`),
  ADD CONSTRAINT `delivery_ibfk_2` FOREIGN KEY (`IDEmpleado`) REFERENCES `empleado` (`IDEmpleado`);

ALTER TABLE `detallecliente`
  ADD CONSTRAINT `detallecliente_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `cliente` (`IDCliente`);

ALTER TABLE `detalleempleado`
  ADD CONSTRAINT `detalleempleado_ibfk_1` FOREIGN KEY (`IDEmpleado`) REFERENCES `empleado` (`IDEmpleado`);

ALTER TABLE `direccion`
  ADD CONSTRAINT `direccion_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `cliente` (`IDCliente`),
  ADD CONSTRAINT `direccion_ibfk_2` FOREIGN KEY (`IDDistrito`) REFERENCES `distrito` (`IDDistrito`);

ALTER TABLE `empleado`
  ADD CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`IDRol`) REFERENCES `roles` (`IDRol`);

ALTER TABLE `entrega`
  ADD CONSTRAINT `entrega_ibfk_1` FOREIGN KEY (`IDDelivery`) REFERENCES `delivery` (`IDDelivery`);

ALTER TABLE `fotoproducto`
  ADD CONSTRAINT `fk_foto_historia` FOREIGN KEY (`IDProdHistoria`) REFERENCES `productohistoria` (`IDProdHistoria`);

ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`IDPedido`) REFERENCES `pedido` (`IDPedido`),
  ADD CONSTRAINT `pago_ibfk_2` FOREIGN KEY (`IDTarjeta`) REFERENCES `tarjetapago` (`IDTarjeta`);

ALTER TABLE `pedido`
  ADD CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `cliente` (`IDCliente`),
  ADD CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`IDDireccion`) REFERENCES `direccion` (`IDDireccion`);

ALTER TABLE `producto`
  ADD CONSTRAINT `fk_producto_historia` FOREIGN KEY (`IDProdHistoria`) REFERENCES `productohistoria` (`IDProdHistoria`);

ALTER TABLE `productohistoria`
  ADD CONSTRAINT `fk_historia_categoria` FOREIGN KEY (`IDCategoria`) REFERENCES `categoriaproducto` (`IDCategoria`);
COMMIT;



INSERT INTO `carrito` (`IDCarrito`, `Cantidad`, `PrecioProducto`, `IDProducto`, `IDPedido`) VALUES
(1, 2, 45.00, 1, 1),
(2, 1, 8.00, 5, 1),
(3, 1, 38.00, 2, 2),
(4, 1, 10.00, 14, 2),
(5, 2, 35.00, 3, 3),
(6, 1, 6.00, 11, 3),
(7, 1, 40.00, 4, 4),
(8, 1, 15.00, 15, 4),
(9, 3, 28.00, 6, 5),
(10, 2, 12.00, 25, 5),
(11, 2, 35.00, 7, 6),
(12, 1, 8.00, 5, 6),
(13, 1, 30.00, 8, 7),
(14, 1, 10.00, 14, 7),
(15, 1, 25.00, 9, 8),
(16, 1, 6.00, 11, 8),
(17, 2, 40.00, 10, 9),
(18, 1, 12.00, 25, 9),
(19, 1, 38.00, 2, 10),
(20, 1, 8.00, 5, 10),
(21, 1, 45.00, 1, 11),
(22, 2, 8.00, 5, 11),
(23, 2, 38.00, 2, 12),
(24, 1, 10.00, 14, 12),
(25, 1, 35.00, 3, 13),
(26, 1, 6.00, 11, 13),
(27, 2, 40.00, 4, 14),
(28, 1, 15.00, 15, 14),
(29, 2, 28.00, 6, 15),
(30, 1, 12.00, 25, 15),
(31, 1, 35.00, 7, 16),
(32, 1, 8.00, 5, 16),
(33, 1, 30.00, 8, 17),
(34, 1, 10.00, 14, 17),
(35, 2, 25.00, 9, 18),
(36, 1, 6.00, 11, 18),
(37, 1, 40.00, 10, 19),
(38, 2, 12.00, 25, 19),
(39, 1, 38.00, 2, 20),
(40, 1, 8.00, 5, 20),
(41, 1, 45.00, 1, 21),
(42, 2, 8.00, 5, 21),
(43, 2, 38.00, 2, 22),
(44, 1, 10.00, 14, 22),
(45, 1, 35.00, 3, 23),
(46, 1, 6.00, 11, 23),
(47, 2, 40.00, 4, 24),
(48, 1, 15.00, 15, 24),
(49, 2, 28.00, 6, 25),
(50, 1, 12.00, 25, 25),
(51, 1, 35.00, 7, 26),
(52, 1, 8.00, 5, 26),
(53, 1, 30.00, 8, 27),
(54, 1, 10.00, 14, 27),
(55, 2, 25.00, 9, 28),
(56, 1, 6.00, 11, 28),
(57, 1, 40.00, 10, 29),
(58, 2, 12.00, 25, 29),
(59, 1, 38.00, 2, 30),
(60, 1, 8.00, 5, 30),
(61, 2, 40.00, 4, 31),
(62, 1, 10.00, 14, 31),
(63, 1, 45.00, 1, 32),
(64, 1, 8.00, 5, 32),
(65, 3, 28.00, 6, 33),
(66, 2, 12.00, 25, 33),
(67, 1, 30.00, 8, 34),
(68, 1, 15.00, 15, 34),
(69, 2, 35.00, 3, 35),
(70, 1, 6.00, 11, 35),
(71, 1, 25.00, 9, 36),
(72, 1, 8.00, 5, 36),
(73, 2, 38.00, 2, 37),
(74, 1, 10.00, 14, 37),
(75, 1, 40.00, 10, 38),
(76, 1, 12.00, 25, 38),
(77, 2, 28.00, 6, 39),
(78, 1, 15.00, 15, 39),
(79, 1, 35.00, 7, 40),
(80, 1, 6.00, 11, 40),
(81, 2, 38.00, 2, 41),
(82, 1, 8.00, 5, 41),
(83, 1, 45.00, 1, 42),
(84, 1, 10.00, 14, 42),
(85, 3, 28.00, 6, 43),
(86, 2, 12.00, 25, 43),
(87, 1, 35.00, 3, 44),
(88, 1, 15.00, 15, 44),
(89, 2, 30.00, 8, 45),
(90, 1, 6.00, 11, 45),
(91, 1, 25.00, 9, 46),
(92, 1, 10.00, 14, 46),
(93, 2, 40.00, 10, 47),
(94, 1, 8.00, 5, 47),
(95, 1, 38.00, 2, 48),
(96, 1, 12.00, 25, 48),
(97, 2, 35.00, 7, 49),
(98, 1, 15.00, 15, 49),
(99, 1, 40.00, 4, 50),
(100, 1, 6.00, 11, 50),
(101, 2, 38.00, 2, 51),
(102, 1, 8.00, 5, 51),
(103, 1, 45.00, 1, 52),
(104, 2, 12.00, 25, 52),
(105, 3, 28.00, 6, 53),
(106, 1, 15.00, 15, 53),
(107, 1, 35.00, 3, 54),
(108, 1, 6.00, 11, 54),
(109, 2, 30.00, 8, 55),
(110, 1, 10.00, 14, 55),
(111, 1, 25.00, 9, 56),
(112, 2, 8.00, 5, 56),
(113, 2, 40.00, 10, 57),
(114, 1, 15.00, 15, 57),
(115, 1, 38.00, 2, 58),
(116, 1, 12.00, 25, 58),
(117, 2, 35.00, 7, 59),
(118, 1, 6.00, 11, 59),
(119, 1, 40.00, 4, 60),
(120, 1, 8.00, 5, 60),
(121, 2, 40.00, 4, 61),
(122, 1, 12.00, 25, 61),
(123, 1, 38.00, 2, 62),
(124, 2, 10.00, 14, 62),
(125, 1, 30.00, 8, 63),
(126, 1, 6.00, 11, 63),
(127, 1, 12.00, 25, 63),
(128, 2, 28.00, 6, 64),
(129, 1, 15.00, 15, 64),
(130, 1, 35.00, 3, 65),
(131, 2, 8.00, 5, 65),
(132, 2, 40.00, 10, 66),
(133, 1, 12.00, 25, 66),
(134, 2, 35.00, 3, 67),
(135, 1, 8.00, 5, 67),
(136, 1, 38.00, 2, 68),
(137, 1, 15.00, 15, 68),
(138, 1, 25.00, 9, 69),
(139, 2, 6.00, 11, 69),
(140, 3, 28.00, 6, 70),
(141, 1, 10.00, 14, 70),
(142, 2, 40.00, 4, 71),
(143, 1, 12.00, 25, 71),
(144, 1, 30.00, 8, 72),
(145, 1, 15.00, 15, 72),
(146, 2, 35.00, 7, 73),
(147, 1, 8.00, 5, 73),
(148, 1, 38.00, 2, 74),
(149, 1, 10.00, 14, 74),
(150, 2, 40.00, 10, 75),
(151, 1, 6.00, 11, 75),
(152, 1, 35.00, 3, 76),
(153, 2, 6.00, 11, 76),
(154, 1, 40.00, 4, 77),
(155, 1, 12.00, 25, 77),
(156, 2, 28.00, 6, 78),
(157, 1, 15.00, 15, 78),
(158, 1, 38.00, 2, 79),
(159, 1, 8.00, 5, 79),
(160, 2, 35.00, 7, 80),
(161, 1, 10.00, 14, 80),
(162, 2, 35.00, 3, 81),
(163, 1, 8.00, 5, 81),
(164, 1, 38.00, 2, 82),
(165, 2, 12.00, 25, 82),
(166, 1, 40.00, 4, 83),
(167, 1, 6.00, 11, 83),
(168, 2, 28.00, 6, 84),
(169, 1, 15.00, 15, 84),
(170, 3, 30.00, 8, 85),
(171, 1, 10.00, 14, 85),
(172, 1, 25.00, 9, 86),
(173, 1, 12.00, 25, 86),
(174, 2, 40.00, 10, 87),
(175, 1, 8.00, 5, 87),
(176, 1, 38.00, 2, 88),
(177, 1, 15.00, 15, 88),
(178, 2, 35.00, 3, 89),
(179, 1, 8.00, 5, 89),
(180, 1, 38.00, 2, 90),
(181, 1, 10.00, 14, 90),
(182, 1, 25.00, 9, 91),
(183, 2, 12.00, 25, 91),
(184, 1, 35.00, 3, 92),
(185, 1, 8.00, 5, 92),
(186, 2, 30.00, 8, 93),
(187, 1, 10.00, 14, 93),
(188, 1, 25.00, 9, 94),
(189, 1, 6.00, 11, 94),
(191, 1, 15.00, 3, 95),
(192, 1, 35.00, 2, 96),
(193, 2, 40.00, 6, 96),
(194, 2, 90.00, 1, 97),
(195, 3, 114.00, 8, 98),
(196, 1, 8.00, 5, 99),
(197, 2, 30.00, 3, 100),
(198, 1, 18.00, 4, 101),
(200, 2, 35.00, 2, 102),
(201, 1, 20.00, 6, 102),
(202, 1, 5.50, 12, 103),
(203, 1, 32.00, 9, 103),
(204, 1, 15.00, 3, 104),
(205, 1, 10.00, 7, 104);

INSERT INTO `tarjetapago` (`IDTarjeta`, `NumeroTarjeta`, `FechaVencimiento`, `CodigoSeguridad`, `NombreTitular`) VALUES
(1, '1234567812345678', '12/25', '123', 'Luis Gustavo Alegre'),
(2, '1111222233334444', '06/26', '321', 'Juan Perez');

INSERT INTO `categoriaproducto` (`IDCategoria`, `NomCategoria`, `Descripcion`, `EstadoCategoria`) VALUES
(1, 'Platillos', 'Principales platos peruanos como Lomo Saltado y Ceviche', 'Habilitado'),
(2, 'Bebidas', 'Bebidas tradicionales como Chicha Morada y Pisco Sour', 'Habilitado'),
(3, 'Entradas', 'Entradas como Papa a la Huancaína y Causa Limeña', 'Habilitado'),
(4, 'Postres', 'Postres típicos peruanos', 'Habilitado');


INSERT INTO `cliente` (`IDCliente`, `Nombre`, `Apellido`, `Usuario`, `Correo`, `Contrasena`, `DNI`) VALUES
(1, 'Luis', 'Ramírez', 'luis', 'luis.ramirez@gmail.com', '502ff82f7f1f8218dd41201fe4353687', '74562145'),
(2, 'María', 'Fernández', 'maria', 'maria.fernandez@gmail.com', '263bce650e68ab4e23f28263760b9fa5', '74895632'),
(3, 'Carlos', 'Ramírez', 'carlos', 'carlos.ramirez@gmail.com', 'dc599a9972fde3045dab59dbd1ae170b', '74985213'),
(4, 'Lucía', 'Fernández', 'lucia', 'lucia.fernandez@gmail.com', '3ba430337eb30f5fd7569451b5dfdf32', '75328416'),
(5, 'Carlos', 'Mendoza', 'carlos', 'carlos.mendoza@gmail.com', 'dc599a9972fde3045dab59dbd1ae170b', '71928453'),
(6, 'Gabriela', 'Rojas', 'gabriela', 'gabriela.rojas@gmail.com', '276e697e74e8b5264465139a480db556', '74219385'),
(7, 'Felipe', 'Mendoza', 'felipe', 'felipe.mendoza@gmail.com', '7e04da88cbb8cc933c7b89fbfe121cca', '74981236'),
(8, 'Valeria', 'Rojas', 'valeria', 'valeria.rojas@gmail.com', '7902b7c0be5cedb6fbada8d4c7fc42a0', '71594236'),
(9, 'Andrés', 'Torres', 'andres', 'andres.torres@gmail.com', '231badb19b93e44f47da1bd64a8147f2', '72984563'),
(10, 'Paola', 'Vargas', 'paola', 'paola.vargas@gmail.com', '72a86026abb289634ec64d7f3b544f0c', '70539824'),
(11, 'Oscar', 'Campos', 'oscar', 'oscar.campos@gmail.com', 'f156e7995d521f30e6c59a3d6c75e1e5', '72548910'),
(12, 'Marta', 'Herrera', 'marta', 'marta.herrera@gmail.com', 'a763a66f984948ca463b081bf0f0e6d0', '73549821'),
(13, 'Renzo', 'Valdivia', 'renzo', 'renzo.valdivia@gmail.com', '674cba521a0445ef3168b298509bf88e', '71548962');

INSERT INTO `delivery` (`IDDelivery`, `CostoDelivery`, `Estado`, `FechaDelivery`, `FechaPedido`, `IDPedido`, `IDEmpleado`) VALUES
(1, NULL, 'Entregado', '2025-01-10 13:00:00', '2025-01-10 12:15:00', 1, 3),
(2, NULL, 'Entregado', '2025-02-05 19:30:00', '2025-02-05 19:00:00', 2, 4),
(3, NULL, 'Entregado', '2025-03-09 14:30:00', '2025-03-09 13:45:00', 3, 5),
(4, NULL, 'Entregado', '2025-04-14 21:00:00', '2025-04-14 20:30:00', 4, 6),
(5, NULL, 'Entregado', '2025-05-22 14:45:00', '2025-05-22 14:10:00', 5, 7),
(6, NULL, 'Entregado', '2025-06-18 19:00:00', '2025-06-18 18:25:00', 6, 8),
(7, NULL, 'Entregado', '2025-07-07 13:15:00', '2025-07-07 12:40:00', 7, 9),
(8, NULL, 'En camino', '2025-08-11 19:40:00', '2025-08-11 19:15:00', 8, 10),
(9, NULL, 'Pendiente', '2025-09-03 13:35:00', '2025-09-03 13:00:00', 9, 11),
(10, NULL, 'Pendiente', '2025-10-25 17:50:00', '2025-10-25 17:20:00', 10, 12),
(11, NULL, 'Entregado', '2025-01-12 12:30:00', '2025-01-12 11:45:00', 11, 3),
(12, NULL, 'Entregado', '2025-02-09 19:00:00', '2025-02-09 18:20:00', 12, 4),
(13, NULL, 'Entregado', '2025-03-15 14:40:00', '2025-03-15 14:00:00', 13, 5),
(14, NULL, 'Entregado', '2025-04-20 20:10:00', '2025-04-20 19:40:00', 14, 6),
(15, NULL, 'Entregado', '2025-05-28 14:00:00', '2025-05-28 13:30:00', 15, 7),
(16, NULL, 'Entregado', '2025-06-21 17:40:00', '2025-06-21 17:10:00', 16, 8),
(17, NULL, 'Entregado', '2025-07-10 13:00:00', '2025-07-10 12:30:00', 17, 9),
(18, NULL, 'En camino', '2025-08-18 19:15:00', '2025-08-18 18:45:00', 18, 10),
(19, NULL, 'Pendiente', '2025-09-07 13:15:00', '2025-09-07 12:50:00', 19, 11),
(20, NULL, 'Pendiente', '2025-10-30 17:30:00', '2025-10-30 17:00:00', 20, 12),
(21, NULL, 'Entregado', '2025-01-25 13:40:00', '2025-01-25 13:10:00', 21, 3),
(22, NULL, 'Entregado', '2025-02-20 19:40:00', '2025-02-20 19:00:00', 22, 4),
(23, NULL, 'Entregado', '2025-03-18 13:25:00', '2025-03-18 12:45:00', 23, 5),
(24, NULL, 'Entregado', '2025-04-25 19:00:00', '2025-04-25 18:30:00', 24, 6),
(25, NULL, 'Entregado', '2025-05-29 13:45:00', '2025-05-29 13:15:00', 25, 7),
(26, NULL, 'Entregado', '2025-06-23 18:20:00', '2025-06-23 17:50:00', 26, 8),
(27, NULL, 'Entregado', '2025-07-20 12:10:00', '2025-07-20 11:40:00', 27, 9),
(28, NULL, 'En camino', '2025-08-19 19:40:00', '2025-08-19 19:10:00', 28, 10),
(29, NULL, 'Pendiente', '2025-09-15 14:45:00', '2025-09-15 14:25:00', 29, 11),
(30, NULL, 'Pendiente', '2025-10-28 18:30:00', '2025-10-28 18:10:00', 30, 12),
(31, NULL, 'Entregado', '2025-01-12 13:00:00', '2025-01-12 12:20:00', 31, 3),
(32, NULL, 'Entregado', '2025-02-08 18:50:00', '2025-02-08 18:10:00', 32, 4),
(33, NULL, 'Entregado', '2025-03-10 14:10:00', '2025-03-10 13:30:00', 33, 5),
(34, NULL, 'Entregado', '2025-04-15 20:15:00', '2025-04-15 19:45:00', 34, 6),
(35, NULL, 'Entregado', '2025-05-19 14:30:00', '2025-05-19 14:00:00', 35, 7),
(36, NULL, 'Entregado', '2025-06-14 18:10:00', '2025-06-14 17:40:00', 36, 8),
(37, NULL, 'Entregado', '2025-07-09 13:20:00', '2025-07-09 12:50:00', 37, 9),
(38, NULL, 'En camino', '2025-08-17 19:50:00', '2025-08-17 19:25:00', 38, 10),
(39, NULL, 'Pendiente', '2025-09-08 13:40:00', '2025-09-08 13:15:00', 39, 11),
(40, NULL, 'Pendiente', '2025-10-23 18:20:00', '2025-10-23 17:55:00', 40, 12),
(41, NULL, 'Entregado', '2025-01-18 13:50:00', '2025-01-18 13:10:00', 41, 3),
(42, NULL, 'Entregado', '2025-02-14 20:00:00', '2025-02-14 19:25:00', 42, 4),
(43, NULL, 'Entregado', '2025-03-22 13:20:00', '2025-03-22 12:45:00', 43, 5),
(44, NULL, 'Entregado', '2025-04-19 21:10:00', '2025-04-19 20:40:00', 44, 6),
(45, NULL, 'Entregado', '2025-05-23 14:00:00', '2025-05-23 13:30:00', 45, 7),
(46, NULL, 'Entregado', '2025-06-20 18:40:00', '2025-06-20 18:15:00', 46, 8),
(47, NULL, 'Entregado', '2025-07-16 13:20:00', '2025-07-16 12:55:00', 47, 9),
(48, NULL, 'En camino', '2025-08-19 19:35:00', '2025-08-19 19:10:00', 48, 10),
(49, NULL, 'Pendiente', '2025-09-15 13:50:00', '2025-09-15 13:25:00', 49, 11),
(50, NULL, 'Pendiente', '2025-10-28 18:25:00', '2025-10-28 18:00:00', 50, 12),
(51, NULL, 'Entregado', '2025-01-22 13:50:00', '2025-01-22 13:20:00', 51, 3),
(52, NULL, 'Entregado', '2025-02-15 19:40:00', '2025-02-15 19:15:00', 52, 4),
(53, NULL, 'Entregado', '2025-03-24 12:55:00', '2025-03-24 12:35:00', 53, 5),
(54, NULL, 'Entregado', '2025-04-20 20:40:00', '2025-04-20 20:20:00', 54, 6),
(55, NULL, 'Entregado', '2025-05-25 13:35:00', '2025-05-25 13:15:00', 55, 7),
(56, NULL, 'Entregado', '2025-06-21 18:45:00', '2025-06-21 18:25:00', 56, 8),
(57, NULL, 'Entregado', '2025-07-18 13:05:00', '2025-07-18 12:45:00', 57, 9),
(58, NULL, 'En camino', '2025-08-20 19:20:00', '2025-08-20 19:00:00', 58, 10),
(59, NULL, 'Pendiente', '2025-09-16 13:55:00', '2025-09-16 13:35:00', 59, 11),
(60, NULL, 'Pendiente', '2025-10-30 18:30:00', '2025-10-30 18:10:00', 60, 12),
(61, NULL, 'Entregado', '2025-01-30 13:00:00', '2025-01-30 12:30:00', 61, 3),
(62, NULL, 'Entregado', '2025-03-02 19:15:00', '2025-03-02 18:40:00', 62, 4),
(63, NULL, 'Entregado', '2025-04-12 14:40:00', '2025-04-12 14:15:00', 63, 5),
(64, NULL, 'Entregado', '2025-06-25 20:25:00', '2025-06-25 20:00:00', 64, 6),
(65, NULL, 'En camino', '2025-08-05 13:20:00', '2025-08-05 13:00:00', 65, 7),
(66, NULL, 'Pendiente', '2025-10-14 19:50:00', '2025-10-14 19:30:00', 66, 8),
(67, NULL, 'Entregado', '2025-01-12 12:40:00', '2025-01-12 12:00:00', 67, 3),
(68, NULL, 'Entregado', '2025-02-18 13:40:00', '2025-02-18 13:15:00', 68, 4),
(69, NULL, 'Entregado', '2025-03-28 19:40:00', '2025-03-28 19:10:00', 69, 5),
(70, NULL, 'Entregado', '2025-04-30 14:50:00', '2025-04-30 14:25:00', 70, 6),
(71, NULL, 'Entregado', '2025-06-08 20:35:00', '2025-06-08 20:10:00', 71, 7),
(72, NULL, 'Entregado', '2025-07-22 18:25:00', '2025-07-22 18:00:00', 72, 8),
(73, NULL, 'En camino', '2025-08-30 14:15:00', '2025-08-30 13:50:00', 73, 9),
(74, NULL, 'Pendiente', '2025-09-25 19:30:00', '2025-09-25 19:00:00', 74, 10),
(75, NULL, 'Pendiente', '2025-10-29 13:10:00', '2025-10-29 12:40:00', 75, 11),
(76, NULL, 'Entregado', '2025-01-10 11:55:00', '2025-01-10 11:20:00', 76, 4),
(77, NULL, 'Entregado', '2025-02-21 18:55:00', '2025-02-21 18:30:00', 77, 5),
(78, NULL, 'Entregado', '2025-04-07 13:10:00', '2025-04-07 12:45:00', 78, 6),
(79, NULL, 'En camino', '2025-07-19 19:30:00', '2025-07-19 19:00:00', 79, 7),
(80, NULL, 'Pendiente', '2025-10-15 13:40:00', '2025-10-15 13:10:00', 80, 8),
(81, NULL, 'Entregado', '2025-01-12 12:45:00', '2025-01-12 12:10:00', 81, 3),
(82, NULL, 'Entregado', '2025-02-25 19:50:00', '2025-02-25 19:20:00', 82, 4),
(83, NULL, 'Entregado', '2025-03-30 13:50:00', '2025-03-30 13:15:00', 83, 5),
(84, NULL, 'Entregado', '2025-05-08 20:25:00', '2025-05-08 20:00:00', 84, 6),
(85, NULL, 'En camino', '2025-06-17 19:10:00', '2025-06-17 18:45:00', 85, 7),
(86, NULL, 'Pendiente', '2025-08-22 12:55:00', '2025-08-22 12:30:00', 86, 8),
(87, NULL, 'Pendiente', '2025-09-14 20:05:00', '2025-09-14 19:40:00', 87, 9),
(88, NULL, 'Pendiente', '2025-10-27 14:35:00', '2025-10-27 14:10:00', 88, 10),
(89, NULL, 'Entregado', '2025-02-10 12:40:00', '2025-02-10 12:00:00', 89, 3),
(90, NULL, 'Entregado', '2025-03-05 13:45:00', '2025-03-05 13:10:00', 90, 4),
(91, NULL, 'Pendiente', '2025-05-20 19:55:00', '2025-05-20 19:30:00', 91, 5),
(92, NULL, 'Entregado', '2025-04-02 13:15:00', '2025-04-02 12:45:00', 92, 6),
(93, NULL, 'Entregado', '2025-06-11 19:00:00', '2025-06-11 18:30:00', 93, 7),
(94, NULL, 'Pendiente', '2025-09-23 13:40:00', '2025-09-23 13:10:00', 94, 8),
(95, 5.00, 'Pendiente', '2025-11-09 17:51:24', NULL, 103, NULL),
(96, 5.00, 'Pendiente', '2025-11-09 22:31:16', NULL, 104, NULL);

INSERT INTO `detallecliente` (`IDDetalleCliente`, `Telefono`, `Foto`, `IDCliente`) VALUES
(1, '987456321', 'luis.jpg', 1),
(2, '986123457', 'maria.jpg', 2),
(3, '987654321', 'carlos.jpg', 3),
(4, '986532147', 'lucia.jpg', 4),
(5, '985674213', 'carlos.jpg', 5),
(6, '986573214', 'gabriela.jpg', 6),
(7, '985612347', 'felipe.jpg', 7),
(8, '984125697', 'valeria.jpg', 8),
(9, '985412367', 'andres.jpg', 9),
(10, '991203875', 'paola.jpg', 10),
(11, '998745120', 'oscar.jpg', 11),
(12, '993214785', 'marta.jpg', 12),
(13, '987512034', 'renzo.jpg', 13);

INSERT INTO `detalleempleado` (`IDDetalleEmpleado`, `Telefono`, `Correo`, `Foto`, `IDEmpleado`) VALUES
(1, '555-1010', 'juanr@empresa.com', 'juan.jpg', 1),
(2, '555-2020', 'luisd@empresa.com', 'luis.jpg', 2),
(3, '555-3030', 'carlos@empresa.com', 'carlos.jpg', 3),
(4, '555-4040', 'mario@empresa.com', 'mario.jpg', 4),
(5, '555-5050', 'diego@empresa.com', 'diego.jpg', 5),
(6, '555-6060', 'jose@empresa.com', 'jose.jpg', 6),
(7, '555-7070', 'rafael@empresa.com', 'rafael.jpg', 7),
(8, '555-8080', 'miguel@empresa.com', 'miguel.jpg', 8),
(9, '555-9090', 'pedro@empresa.com', 'pedro.jpg', 9),
(10, '555-1111', 'jorge@empresa.com', 'jorge.jpg', 10),
(11, '555-1212', 'andres@empresa.com', 'andres.jpg', 11),
(12, '555-1313', 'ricardo@empresa.com', 'ricardo.jpg', 12),
(14, NULL, 'luis@gmail.com', NULL, 15);

INSERT INTO `direccion` (`IDDireccion`, `DireccionCompleta`, `Referencia`, `IDCliente`, `IDDistrito`) VALUES
(1, 'Av. Los Jardines 250', 'Frente al parque central', 1, 1),
(2, 'Jr. Las Palmeras 560', 'A una cuadra del mercado central', 2, 2),
(3, 'Av. Los Héroes 234', 'Frente al parque principal', 3, 3),
(4, 'Jr. Los Álamos 580', 'Cerca a la universidad local', 4, 2),
(5, 'Av. Prolongación Primavera 742', 'Frente al parque ecológico', 5, 3),
(6, 'Calle Los Nogales 223', 'Cerca al colegio San Ignacio', 6, 3),
(7, 'Jr. Las Violetas 320', 'Frente al parque La Esperanza', 7, 5),
(8, 'Calle Los Cedros 280', 'A una cuadra del colegio Santa Ana', 8, 6),
(9, 'Av. Primavera 410', 'Frente al parque central', 9, 2),
(10, 'Jr. Huancavelica 320', 'Cerca al mercado central', 10, 3),
(11, 'Av. San Luis 600', 'Frente al parque San Luis', 11, 5),
(12, 'Jr. Las Magnolias 250', 'Al costado de la bodega El Sol', 12, 4),
(13, 'Calle Los Girasoles 420', 'A una cuadra del colegio San Antonio', 13, 2),
(14, 'Av. Los Alamos 321', 'Residencia A', 1, 4),
(15, 'Av. Primaveras 123', 'Torre B', 1, 7);


INSERT INTO `distrito` (`IDDistrito`, `Distrito`, `EstadoDistrito`) VALUES
(1, 'Miraflores', 'Habilitado'),
(2, 'San Isidro', 'Habilitado'),
(3, 'Barranco', 'Habilitado'),
(4, 'Surco', 'Habilitado'),
(5, 'Comas', 'Habilitado'),
(6, 'El Agustino', 'Habilitado'),
(7, 'Lince', 'Habilitado'),
(8, 'Surquilo', 'Habilitado'),
(9, 'Callao', 'Habilitado'),
(10, 'San Martín de Porres', 'Habilitado'),
(11, 'Pueblo Libre', 'Habilitado');


INSERT INTO `empleado` (`IDEmpleado`, `Nombre`, `ApellidoPaterno`, `ApellidoMaterno`, `Usuario`, `Contrasena`, `IDRol`) VALUES
(1, 'Juan', 'Ramirez', 'Torres', 'juanr', '16a8d6e0d509a7e491d252ea37bfbb23', 1),
(2, 'Luis', 'Diaz', 'Rojas', 'luisd', 'c34b2ecc24e0f948a5e99a3c94403e29', 2),
(3, 'Carlos', 'Lopez', 'Perez', 'carlos', 'dc599a9972fde3045dab59dbd1ae170b', 4),
(4, 'Mario', 'Gomez', 'Ramos', 'mario', 'de2f15d014d40b93578d255e6221fd60', 4),
(5, 'Diego', 'Fernandez', 'Castro', 'diego', '078c007bd92ddec308ae2f5115c1775d', 4),
(6, 'Jose', 'Hernandez', 'Vargas', 'jose', '662eaa47199461d01a623884080934ab', 4),
(7, 'Rafael', 'Sanchez', 'Suarez', 'rafael', '9135d8523ad3da99d8a4eb83afac13d1', 4),
(8, 'Miguel', 'Torres', 'Ruiz', 'miguel', '9eb0c9605dc81a68731f61b3e0838937', 4),
(9, 'Pedro', 'Navarro', 'Soto', 'pedro', 'c6cc8094c2dc07b700ffcc36d64e2138', 4),
(10, 'Jorge', 'Vargas', 'Medina', 'jorge', 'd67326a22642a324aa1b0745f2f17abb', 4),
(11, 'Andres', 'Paredes', 'Lozano', 'andres', '231badb19b93e44f47da1bd64a8147f2', 4),
(12, 'Ricardo', 'Morales', 'Huaman', 'ricardo', '6720720054e9d24fbf6c20a831ff287e', 4),
(15, 'gustavo', 'alvarado', 'alegre', 'gustav0', 'gustav0', 1);

INSERT INTO `entrega` (`IDEntrega`, `HoraSalida`, `HoraEntrega`, `IDDelivery`) VALUES
(1, '2025-01-10 12:35:00', '2025-01-10 13:00:00', 1),
(2, '2025-02-05 19:10:00', '2025-02-05 19:30:00', 2),
(3, '2025-03-09 14:00:00', '2025-03-09 14:30:00', 3),
(4, '2025-04-14 20:45:00', '2025-04-14 21:00:00', 4),
(5, '2025-05-22 14:30:00', '2025-05-22 14:45:00', 5),
(6, '2025-06-18 18:45:00', '2025-06-18 19:00:00', 6),
(7, '2025-07-07 13:00:00', '2025-07-07 13:15:00', 7),
(8, '2025-08-11 19:25:00', '2025-08-11 19:40:00', 8),
(9, '2025-09-03 13:15:00', '2025-09-03 13:35:00', 9),
(10, '2025-10-25 17:35:00', '2025-10-25 17:50:00', 10),
(11, '2025-01-12 12:10:00', '2025-01-12 12:30:00', 11),
(12, '2025-02-09 18:40:00', '2025-02-09 19:00:00', 12),
(13, '2025-03-15 14:20:00', '2025-03-15 14:40:00', 13),
(14, '2025-04-20 19:50:00', '2025-04-20 20:10:00', 14),
(15, '2025-05-28 13:40:00', '2025-05-28 14:00:00', 15),
(16, '2025-06-21 17:20:00', '2025-06-21 17:40:00', 16),
(17, '2025-07-10 12:40:00', '2025-07-10 13:00:00', 17),
(18, '2025-08-18 18:55:00', '2025-08-18 19:15:00', 18),
(19, '2025-09-07 13:00:00', '2025-09-07 13:15:00', 19),
(20, '2025-10-30 17:10:00', '2025-10-30 17:30:00', 20),
(21, '2025-01-25 13:20:00', '2025-01-25 13:40:00', 21),
(22, '2025-02-20 19:10:00', '2025-02-20 19:40:00', 22),
(23, '2025-03-18 12:55:00', '2025-03-18 13:25:00', 23),
(24, '2025-04-25 18:40:00', '2025-04-25 19:00:00', 24),
(25, '2025-05-29 13:25:00', '2025-05-29 13:45:00', 25),
(26, '2025-06-23 18:00:00', '2025-06-23 18:20:00', 26),
(27, '2025-07-20 11:50:00', '2025-07-20 12:10:00', 27),
(28, '2025-08-19 19:20:00', '2025-08-19 19:40:00', 28),
(29, '2025-09-15 14:35:00', '2025-09-15 14:45:00', 29),
(30, '2025-10-28 18:20:00', '2025-10-28 18:30:00', 30),
(31, '2025-01-12 12:40:00', '2025-01-12 13:00:00', 31),
(32, '2025-02-08 18:30:00', '2025-02-08 18:50:00', 32),
(33, '2025-03-10 13:50:00', '2025-03-10 14:10:00', 33),
(34, '2025-04-15 20:00:00', '2025-04-15 20:15:00', 34),
(35, '2025-05-19 14:15:00', '2025-05-19 14:30:00', 35),
(36, '2025-06-14 17:55:00', '2025-06-14 18:10:00', 36),
(37, '2025-07-09 13:00:00', '2025-07-09 13:20:00', 37),
(38, '2025-08-17 19:35:00', '2025-08-17 19:50:00', 38),
(39, '2025-09-08 13:25:00', '2025-09-08 13:40:00', 39),
(40, '2025-10-23 18:05:00', '2025-10-23 18:20:00', 40),
(41, '2025-01-18 13:25:00', '2025-01-18 13:50:00', 41),
(42, '2025-02-14 19:40:00', '2025-02-14 20:00:00', 42),
(43, '2025-03-22 13:00:00', '2025-03-22 13:20:00', 43),
(44, '2025-04-19 20:55:00', '2025-04-19 21:10:00', 44),
(45, '2025-05-23 13:45:00', '2025-05-23 14:00:00', 45),
(46, '2025-06-20 18:25:00', '2025-06-20 18:40:00', 46),
(47, '2025-07-16 13:05:00', '2025-07-16 13:20:00', 47),
(48, '2025-08-19 19:20:00', '2025-08-19 19:35:00', 48),
(49, '2025-09-15 13:35:00', '2025-09-15 13:50:00', 49),
(50, '2025-10-28 18:10:00', '2025-10-28 18:25:00', 50),
(51, '2025-01-22 13:30:00', '2025-01-22 13:50:00', 51),
(52, '2025-02-15 19:25:00', '2025-02-15 19:40:00', 52),
(53, '2025-03-24 12:45:00', '2025-03-24 12:55:00', 53),
(54, '2025-04-20 20:30:00', '2025-04-20 20:40:00', 54),
(55, '2025-05-25 13:25:00', '2025-05-25 13:35:00', 55),
(56, '2025-06-21 18:35:00', '2025-06-21 18:45:00', 56),
(57, '2025-07-18 12:55:00', '2025-07-18 13:05:00', 57),
(58, '2025-08-20 19:10:00', '2025-08-20 19:20:00', 58),
(59, '2025-09-16 13:45:00', '2025-09-16 13:55:00', 59),
(60, '2025-10-30 18:20:00', '2025-10-30 18:30:00', 60),
(61, '2025-01-30 12:40:00', '2025-01-30 13:00:00', 61),
(62, '2025-03-02 18:50:00', '2025-03-02 19:15:00', 62),
(63, '2025-04-12 14:25:00', '2025-04-12 14:40:00', 63),
(64, '2025-06-25 20:10:00', '2025-06-25 20:25:00', 64),
(65, '2025-08-05 13:10:00', '2025-08-05 13:20:00', 65),
(66, '2025-10-14 19:40:00', '2025-10-14 19:50:00', 66),
(67, '2025-01-12 12:15:00', '2025-01-12 12:40:00', 67),
(68, '2025-02-18 13:25:00', '2025-02-18 13:40:00', 68),
(69, '2025-03-28 19:20:00', '2025-03-28 19:40:00', 69),
(70, '2025-04-30 14:35:00', '2025-04-30 14:50:00', 70),
(71, '2025-06-08 20:20:00', '2025-06-08 20:35:00', 71),
(72, '2025-07-22 18:10:00', '2025-07-22 18:25:00', 72),
(73, '2025-08-30 14:00:00', '2025-08-30 14:15:00', 73),
(74, '2025-09-25 19:10:00', '2025-09-25 19:30:00', 74),
(75, '2025-10-29 12:55:00', '2025-10-29 13:10:00', 75),
(76, '2025-01-10 11:30:00', '2025-01-10 11:55:00', 76),
(77, '2025-02-21 18:40:00', '2025-02-21 18:55:00', 77),
(78, '2025-04-07 12:55:00', '2025-04-07 13:10:00', 78),
(79, '2025-07-19 19:10:00', '2025-07-19 19:30:00', 79),
(80, '2025-10-15 13:20:00', '2025-10-15 13:40:00', 80),
(81, '2025-01-12 12:25:00', '2025-01-12 12:45:00', 81),
(82, '2025-02-25 19:35:00', '2025-02-25 19:50:00', 82),
(83, '2025-03-30 13:25:00', '2025-03-30 13:50:00', 83),
(84, '2025-05-08 20:10:00', '2025-05-08 20:25:00', 84),
(85, '2025-06-17 18:55:00', '2025-06-17 19:10:00', 85),
(86, '2025-08-22 12:40:00', '2025-08-22 12:55:00', 86),
(87, '2025-09-14 19:50:00', '2025-09-14 20:05:00', 87),
(88, '2025-10-27 14:20:00', '2025-10-27 14:35:00', 88),
(89, '2025-02-10 12:25:00', '2025-02-10 12:40:00', 89),
(90, '2025-03-05 13:25:00', '2025-03-05 13:45:00', 90),
(91, '2025-05-20 19:40:00', '2025-05-20 19:55:00', 91),
(92, '2025-04-02 13:00:00', '2025-04-02 13:15:00', 92),
(93, '2025-06-11 18:45:00', '2025-06-11 19:00:00', 93),
(94, '2025-09-23 13:25:00', '2025-09-23 13:40:00', 94);

INSERT INTO `fotoproducto` (`IDFoto`, `IDProdHistoria`, `FotoPrincipal`, `FotoDos`, `FotoTres`, `FotoCuatro`, `FotoCinco`) VALUES
(1, 1, 'lomosaltado.jpg', NULL, NULL, NULL, NULL),
(2, 2, 'ceviche.jpg', NULL, NULL, NULL, NULL),
(3, 3, 'papaalahuancaina.jpg', NULL, NULL, NULL, NULL),
(4, 4, 'causalimane%C3%B1a.jpg', NULL, NULL, NULL, NULL),
(5, 5, 'chichamorada.jpg', NULL, NULL, NULL, NULL),
(6, 6, 'piscosour.jpg', NULL, NULL, NULL, NULL),
(7, 7, 'mazamorramorada.jpg', NULL, NULL, NULL, NULL),
(8, 8, 'secocabrito.jpg', NULL, NULL, NULL, NULL),
(9, 9, 'ajidelangostinos.jpg', NULL, NULL, NULL, NULL),
(10, 10, 'tiradito.jpg', NULL, NULL, NULL, NULL),
(11, 11, 'chichadejora.jpg', NULL, NULL, NULL, NULL),
(12, 12, 'cafeconleche.jpg', NULL, NULL, NULL, NULL),
(13, 13, 'motequeso.jpg', NULL, NULL, NULL, NULL),
(14, 14, 'chocotejas.jpg', NULL, NULL, NULL, NULL),
(15, 15, 'tortaalcayota.jpg', NULL, NULL, NULL, NULL),
(16, 16, 'sancochado.jpg', NULL, NULL, NULL, NULL),
(17, 17, 'temuna.jpg', NULL, NULL, NULL, NULL),
(18, 18, 'chocloqueso.jpg', NULL, NULL, NULL, NULL),
(19, 19, 'kekedapera.jpg', NULL, NULL, NULL, NULL),
(20, 20, 'arrozconmariscos.jpg', NULL, NULL, NULL, NULL),
(21, 21, 'choritoschalaca.jpg', NULL, NULL, NULL, NULL),
(22, 22, 'peruanito.jpg', NULL, NULL, NULL, NULL),
(23, 23, 'cecinacerdo.jpg', NULL, NULL, NULL, NULL),
(24, 24, 'heladolucuma.jpg', NULL, NULL, NULL, NULL),
(25, 25, 'arrozconleche.jpg', NULL, NULL, NULL, NULL),
(26, 26, 'sopaseca.jpg', NULL, NULL, NULL, NULL),
(27, 27, 'tequilasour.jpg', NULL, NULL, NULL, NULL),
(28, 28, 'chaufa.jpg', NULL, NULL, NULL, NULL);

INSERT INTO `pago` (`IDPago`, `PagoTotal`, `FechaPago`, `IDPedido`, `IDTarjeta`) VALUES
(1, 98.00, '2025-01-10 12:35:00', 1, NULL),
(2, 48.00, '2025-02-05 19:20:00', 2, NULL),
(3, 76.00, '2025-03-09 14:05:00', 3, NULL),
(4, 55.00, '2025-04-14 20:50:00', 4, NULL),
(5, 108.00, '2025-05-22 14:30:00', 5, NULL),
(6, 78.00, '2025-06-18 18:45:00', 6, NULL),
(7, 40.00, '2025-07-07 13:00:00', 7, NULL),
(8, 31.00, '2025-08-11 19:30:00', 8, NULL),
(9, 92.00, '2025-09-03 13:20:00', 9, NULL),
(10, 46.00, '2025-10-25 17:40:00', 10, NULL),
(11, 61.00, '2025-01-12 12:05:00', 11, NULL),
(12, 86.00, '2025-02-09 18:40:00', 12, NULL),
(13, 41.00, '2025-03-15 14:20:00', 13, NULL),
(14, 95.00, '2025-04-20 20:00:00', 14, NULL),
(15, 68.00, '2025-05-28 13:50:00', 15, NULL),
(16, 43.00, '2025-06-21 17:30:00', 16, NULL),
(17, 40.00, '2025-07-10 12:50:00', 17, NULL),
(18, 56.00, '2025-08-18 19:05:00', 18, NULL),
(19, 64.00, '2025-09-07 13:10:00', 19, NULL),
(20, 46.00, '2025-10-30 17:20:00', 20, NULL),
(21, 61.00, '2025-01-25 13:30:00', 21, NULL),
(22, 86.00, '2025-02-20 19:20:00', 22, NULL),
(23, 41.00, '2025-03-18 13:05:00', 23, NULL),
(24, 95.00, '2025-04-25 18:50:00', 24, NULL),
(25, 68.00, '2025-05-29 13:35:00', 25, NULL),
(26, 43.00, '2025-06-23 18:10:00', 26, NULL),
(27, 40.00, '2025-07-20 12:00:00', 27, NULL),
(28, 56.00, '2025-08-19 19:30:00', 28, NULL),
(29, 64.00, '2025-09-15 14:45:00', 29, NULL),
(30, 46.00, '2025-10-28 18:30:00', 30, NULL),
(31, 90.00, '2025-01-12 12:40:00', 31, NULL),
(32, 53.00, '2025-02-08 18:30:00', 32, NULL),
(33, 108.00, '2025-03-10 13:50:00', 33, NULL),
(34, 45.00, '2025-04-15 20:05:00', 34, NULL),
(35, 76.00, '2025-05-19 14:20:00', 35, NULL),
(36, 33.00, '2025-06-14 18:00:00', 36, NULL),
(37, 86.00, '2025-07-09 13:10:00', 37, NULL),
(38, 52.00, '2025-08-17 19:45:00', 38, NULL),
(39, 71.00, '2025-09-08 13:35:00', 39, NULL),
(40, 41.00, '2025-10-23 18:15:00', 40, NULL),
(41, 84.00, '2025-01-18 13:30:00', 41, NULL),
(42, 55.00, '2025-02-14 19:45:00', 42, NULL),
(43, 108.00, '2025-03-22 13:05:00', 43, NULL),
(44, 50.00, '2025-04-19 21:00:00', 44, NULL),
(45, 66.00, '2025-05-23 13:50:00', 45, NULL),
(46, 35.00, '2025-06-20 18:35:00', 46, NULL),
(47, 88.00, '2025-07-16 13:15:00', 47, NULL),
(48, 50.00, '2025-08-19 19:30:00', 48, NULL),
(49, 85.00, '2025-09-15 13:45:00', 49, NULL),
(50, 46.00, '2025-10-28 18:20:00', 50, NULL),
(51, 84.00, '2025-01-22 13:40:00', 51, NULL),
(52, 69.00, '2025-02-15 19:35:00', 52, NULL),
(53, 99.00, '2025-03-24 12:55:00', 53, NULL),
(54, 41.00, '2025-04-20 20:40:00', 54, NULL),
(55, 70.00, '2025-05-25 13:35:00', 55, NULL),
(56, 41.00, '2025-06-21 18:45:00', 56, NULL),
(57, 95.00, '2025-07-18 13:05:00', 57, NULL),
(58, 50.00, '2025-08-20 19:20:00', 58, NULL),
(59, 76.00, '2025-09-16 13:55:00', 59, NULL),
(60, 48.00, '2025-10-30 18:30:00', 60, NULL),
(61, 92.00, '2025-01-30 12:50:00', 61, NULL),
(62, 58.00, '2025-03-02 19:00:00', 62, NULL),
(63, 48.00, '2025-04-12 14:35:00', 63, NULL),
(64, 71.00, '2025-06-25 20:20:00', 64, NULL),
(65, 51.00, '2025-08-05 13:20:00', 65, NULL),
(66, 92.00, '2025-10-14 19:50:00', 66, NULL),
(67, 78.00, '2025-01-12 12:20:00', 67, NULL),
(68, 53.00, '2025-02-18 13:35:00', 68, NULL),
(69, 37.00, '2025-03-28 19:30:00', 69, NULL),
(70, 94.00, '2025-04-30 14:45:00', 70, NULL),
(71, 92.00, '2025-06-08 20:30:00', 71, NULL),
(72, 45.00, '2025-07-22 18:20:00', 72, NULL),
(73, 78.00, '2025-08-30 14:10:00', 73, NULL),
(74, 48.00, '2025-09-25 19:20:00', 74, NULL),
(75, 86.00, '2025-10-29 13:00:00', 75, NULL),
(76, 47.00, '2025-01-10 11:40:00', 76, NULL),
(77, 52.00, '2025-02-21 18:45:00', 77, NULL),
(78, 71.00, '2025-04-07 13:05:00', 78, NULL),
(79, 46.00, '2025-07-19 19:20:00', 79, NULL),
(80, 80.00, '2025-10-15 13:30:00', 80, NULL),
(81, 78.00, '2025-01-12 12:30:00', 81, NULL),
(82, 62.00, '2025-02-25 19:40:00', 82, NULL),
(83, 46.00, '2025-03-30 13:35:00', 83, NULL),
(84, 71.00, '2025-05-08 20:20:00', 84, NULL),
(85, 100.00, '2025-06-17 19:05:00', 85, NULL),
(86, 37.00, '2025-08-22 12:50:00', 86, NULL),
(87, 88.00, '2025-09-14 20:00:00', 87, NULL),
(88, 53.00, '2025-10-27 14:30:00', 88, NULL),
(89, 78.00, '2025-02-10 12:20:00', 89, NULL),
(90, 48.00, '2025-03-05 13:30:00', 90, NULL),
(91, 49.00, '2025-05-20 19:50:00', 91, NULL),
(92, 43.00, '2025-04-02 13:05:00', 92, NULL),
(93, 70.00, '2025-06-11 18:50:00', 93, NULL),
(94, 31.00, '2025-09-23 13:30:00', 94, NULL),
(95, 25.00, '2025-11-09 22:31:19', 104, 1);

INSERT INTO `pedido` (`IDPedido`, `FechaPedido`, `EstadoPedido`, `MontoFinal`, `IDCliente`, `IDDireccion`) VALUES
(1, '2025-01-10 12:15:00', 'Pagado', 98.00, 1, 1),
(2, '2025-02-05 19:00:00', 'Pagado', 48.00, 1, 1),
(3, '2025-03-09 13:45:00', 'Pagado', 76.00, 1, 1),
(4, '2025-04-14 20:30:00', 'Pagado', 55.00, 1, 1),
(5, '2025-05-22 14:10:00', 'Pagado', 108.00, 1, 1),
(6, '2025-06-18 18:25:00', 'Pagado', 78.00, 1, 1),
(7, '2025-07-07 12:40:00', 'Pagado', 40.00, 1, 1),
(8, '2025-08-11 19:15:00', 'En camino', 0.00, 1, 1),
(9, '2025-09-03 13:00:00', 'Pendiente', 0.00, 1, 1),
(10, '2025-10-25 17:20:00', 'Pendiente', 0.00, 1, 1),
(11, '2025-01-12 11:45:00', 'Pagado', 61.00, 2, 2),
(12, '2025-02-09 18:20:00', 'Pagado', 86.00, 2, 2),
(13, '2025-03-15 14:00:00', 'Pagado', 41.00, 2, 2),
(14, '2025-04-20 19:40:00', 'Pagado', 95.00, 2, 2),
(15, '2025-05-28 13:30:00', 'Pagado', 68.00, 2, 2),
(16, '2025-06-21 17:10:00', 'Pagado', 43.00, 2, 2),
(17, '2025-07-10 12:30:00', 'Pagado', 40.00, 2, 2),
(18, '2025-08-18 18:45:00', 'En camino', 0.00, 2, 2),
(19, '2025-09-07 12:50:00', 'Pendiente', 0.00, 2, 2),
(20, '2025-10-30 17:00:00', 'Pendiente', 0.00, 2, 2),
(21, '2025-01-25 13:10:00', 'Pagado', 61.00, 3, 3),
(22, '2025-02-20 19:00:00', 'Pagado', 86.00, 3, 3),
(23, '2025-03-18 12:45:00', 'Pagado', 41.00, 3, 3),
(24, '2025-04-25 18:30:00', 'Pagado', 95.00, 3, 3),
(25, '2025-05-29 13:15:00', 'Pagado', 68.00, 3, 3),
(26, '2025-06-23 17:50:00', 'Pagado', 43.00, 3, 3),
(27, '2025-07-20 11:40:00', 'Pagado', 40.00, 3, 3),
(28, '2025-08-19 19:10:00', 'En camino', 0.00, 3, 3),
(29, '2025-09-15 14:25:00', 'Pendiente', 0.00, 3, 3),
(30, '2025-10-28 18:10:00', 'Pendiente', 0.00, 3, 3),
(31, '2025-01-12 12:20:00', 'Pagado', 90.00, 4, 4),
(32, '2025-02-08 18:10:00', 'Pagado', 53.00, 4, 4),
(33, '2025-03-10 13:30:00', 'Pagado', 108.00, 4, 4),
(34, '2025-04-15 19:45:00', 'Pagado', 45.00, 4, 4),
(35, '2025-05-19 14:00:00', 'Pagado', 76.00, 4, 4),
(36, '2025-06-14 17:40:00', 'Pagado', 33.00, 4, 4),
(37, '2025-07-09 12:50:00', 'Pagado', 86.00, 4, 4),
(38, '2025-08-17 19:25:00', 'En camino', 0.00, 4, 4),
(39, '2025-09-08 13:15:00', 'Pendiente', 0.00, 4, 4),
(40, '2025-10-23 17:55:00', 'Pendiente', 0.00, 4, 4),
(41, '2025-01-18 13:10:00', 'Pagado', 84.00, 5, 5),
(42, '2025-02-14 19:25:00', 'Pagado', 55.00, 5, 5),
(43, '2025-03-22 12:45:00', 'Pagado', 108.00, 5, 5),
(44, '2025-04-19 20:40:00', 'Pagado', 50.00, 5, 5),
(45, '2025-05-23 13:30:00', 'Pagado', 66.00, 5, 5),
(46, '2025-06-20 18:15:00', 'Pagado', 35.00, 5, 5),
(47, '2025-07-16 12:55:00', 'Pagado', 88.00, 5, 5),
(48, '2025-08-19 19:10:00', 'En camino', 0.00, 5, 5),
(49, '2025-09-15 13:25:00', 'Pendiente', 0.00, 5, 5),
(50, '2025-10-28 18:00:00', 'Pendiente', 0.00, 5, 5),
(51, '2025-01-22 13:20:00', 'Pagado', 84.00, 6, 6),
(52, '2025-02-15 19:15:00', 'Pagado', 69.00, 6, 6),
(53, '2025-03-24 12:35:00', 'Pagado', 99.00, 6, 6),
(54, '2025-04-20 20:20:00', 'Pagado', 41.00, 6, 6),
(55, '2025-05-25 13:15:00', 'Pagado', 70.00, 6, 6),
(56, '2025-06-21 18:25:00', 'Pagado', 41.00, 6, 6),
(57, '2025-07-18 12:45:00', 'Pagado', 95.00, 6, 6),
(58, '2025-08-20 19:00:00', 'En camino', 0.00, 6, 6),
(59, '2025-09-16 13:35:00', 'Pendiente', 0.00, 6, 6),
(60, '2025-10-30 18:10:00', 'Pendiente', 0.00, 6, 6),
(61, '2025-01-30 12:30:00', 'Pagado', 92.00, 7, 7),
(62, '2025-03-02 18:40:00', 'Pagado', 58.00, 7, 7),
(63, '2025-04-12 14:15:00', 'Pagado', 48.00, 7, 7),
(64, '2025-06-25 20:00:00', 'Pagado', 71.00, 7, 7),
(65, '2025-08-05 13:00:00', 'En camino', 0.00, 7, 7),
(66, '2025-10-14 19:30:00', 'Pendiente', 0.00, 7, 7),
(67, '2025-01-12 12:00:00', 'Pagado', 78.00, 8, 8),
(68, '2025-02-18 13:15:00', 'Pagado', 53.00, 8, 8),
(69, '2025-03-28 19:10:00', 'Pagado', 37.00, 8, 8),
(70, '2025-04-30 14:25:00', 'Pagado', 94.00, 8, 8),
(71, '2025-06-08 20:10:00', 'Pagado', 92.00, 8, 8),
(72, '2025-07-22 18:00:00', 'Pagado', 45.00, 8, 8),
(73, '2025-08-30 13:50:00', 'En camino', 0.00, 8, 8),
(74, '2025-09-25 19:00:00', 'Pendiente', 0.00, 8, 8),
(75, '2025-10-29 12:40:00', 'Pendiente', 0.00, 8, 8),
(76, '2025-01-10 11:20:00', 'Pagado', 47.00, 9, 9),
(77, '2025-02-21 18:30:00', 'Pagado', 52.00, 9, 9),
(78, '2025-04-07 12:45:00', 'Pagado', 71.00, 9, 9),
(79, '2025-07-19 19:00:00', 'En camino', 0.00, 9, 9),
(80, '2025-10-15 13:10:00', 'Pendiente', 0.00, 9, 9),
(81, '2025-01-12 12:10:00', 'Pagado', 78.00, 10, 10),
(82, '2025-02-25 19:20:00', 'Pagado', 62.00, 10, 10),
(83, '2025-03-30 13:15:00', 'Pagado', 46.00, 10, 10),
(84, '2025-05-08 20:00:00', 'Pagado', 71.00, 10, 10),
(85, '2025-06-17 18:45:00', 'En camino', 0.00, 10, 10),
(86, '2025-08-22 12:30:00', 'Pendiente', 0.00, 10, 10),
(87, '2025-09-14 19:40:00', 'Pendiente', 0.00, 10, 10),
(88, '2025-10-27 14:10:00', 'Pendiente', 0.00, 10, 10),
(89, '2025-02-10 12:00:00', 'Pagado', 78.00, 11, 11),
(90, '2025-03-05 13:10:00', 'Pagado', 48.00, 12, 12),
(91, '2025-05-20 19:30:00', 'Pendiente', 0.00, 12, 12),
(92, '2025-04-02 12:45:00', 'Pagado', 43.00, 13, 13),
(93, '2025-06-11 18:30:00', 'Pagado', 70.00, 13, 13),
(94, '2025-09-23 13:10:00', 'Pendiente', 0.00, 13, 13),
(95, '2025-11-04 01:46:05', 'Activo', 15.00, 1, NULL),
(96, '2025-11-07 22:40:04', 'Activo', 115.00, 1, NULL),
(97, '2025-11-08 18:18:13', 'Pagado', 180.00, 1, 1),
(98, '2025-11-09 00:41:29', 'Pagado', 342.00, 2, 2),
(99, '2025-11-09 00:54:37', 'Pagado', 8.00, 2, 2),
(100, '2025-11-09 02:19:46', 'Pagado', 60.00, 3, 3),
(101, '2025-11-09 02:41:05', 'Pagado', 18.00, 3, 3),
(102, '2025-11-09 16:50:08', 'Pagado', 90.00, 11, 11),
(103, '2025-11-09 17:51:24', 'Pagado', 37.50, 12, 12),
(104, '2025-11-09 22:31:16', 'Pagado', 25.00, 11, 11);

INSERT INTO `producto` (`IDProducto`, `IDProdHistoria`, `PrecioUnitario`, `Cantidad`, `FechaProducto`, `EnCarta`) VALUES
(1, 1, 45.00, 100, '2025-11-03 00:24:28', 0),
(2, 2, 35.00, 100, '2025-11-04 00:24:28', 0),
(3, 3, 15.00, 100, '2024-11-15 00:24:28', 0),
(4, 4, 18.00, 100, '2024-11-15 00:24:28', 0),
(5, 5, 8.00, 200, '2024-11-15 00:24:28', 0),
(6, 6, 20.00, 100, '2024-11-15 00:24:28', 0),
(7, 7, 10.00, 50, '2024-11-15 00:24:28', 0),
(8, 8, 38.00, 45, '2024-11-15 00:24:28', 0),
(9, 9, 32.00, 50, '2024-11-15 00:24:28', 0),
(10, 10, 35.00, 60, '2024-11-15 00:24:28', 0),
(11, 11, 6.00, 120, '2024-11-15 00:24:28', 0),
(12, 12, 5.50, 150, '2025-11-04 00:24:28', 0),
(13, 13, 12.00, 60, '2024-11-15 00:24:28', 0),
(14, 14, 10.00, 80, '2024-11-15 00:24:28', 0),
(15, 15, 15.00, 50, '2024-11-15 00:24:28', 0),
(16, 16, 27.00, 40, '2024-11-15 00:24:28', 0),
(17, 17, 7.00, 100, '2024-11-15 00:24:28', 0),
(18, 18, 8.00, 150, '2024-11-15 00:24:28', 0),
(19, 19, 16.00, 60, '2024-11-15 00:24:28', 0),
(20, 20, 38.00, 40, '2024-11-15 00:24:28', 0),
(21, 21, 25.00, 50, '2024-11-15 00:24:28', 0),
(22, 22, 5.00, 100, '2024-11-15 00:24:28', 0),
(23, 23, 28.00, 40, '2024-11-15 00:24:28', 0),
(24, 24, 8.00, 80, '2024-11-15 00:24:28', 0),
(25, 25, 12.00, 60, '2024-11-15 00:24:28', 0),
(26, 26, 24.00, 50, '2024-11-15 00:24:28', 0),
(27, 27, 15.00, 80, '2024-11-15 00:24:28', 0),
(28, 28, 15.00, 10, '2024-12-06 16:45:10', 0);

INSERT INTO `productohistoria` (`IDProdHistoria`, `NomProducto`, `Descripcion`, `EstadoProducto`, `IDCategoria`, `EnCarta`) VALUES
(1, 'Lomo Saltado', 'Lomo saltado de carne con papas fritas, cebolla y tomate', 'Activo', 1, 0),
(2, 'Ceviche de Pescado', 'Ceviche de pescado fresco con limón y cebolla', 'Activo', 1, 1),
(3, 'Papa a la Huancaína', 'Rodajas de papa bañadas en salsa de queso y ají amarillo', 'Activo', 3, 0),
(4, 'Causa Limeña', 'Causa de papa con relleno de pollo y palta', 'Activo', 3, 0),
(5, 'Chicha Morada', 'Bebida de maíz morado y frutas', 'Activo', 2, 0),
(6, 'Pisco Sour', 'Cóctel peruano hecho con pisco, limón y clara de huevo', 'Activo', 2, 0),
(7, 'Mazamorra Morada', 'Postre peruano de maíz morado y frutas', 'Activo', 4, 0),
(8, 'Seco de Cabrito', 'Plato típico peruano de carne de cabrito guisado con frejoles y arroz', 'Activo', 1, 0),
(9, 'Ají de Langostinos', 'Ají amarillo con langostinos, acompañado de arroz blanco', 'Activo', 1, 0),
(10, 'Tiradito de Pescado', 'Pescado crudo marinado en limón, ají amarillo y servido con cebollas y cilantro', 'Activo', 1, 0),
(11, 'Chicha de Jora', 'Bebida ancestral peruana fermentada de maíz, con un sabor único y refrescante', 'Activo', 2, 0),
(12, 'Café con Leche', 'Café peruano de altura, acompañado de leche evaporada', 'Activo', 2, 0),
(13, 'Mote de Queso', 'Plato peruano hecho a base de mote de maíz, queso fresco y hierbas aromáticas', 'Activo', 1, 0),
(14, 'Chocotejas', 'Deliciosos bombones rellenos de dulce de leche o manjarblanco, cubiertos de chocolate', 'Activo', 4, 0),
(15, 'Torta de Alcayota', 'Postre peruano elaborado con alcayota, un dulce de calabaza con azúcar y especias', 'Activo', 4, 0),
(16, 'Sancochado', 'Sopa espesa de carne de res, papas, zanahorias, maíz y hierbas', 'Activo', 1, 0),
(17, 'Té de Muña', 'Infusión de muña, una planta aromática andina, conocida por sus propiedades digestivas', 'Activo', 2, 0),
(18, 'Choclo con Queso', 'Mazorca de maíz peruano acompañada de queso fresco', 'Activo', 1, 0),
(19, 'Keke de Pera', 'Delicioso pastel de pera, un postre tradicional peruano', 'Activo', 4, 0),
(20, 'Arroz con Mariscos', 'Plato peruano de arroz con mariscos frescos, sazonado con ají amarillo y hierbas', 'Activo', 1, 0),
(21, 'Choritos a la Chalaca', 'Mejillones frescos acompañados de cebolla, tomate, ají y cilantro', 'Activo', 1, 0),
(22, 'Peruanito', 'Refresco natural de frutas de temporada con un toque de limón y hierba buena', 'Activo', 2, 0),
(23, 'Cecina de Cerdo', 'Plato tradicional amazónico, carne de cerdo ahumada y frita, acompañada de yuca', 'Activo', 1, 0),
(24, 'Helado de Lúcuma', 'Helado artesanal de lúcuma, una fruta autóctona de Perú', 'Activo', 4, 0),
(25, 'Arroz con Leche', 'Postre tradicional de arroz con leche, preparado con canela y clavo de olor', 'Activo', 4, 0),
(26, 'Sopa Seca', 'Plato peruano de fideos al estilo de arroz con carne y verduras', 'Activo', 1, 0),
(27, 'Tequila Sour', 'Cóctel inspirado en el Pisco Sour, pero preparado con tequila mexicano', 'Activo', 2, 0),
(28, 'Chaufa', 'Chaufa nuevo producto', 'Activo', 1, 0);

INSERT INTO `roles` (`IDRol`, `NomRol`, `Descripcion`, `EstadoRoles`) VALUES
(1, 'Administrador', 'Gestiona el restaurante', 'Habilitado'),
(2, 'Mesero', 'Atiende a los clientes en las mesas', 'Habilitado'),
(3, 'Cocinero', 'Encargado de preparar los platos', 'Habilitado'),
(4, 'Delivery', 'Encargado de recoger y entregar pedidos', 'Habilitado');
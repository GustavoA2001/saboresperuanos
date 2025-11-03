
--
-- Base de datos: `restaurantedb_sp`
--

DROP DATABASE IF EXISTS restaurantedb_sp;
CREATE DATABASE restaurantedb_sp CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE restaurantedb_sp;

-- =======================
-- TABLAS BASE
-- =======================

CREATE TABLE roles (
  IDRol INT NOT NULL AUTO_INCREMENT,
  NomRol VARCHAR(50),
  Descripcion VARCHAR(100),
  EstadoRoles VARCHAR(20),
  PRIMARY KEY (IDRol)
);

CREATE TABLE empleado (
  IDEmpleado INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(50),
  ApellidoPaterno VARCHAR(50),
  ApellidoMaterno VARCHAR(50),
  Usuario VARCHAR(50),
  Contrasena VARCHAR(150),
  IDRol INT,
  PRIMARY KEY (IDEmpleado)
);

CREATE TABLE cliente (
  IDCliente INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(50),
  Apellido VARCHAR(50),
  Usuario VARCHAR(50),
  Correo VARCHAR(100),
  Contrasena VARCHAR(100),
  DNI VARCHAR(20),
  PRIMARY KEY (IDCliente)
);

CREATE TABLE distrito (
  IDDistrito INT NOT NULL AUTO_INCREMENT,
  Distrito VARCHAR(100),
  EstadoDistrito VARCHAR(20),
  PRIMARY KEY (IDDistrito)
);

CREATE TABLE direccion (
  IDDireccion INT NOT NULL AUTO_INCREMENT,
  DireccionCompleta VARCHAR(150),
  Referencia VARCHAR(100),
  IDCliente INT,
  IDDistrito INT,
  PRIMARY KEY (IDDireccion)
);

CREATE TABLE categoriaproducto (
  IDCategoria INT NOT NULL AUTO_INCREMENT,
  NomCategoria VARCHAR(50),
  Descripcion VARCHAR(100),
  EstadoCategoria VARCHAR(20),
  PRIMARY KEY (IDCategoria)
);

CREATE TABLE producto (
  IDProducto INT NOT NULL AUTO_INCREMENT,
  PrecioUnitario DECIMAL(10,2),
  Cantidad INT,
  FechaProducto DATETIME,
  IDCategoria INT,
  PRIMARY KEY (IDProducto)
);

CREATE TABLE productohistoria (
  IDProdHistoria INT NOT NULL AUTO_INCREMENT,
  NomProducto VARCHAR(100),
  Descripcion VARCHAR(100),
  EstadoProducto VARCHAR(50),
  IDProducto INT,
  PRIMARY KEY (IDProdHistoria)
);

CREATE TABLE fotoproducto (
  IDFoto INT NOT NULL AUTO_INCREMENT,
  FotoPrincipal VARCHAR(100),
  FotoDos VARCHAR(100),
  FotoTres VARCHAR(100),
  FotoCuatro VARCHAR(100),
  FotoCinco VARCHAR(100),
  IDProducto INT,
  PRIMARY KEY (IDFoto)
);

CREATE TABLE pedido (
  IDPedido INT NOT NULL AUTO_INCREMENT,
  FechaPedido DATETIME,
  EstadoPedido VARCHAR(50),
  IDCliente INT,
  IDDireccion INT,
  PRIMARY KEY (IDPedido)
);

CREATE TABLE carrito (
  IDCarrito INT NOT NULL AUTO_INCREMENT,
  Cantidad INT,
  PrecioProducto DECIMAL(10,2),
  IDProducto INT,
  IDPedido INT,
  PRIMARY KEY (IDCarrito)
);

CREATE TABLE pago (
  IDPago INT NOT NULL AUTO_INCREMENT,
  PagoTotal DECIMAL(10,2),
  FechaPago DATETIME,
  IDPedido INT,
  PRIMARY KEY (IDPago)
);

CREATE TABLE delivery (
  IDDelivery INT NOT NULL AUTO_INCREMENT,
  Estado VARCHAR(50),
  FechaDelivery DATETIME,
  FechaPedido DATETIME,
  IDPedido INT,
  IDEmpleado INT,
  PRIMARY KEY (IDDelivery)
);

CREATE TABLE entrega (
  IDEntrega INT NOT NULL AUTO_INCREMENT,
  HoraSalida DATETIME,
  HoraEntrega DATETIME,
  IDDelivery INT,
  PRIMARY KEY (IDEntrega)
);

CREATE TABLE detallecliente (
  IDDetalleCliente INT NOT NULL AUTO_INCREMENT,
  Telefono VARCHAR(20),
  Foto VARCHAR(100),
  IDCliente INT,
  PRIMARY KEY (IDDetalleCliente)
);

CREATE TABLE detalleempleado (
  IDDetalleEmpleado INT NOT NULL AUTO_INCREMENT,
  Telefono VARCHAR(20),
  Correo VARCHAR(100),
  Foto VARCHAR(100),
  IDEmpleado INT,
  PRIMARY KEY (IDDetalleEmpleado)
);

CREATE TABLE tarjetapago (
  IDTarjeta INT NOT NULL AUTO_INCREMENT,
  NumeroTarjeta VARCHAR(20),
  FechaVencimiento VARCHAR(8),
  CodigoSeguridad VARCHAR(5),
  NombreTitular VARCHAR(100),
  PRIMARY KEY (IDTarjeta)
);

ALTER TABLE empleado
  ADD FOREIGN KEY (IDRol) REFERENCES roles(IDRol);

ALTER TABLE direccion
  ADD FOREIGN KEY (IDCliente) REFERENCES cliente(IDCliente),
  ADD FOREIGN KEY (IDDistrito) REFERENCES distrito(IDDistrito);

ALTER TABLE producto
  ADD FOREIGN KEY (IDCategoria) REFERENCES categoriaproducto(IDCategoria);

ALTER TABLE productohistoria
  ADD FOREIGN KEY (IDProducto) REFERENCES producto(IDProducto);

ALTER TABLE pedido
  ADD FOREIGN KEY (IDCliente) REFERENCES cliente(IDCliente),
  ADD FOREIGN KEY (IDDireccion) REFERENCES direccion(IDDireccion);

ALTER TABLE carrito
  ADD FOREIGN KEY (IDProducto) REFERENCES producto(IDProducto),
  ADD FOREIGN KEY (IDPedido) REFERENCES pedido(IDPedido);

ALTER TABLE pago
  ADD FOREIGN KEY (IDPedido) REFERENCES pedido(IDPedido);

ALTER TABLE delivery
  ADD FOREIGN KEY (IDPedido) REFERENCES pedido(IDPedido),
  ADD FOREIGN KEY (IDEmpleado) REFERENCES empleado(IDEmpleado);

ALTER TABLE entrega
  ADD FOREIGN KEY (IDDelivery) REFERENCES delivery(IDDelivery);

ALTER TABLE detallecliente
  ADD FOREIGN KEY (IDCliente) REFERENCES cliente(IDCliente);

ALTER TABLE detalleempleado
  ADD FOREIGN KEY (IDEmpleado) REFERENCES empleado(IDEmpleado);

ALTER TABLE fotoproducto
  ADD FOREIGN KEY (IDProducto) REFERENCES producto(IDProducto);




INSERT INTO `categoriaproducto` (`IDCategoria`, `NomCategoria`, `Descripcion`, `EstadoCategoria`) VALUES
(1, 'Platillos', 'Principales platos peruanos como Lomo Saltado y Ceviche', 'Habilitado'),
(2, 'Bebidas', 'Bebidas tradicionales como Chicha Morada y Pisco Sour', 'Habilitado'),
(3, 'Entradas', 'Entradas como Papa a la Huancaína y Causa Limeña', 'Habilitado'),
(4, 'Postres', 'Postres típicos peruanos', 'Habilitado');

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

INSERT INTO `roles` (`IDRol`, `NomRol`, `Descripcion`, `EstadoRoles`) VALUES
(1, 'Administrador', 'Gestiona el restaurante', 'Habilitado'),
(2, 'Mesero', 'Atiende a los clientes en las mesas', 'Habilitado'),
(3, 'Cocinero', 'Encargado de preparar los platos', 'Habilitado'),
(4, 'Delivery', 'Encargado de recoger y entregar pedidos', 'Habilitado');

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
(12, 'Ricardo', 'Morales', 'Huaman', 'ricardo', '6720720054e9d24fbf6c20a831ff287e', 4);

INSERT INTO `producto` (`IDProducto`, `PrecioUnitario`, `Cantidad`, `FechaProducto`, `IDCategoria`) VALUES
(1, 45.00, 100, '2024-11-15 00:24:28', 1),
(2, 35.00, 100, '2024-11-15 00:24:28', 1),
(3, 15.00, 100, '2024-11-15 00:24:28', 3),
(4, 18.00, 100, '2024-11-15 00:24:28', 3),
(5, 8.00, 200, '2024-11-15 00:24:28', 2),
(6, 20.00, 100, '2024-11-15 00:24:28', 2),
(7, 10.00, 50, '2024-11-15 00:24:28', 4),
(8, 38.00, 45, '2024-11-15 00:24:28', 1),
(9, 32.00, 50, '2024-11-15 00:24:28', 1),
(10, 35.00, 60, '2024-11-15 00:24:28', 1),
(11, 6.00, 120, '2024-11-15 00:24:28', 2),
(12, 5.50, 150, '2024-11-15 00:24:28', 2),
(13, 12.00, 60, '2024-11-15 00:24:28', 1),
(14, 10.00, 80, '2024-11-15 00:24:28', 4),
(15, 15.00, 50, '2024-11-15 00:24:28', 4),
(16, 27.00, 40, '2024-11-15 00:24:28', 1),
(17, 7.00, 100, '2024-11-15 00:24:28', 2),
(18, 8.00, 150, '2024-11-15 00:24:28', 1),
(19, 16.00, 60, '2024-11-15 00:24:28', 4),
(20, 38.00, 40, '2024-11-15 00:24:28', 1),
(21, 25.00, 50, '2024-11-15 00:24:28', 1),
(22, 5.00, 100, '2024-11-15 00:24:28', 2),
(23, 28.00, 40, '2024-11-15 00:24:28', 1),
(24, 8.00, 80, '2024-11-15 00:24:28', 4),
(25, 12.00, 60, '2024-11-15 00:24:28', 4),
(26, 24.00, 50, '2024-11-15 00:24:28', 1),
(27, 15.00, 80, '2024-11-15 00:24:28', 2),
(28, 15.00, 10, '2024-12-06 16:45:10', 1);

INSERT INTO `fotoproducto` (`IDFoto`, `FotoPrincipal`, `FotoDos`, `FotoTres`, `FotoCuatro`, `FotoCinco`, `IDProducto`) VALUES
(1, 'lomosaltado.jpg', NULL, NULL, NULL, NULL, 1),
(2, 'ceviche.jpg', NULL, NULL, NULL, NULL, 2),
(3, 'papaalahuancaina.jpg', NULL, NULL, NULL, NULL, 3),
(4, 'causalimane%C3%B1a.jpg', NULL, NULL, NULL, NULL, 4),
(5, 'chichamorada.jpg', NULL, NULL, NULL, NULL, 5),
(6, 'piscosour.jpg', NULL, NULL, NULL, NULL, 6),
(7, 'mazamorramorada.jpg', NULL, NULL, NULL, NULL, 7),
(8, 'secocabrito.jpg', NULL, NULL, NULL, NULL, 8),
(9, 'ajidelangostinos.jpg', NULL, NULL, NULL, NULL, 9),
(10, 'tiradito.jpg', NULL, NULL, NULL, NULL, 10),
(11, 'chichadejora.jpg', NULL, NULL, NULL, NULL, 11),
(12, 'cafeconleche.jpg', NULL, NULL, NULL, NULL, 12),
(13, 'motequeso.jpg', NULL, NULL, NULL, NULL, 13),
(14, 'chocotejas.jpg', NULL, NULL, NULL, NULL, 14),
(15, 'tortaalcayota.jpg', NULL, NULL, NULL, NULL, 15),
(16, 'sancochado.jpg', NULL, NULL, NULL, NULL, 16),
(17, 'temuna.jpg', NULL, NULL, NULL, NULL, 17),
(18, 'chocloqueso.jpg', NULL, NULL, NULL, NULL, 18),
(19, 'kekedapera.jpg', NULL, NULL, NULL, NULL, 19),
(20, 'arrozconmariscos.jpg', NULL, NULL, NULL, NULL, 20),
(21, 'choritoschalaca.jpg', NULL, NULL, NULL, NULL, 21),
(22, 'peruanito.jpg', NULL, NULL, NULL, NULL, 22),
(23, 'cecinacerdo.jpg', NULL, NULL, NULL, NULL, 23),
(24, 'heladolucuma.jpg', NULL, NULL, NULL, NULL, 24),
(25, 'arrozconleche.jpg', NULL, NULL, NULL, NULL, 25),
(26, 'sopaseca.jpg', NULL, NULL, NULL, NULL, 26),
(27, 'tequilasour.jpg', NULL, NULL, NULL, NULL, 27),
(28, 'chaufa.jpg', NULL, NULL, NULL, NULL, 28);

INSERT INTO `productohistoria` (`IDProdHistoria`, `NomProducto`, `Descripcion`, `EstadoProducto`, `IDProducto`) VALUES
(1, 'Lomo Saltado', 'Lomo saltado de carne con papas fritas, cebolla y tomate', 'Activo', 1),
(2, 'Ceviche de Pescado', 'Ceviche de pescado fresco con limón y cebolla', 'Activo', 2),
(3, 'Papa a la Huancaína', 'Rodajas de papa bañadas en salsa de queso y ají amarillo', 'Activo', 3),
(4, 'Causa Limeña', 'Causa de papa con relleno de pollo y palta', 'Activo', 4),
(5, 'Chicha Morada', 'Bebida de maíz morado y frutas', 'Activo', 5),
(6, 'Pisco Sour', 'Cóctel peruano hecho con pisco, limón y clara de huevo', 'Activo', 6),
(7, 'Mazamorra Morada', 'Postre peruano de maíz morado y frutas', 'Activo', 7),
(8, 'Seco de Cabrito', 'Plato típico peruano de carne de cabrito guisado con frejoles y arroz', 'Activo', 8),
(9, 'Ají de Langostinos', 'Ají amarillo con langostinos, acompañado de arroz blanco', 'Activo', 9),
(10, 'Tiradito de Pescado', 'Pescado crudo marinado en limón, ají amarillo y servido con cebollas y cilantro', 'Activo', 10),
(11, 'Chicha de Jora', 'Bebida ancestral peruana fermentada de maíz, con un sabor único y refrescante', 'Activo', 11),
(12, 'Café con Leche', 'Café peruano de altura, acompañado de leche evaporada', 'Activo', 12),
(13, 'Mote de Queso', 'Plato peruano hecho a base de mote de maíz, queso fresco y hierbas aromáticas', 'Activo', 13),
(14, 'Chocotejas', 'Deliciosos bombones rellenos de dulce de leche o manjarblanco, cubiertos de chocolate', 'Activo', 14),
(15, 'Torta de Alcayota', 'Postre peruano elaborado con alcayota, un dulce de calabaza con azúcar y especias', 'Activo', 15),
(16, 'Sancochado', 'Sopa espesa de carne de res, papas, zanahorias, maíz y hierbas', 'Activo', 16),
(17, 'Té de Muña', 'Infusión de muña, una planta aromática andina, conocida por sus propiedades digestivas', 'Activo', 17),
(18, 'Choclo con Queso', 'Mazorca de maíz peruano acompañada de queso fresco', 'Activo', 18),
(19, 'Keke de Pera', 'Delicioso pastel de pera, un postre tradicional peruano', 'Activo', 19),
(20, 'Arroz con Mariscos', 'Plato peruano de arroz con mariscos frescos, sazonado con ají amarillo y hierbas', 'Activo', 20),
(21, 'Choritos a la Chalaca', 'Mejillones frescos acompañados de cebolla, tomate, ají y cilantro', 'Activo', 21),
(22, 'Peruanito', 'Refresco natural de frutas de temporada con un toque de limón y hierba buena', 'Activo', 22),
(23, 'Cecina de Cerdo', 'Plato tradicional amazónico, carne de cerdo ahumada y frita, acompañada de yuca', 'Activo', 23),
(24, 'Helado de Lúcuma', 'Helado artesanal de lúcuma, una fruta autóctona de Perú', 'Activo', 24),
(25, 'Arroz con Leche', 'Postre tradicional de arroz con leche, preparado con canela y clavo de olor', 'Activo', 25),
(26, 'Sopa Seca', 'Plato peruano de fideos al estilo de arroz con carne y verduras', 'Activo', 26),
(27, 'Tequila Sour', 'Cóctel inspirado en el Pisco Sour, pero preparado con tequila mexicano', 'Activo', 27),
(28, 'Chaufa', 'Chaufa nuevo producto', 'Activo', 28);

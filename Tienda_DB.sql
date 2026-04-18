drop database if exists tienda_in5cm;
create database tienda_in5cm;
use tienda_in5cm;

create table Clientes(
    id_cliente int not null auto_increment,
    nombre_cliente varchar(50),
    apellido_cliente varchar(50),
    direccion varchar(100),
    estado int,
    primary key (id_cliente)
);

create table Usuarios(
    id_usuario int not null auto_increment,
    username varchar(45),
    password varchar(45),
    email varchar(60),
    rol varchar(45),
    estado int,
    foto_url varchar(500),
    primary key (id_usuario)
);

create table Productos(
    id_producto int not null auto_increment,
    nombre_producto varchar(60),
    precio decimal(10,2),
    stock int,
    estado int,
    primary key (id_producto)
);

create table Ventas(
    id_venta int not null auto_increment,
    fecha_venta date,
    total decimal(10,2),
    estado int,
    id_cliente int,
    id_usuario int,
    primary key (id_venta),
    constraint fk_Ventas_Clientes foreign key (id_cliente) 
        references Clientes(id_cliente),
    constraint fk_Ventas_Usuarios foreign key (id_usuario) 
        references Usuarios(id_usuario)
);

create table DetalleVenta(
    id_detalle_venta int not null auto_increment,
    cantidad int,
    precio_unitario decimal(10,2),
    subtotal decimal(10,2),
    id_producto int,
    id_venta int,
    primary key (id_detalle_venta),
    constraint fk_Detalle_Productos foreign key (id_producto) 
        references Productos(id_producto),
    constraint fk_Detalle_Ventas foreign key (id_venta) 
        references Ventas(id_venta)
);

delimiter $$

create procedure sp_agregarcliente(in p_nombre varchar(50), in p_apellido varchar(50), in p_direccion varchar(100), in p_estado int)
begin
    insert into Clientes(nombre_cliente, apellido_cliente, direccion, estado) 
    values (p_nombre, p_apellido, p_direccion, p_estado);
end$$

create procedure sp_actualizarcliente(in p_id int, in p_nombre varchar(50), in p_apellido varchar(50), in p_direccion varchar(100), in p_estado int)
begin
    update Clientes set nombre_cliente = p_nombre, apellido_cliente = p_apellido, direccion = p_direccion, estado = p_estado 
    where id_cliente = p_id;
end$$

create procedure sp_agregarproducto(in p_nombre varchar(60), in p_precio decimal(10,2), in p_stock int, in p_estado int)
begin
    insert into Productos(nombre_producto, precio, stock, estado) 
    values (p_nombre, p_precio, p_stock, p_estado);
end$$

create procedure sp_actualizarstock(in p_id int, in p_cantidad int)
begin
    update Productos set stock = stock - p_cantidad 
    where id_producto = p_id;
end$$

create procedure sp_agregarusuario(in p_user varchar(45), in p_pass varchar(45), in p_email varchar(60), in p_rol varchar(45), in p_estado int, in p_foto varchar(500))
begin
    insert into Usuarios(username, password, email, rol, estado, foto_url) 
    values (p_user, p_pass, p_email, p_rol, p_estado, p_foto);
end$$

create procedure sp_login(in p_user varchar(45), in p_pass varchar(45))
begin
    select * from Usuarios where username = p_user and password = p_pass and estado = 1;
end$$

create procedure sp_registrarventa(in p_id_cliente int, in p_id_usuario int, in p_total decimal(10,2))
begin
    insert into Ventas(fecha_venta, total, estado, id_cliente, id_usuario) 
    values (curdate(), p_total, 1, p_id_cliente, p_id_usuario);
end$$

create procedure sp_agregardetalleventa(in p_cantidad int, in p_precio decimal(10,2), in p_id_prod int, in p_id_venta int)
begin
    declare v_subtotal decimal(10,2);
    set v_subtotal = p_cantidad * p_precio;
    insert into DetalleVenta(cantidad, precio_unitario, subtotal, id_producto, id_venta) 
    values (p_cantidad, p_precio, v_subtotal, p_id_prod, p_id_venta);
    update Productos set stock = stock - p_cantidad where id_producto = p_id_prod;
end$$

delimiter ;

call sp_agregarcliente('Miguel', 'Santizo', 'Ciudad de Guatemala', 1);
call sp_agregarcliente('Juan', 'Perez', 'Antigua Guatemala', 1);
call sp_agregarcliente('Maria', 'Lopez', 'Quetzaltenango', 1);
call sp_agregarcliente('Carlos', 'Gomez', 'Escuintla', 1);
call sp_agregarcliente('Ana', 'Martinez', 'Chimaltenango', 1);

call sp_agregarusuario('msantizo', 'pass123', 'msantizo@tienda.com', 'ADMIN', 1, 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix');
call sp_agregarusuario('jperez', 'admin2024', 'jperez@tienda.com', 'VENDEDOR', 1, 'https://api.dicebear.com/7.x/avataaars/svg?seed=Aneka');
call sp_agregarusuario('mlopez', 'secure456', 'mlopez@tienda.com', 'VENDEDOR', 1, 'https://api.dicebear.com/7.x/avataaars/svg?seed=Styles');
call sp_agregarusuario('cgomez', 'venta789', 'cgomez@tienda.com', 'VENDEDOR', 1, 'https://api.dicebear.com/7.x/avataaars/svg?seed=Buster');
call sp_agregarusuario('amartinez', 'staff321', 'amartinez@tienda.com', 'VENDEDOR', 1, 'https://api.dicebear.com/7.x/avataaars/svg?seed=Gracie');

call sp_agregarproducto('Laptop Gaming', 12500.00, 20, 1);
call sp_agregarproducto('Mouse Inalambrico', 150.00, 50, 1);
call sp_agregarproducto('Teclado Mecanico', 450.00, 30, 1);
call sp_agregarproducto('Monitor 24 pulgadas', 1800.00, 15, 1);
call sp_agregarproducto('Audifonos Bluetooth', 350.00, 40, 1);

call sp_registrarventa(1, 1, 12650.00);
call sp_registrarventa(2, 2, 450.00);
call sp_registrarventa(3, 3, 3600.00);
call sp_registrarventa(4, 4, 350.00);
call sp_registrarventa(5, 5, 600.00);

call sp_agregardetalleventa(1, 12500.00, 1, 1);
call sp_agregardetalleventa(1, 150.00, 2, 1);
call sp_agregardetalleventa(1, 450.00, 3, 2);
call sp_agregardetalleventa(2, 1800.00, 4, 3);
call sp_agregardetalleventa(1, 350.00, 5, 4);
call sp_agregardetalleventa(4, 150.00, 2, 5);

select * from Usuarios;
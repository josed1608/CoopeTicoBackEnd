INSERT INTO permiso VALUES  (100, 'Pedir viaje');
INSERT INTO permiso VALUES  (101, 'Ver taxis cerca');
INSERT INTO permiso VALUES  (102, 'Editar perfil');
INSERT INTO permiso VALUES  (103, 'Calificar viaje');
INSERT INTO permiso VALUES  (104, 'Ingresar monto');
INSERT INTO permiso VALUES  (200, 'Tomar vijae');
INSERT INTO permiso VALUES  (300, 'Agregar taxi');
INSERT INTO permiso VALUES  (301, 'Agregar taxista');
INSERT INTO permiso VALUES  (302, 'Borrar taxi');
INSERT INTO permiso VALUES  (303, 'Borrar taxista');
INSERT INTO permiso VALUES  (304, 'Editar taxi');
INSERT INTO permiso VALUES  (305, 'Editar taxista');
INSERT INTO permiso VALUES  (306, 'Asignar taxista a taxi');
INSERT INTO permiso VALUES  (307, 'Borrar usuario cliente');
INSERT INTO permiso VALUES  (308, 'Asignar rol a usuario');
INSERT INTO permiso VALUES  (309, 'Ver historial de viajes de un taxista');
INSERT INTO permiso VALUES  (400, 'Agregar administrativo');
INSERT INTO permiso VALUES  (401, 'Borrar administrativo');
INSERT INTO permiso VALUES  (402, 'Editar permiso');
INSERT INTO permiso VALUES  (403, 'Agregar grupo de permisos');


INSERT INTO grupo VALUES ('Cliente');
INSERT INTO grupo VALUES ('Taxista');
INSERT INTO grupo VALUES ('Administrativo');
INSERT INTO grupo VALUES ('Telefonista');
INSERT INTO grupo VALUES ('Gerente');




INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (100, 'Cliente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (101, 'Cliente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (102, 'Cliente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (103, 'Cliente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (104, 'Cliente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (200, 'Taxista');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (300, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (301, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (302, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (303, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (304, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (305, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (306, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (307, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (308, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (309, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (400, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (401, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (402, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (403, 'Gerente');

-- La contraseña es contrasenna para todos

INSERT INTO usuario(pk_correo, id_grupo, apellidos, telefono, contrasena, foto, nombre) VALUES ('cliente@cliente.com', 'Cliente', 'apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Cliente');
INSERT INTO usuario(pk_correo, id_grupo, apellidos, telefono, contrasena, foto, nombre) VALUES ('coopeticotaxi@gmail.com', 'Cliente', 'apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Cliente');
INSERT INTO usuario(pk_correo, id_grupo, apellidos, telefono, contrasena, foto, nombre) VALUES ('taxista@taxista.com', 'Taxista', 'apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Taxista');
INSERT INTO usuario(pk_correo, id_grupo, apellidos, telefono, contrasena, foto, nombre) VALUES ('administrativo@administrativo.com', 'Administrativo', 'apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Administrativo');
INSERT INTO usuario(pk_correo, id_grupo, apellidos, telefono, contrasena, foto, nombre) VALUES ('gerente@gerente.com', 'Gerente', 'apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Gerente');

INSERT INTO taxi(pk_placa,datafono,telefono,clase,tipo,fecha_ven_marchamo, fecha_ven_seguro, fecha_ven_taxista) VALUES ('AAA111', 1, '11111111', 'A', 'normal', now(), now(), now());

INSERT INTO cliente(pk_correo_usuario) VALUES  ('cliente@cliente.com');
INSERT INTO cliente(pk_correo_usuario) VALUES  ('coopeticotaxi@gmail.com');

INSERT INTO taxista(pk_correo_usuario, faltas, estado, hoja_delincuencia, estrellas,  placa_taxi_maneja, placa_taxi_dueno) VALUES ('taxista@taxista.com', '0', 1, 1, 5, 'AAA111', null);

INSERT INTO coopetico(pk_correo_usuario) VALUES ('administrativo@administrativo.com');
INSERT INTO coopetico(pk_correo_usuario) VALUES ('gerente@gerente.com');

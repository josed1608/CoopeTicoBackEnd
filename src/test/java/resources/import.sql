use `coopetico-test`;

INSERT INTO permiso VALUES  (100, 'Pedir viaje');
INSERT INTO permiso VALUES  (101, 'Ver taxis cerca');
INSERT INTO permiso VALUES  (102, 'Editar perfil');
INSERT INTO permiso VALUES  (103, 'Calificar viaje');
INSERT INTO permiso VALUES  (104, 'Ingresar monto');
INSERT INTO permiso VALUES  (200, 'Tomar viaje');
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
INSERT INTO permiso VALUES  (310, 'Consultar taxis');
INSERT INTO permiso VALUES  (311, 'Consultar taxista');
INSERT INTO permiso VALUES  (400, 'Agregar administrativo');
INSERT INTO permiso VALUES  (401, 'Borrar administrativo');
INSERT INTO permiso VALUES  (402, 'Editar permiso');
INSERT INTO permiso VALUES  (403, 'Agregar grupo de permisos');
INSERT INTO permiso VALUES  (404, 'Ver permisos');
INSERT INTO permiso VALUES  (405, 'Asignar permisos');
INSERT INTO permiso VALUES  (500, 'Consultar viajes');

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

INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (300, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (301, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (302, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (303, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (304, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (305, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (306, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (307, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (308, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (309, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (310, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (311, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (400, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (401, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (402, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (403, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (404, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (405, 'Gerente');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (500, 'Gerente');

INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (400, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (401, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (402, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (403, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (404, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (405, 'Administrativo');
INSERT INTO permisos_grupo( pk_id_permisos, pk_id_grupo) VALUES (500, 'Administrativo');



-- La contraseña es contrasenna para todos

INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('cliente@cliente.com', 'Cliente', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Cliente',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('coopeticotaxi@gmail.com', 'Cliente', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Cliente',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('taxista1@taxista.com', 'Taxista', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Taxista',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('taxista2@taxista.com', 'Taxista', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Taxista',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('taxistaNoSuspendido@taxista.com', 'Taxista', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Taxista',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('taxistaSuspendido@taxista.com', 'Taxista', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Taxista',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('administrativo@administrativo.com', 'Administrativo', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Administrativo',TRUE);
INSERT INTO usuario(pk_correo, id_grupo, apellido1, apellido2, telefono, contrasena, foto, nombre, valid) VALUES ('gerente@gerente.com', 'Gerente', 'apellido','apellido', '11111111', '$2a$10$gJ0hUnsEvTp5zyBVo19IHe.GoYKkL3Wy268wGJxG5.k.tUFhSUify', 'foto', 'Gerente',TRUE);


INSERT INTO taxista(pk_correo_usuario, faltas, estado, hoja_delincuencia, estrellas, justificacion, vence_licencia) VALUES ('taxista1@taxista.com', '0', 1, 1, 5, 'justificación', '2019-05-01');
INSERT INTO taxista(pk_correo_usuario, faltas, estado, hoja_delincuencia, estrellas, justificacion, vence_licencia) VALUES ('taxista2@taxista.com', '0', 1, 1, 5, 'justificación', '2019-05-01');
INSERT INTO taxista(pk_correo_usuario, faltas, estado, hoja_delincuencia, estrellas, justificacion, vence_licencia) VALUES ('taxistaNoSuspendido@taxista.com', '0', 1, 1, 5, '', '2019-05-01');
INSERT INTO taxista(pk_correo_usuario, faltas, estado, hoja_delincuencia, estrellas, justificacion, vence_licencia) VALUES ('taxistaSuspendido@taxista.com', '0', 0, 1, 5, 'Cobro de más a un cliente', '2019-05-01');


INSERT INTO taxi(pk_placa,datafono,telefono,clase,tipo,fecha_ven_marchamo, fecha_ven_seguro, fecha_ven_rtv, valido, foto, correo_taxista) VALUES ('AAA111', 1, '11111111', 'A', 'normal', now(), now(), now(),TRUE,NULL,'taxista1@taxista.com');
INSERT INTO taxi(pk_placa,datafono,telefono,clase,tipo,fecha_ven_marchamo, fecha_ven_seguro, fecha_ven_rtv, valido, foto, correo_taxista) VALUES ('BBB111', 1, '11111111', 'A', 'normal', now(), now(), now(),TRUE,NULL,'taxista1@taxista.com');

Insert INTO conduce VALUES('taxista1@taxista.com', 'AAA111');
Insert INTO conduce VALUES('taxista2@taxista.com', 'AAA111');

INSERT INTO cliente(pk_correo_usuario) VALUES  ('cliente@cliente.com');
INSERT INTO cliente(pk_correo_usuario) VALUES  ('coopeticotaxi@gmail.com');


INSERT INTO operador VALUES('administrativo@administrativo.com');
INSERT INTO operador VALUES('gerente@gerente.com');


INSERT INTO token_recuperacion_contrasena(fk_correo_usuario, token, fecha_expiracion) VALUES ('gerente@gerente.com', 'tokenGenerado', '2019-04-04');
INSERT INTO token_recuperacion_contrasena(fk_correo_usuario, token, fecha_expiracion) VALUES ('cliente@cliente.com', 'tokenGenerado2', '2019-07-07');
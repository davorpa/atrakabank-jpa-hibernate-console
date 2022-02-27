USE EjercicioAtracadores;

-- Banco
INSERT INTO BANCO (CODIGO, SEDE, NUM_SUCURSALES) VALUES 
        ("BBVA",        "C/ Castellana Nº 123 - Madrid",  2),
        ("KUTXABANK",   "C/ Erripa Kalea Nº8 - Bilbao",   1),
        ("SABADELL",    "C/ Mataró Nº 1 - Barcelona",     2),
        ("ING",         "Avenida de la Constitución S/N", 0);


-- Sucursal
INSERT INTO SUCURSAL (CODIGO, DIRECCION, CIUDAD, NOMBRE_DIRECTOR, NUM_TRABAJADORES, ID_BANCO) VALUES 
        ("BBVA01",   "C/ Corrida",               "Gijón",  "Martín Martinez",         12, (SELECT ID FROM BANCO WHERE CODIGO = "BBVA")),
        ("BBVA02",   "C/ Uría",                  "Oviedo", "Felipe Sánchez",           4, (SELECT ID FROM BANCO WHERE CODIGO = "BBVA")),
        ("KTXBNK01", "C/ Fruela",                "Oviedo", "Susana Solís Solís",       2, (SELECT ID FROM BANCO WHERE CODIGO = "KUTXABANK")),
        ("SBDLL01",  "C/ Plaza de Requexu",      "Mieres", "Jaime López Pérez",        7, (SELECT ID FROM BANCO WHERE CODIGO = "SABADELL")),
        ("SBDLL02",  "Plaza de la Constitución", "Avilés", "Matilde Gónzalo Álvarez", 12, (SELECT ID FROM BANCO WHERE CODIGO = "SABADELL"));


-- Vigilante
INSERT INTO VIGILANTE (CODIGO, NOMBRE) VALUES
        ("12345678Z",  "Delfín Alejandro Roybal"),
        ("11111111A",  "Lindor Esparza Camacho"),
        ("23452342S",  "Dante Badillo Puente"),
        ("26456534F",  "Mireya Crespo Casas"),
        ("98654334Y",  "Mireya Crespo Casas"),
        ("874543GBD",  "Gema Madrigal Pacheco"),
        ("245625722D", "Jenaro Serrato Chavarría"),
        ("78646563O",  "René Gil Cervántez"),
        ("05326325Q",  "César Crespo Abreu");


-- Contrato
INSERT INTO CONTRATO(IDENTIFICADOR, FECHA_INICIO, FECHA_FIN, PERMISO_ARMAS, ID_SUCURSAL, ID_VIGILANTE) VALUES
    ("CONTR01",  STR_TO_DATE('2013-02-11', '%Y-%m-%d'), STR_TO_DATE('2022-01-30', '%Y-%m-%d'), 1, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "12345678Z")),
    ("CONTR02",  STR_TO_DATE('2019-07-23', '%Y-%m-%d'), STR_TO_DATE('2022-01-30', '%Y-%m-%d'), 0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "11111111A")),
    ("CONTR03",  STR_TO_DATE('2011-01-21', '%Y-%m-%d'), STR_TO_DATE('2019-06-05', '%Y-%m-%d'), 0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM VIGILANTE WHERE CODIGO = "26456534F")),
    ("CONTR04",  STR_TO_DATE('2020-05-21', '%Y-%m-%d'), STR_TO_DATE('2013-02-11', '%Y-%m-%d'), 0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM VIGILANTE WHERE CODIGO = "874543GBD")),
    ("CONTR05",  STR_TO_DATE('2017-10-31', '%Y-%m-%d'), STR_TO_DATE('2019-02-11', '%Y-%m-%d'), 0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "874543GBD")),
    ("CONTR06",  STR_TO_DATE('2022-12-15', '%Y-%m-%d'), STR_TO_DATE('2010-06-18', '%Y-%m-%d'), 1, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "23452342S")),
    ("CONTR07",  STR_TO_DATE('2010-06-18', '%Y-%m-%d'), STR_TO_DATE('2013-02-11', '%Y-%m-%d'), 0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM VIGILANTE WHERE CODIGO = "98654334Y")),
    ("CONTR08",  STR_TO_DATE('2006-01-09', '%Y-%m-%d'), STR_TO_DATE('2013-01-02', '%Y-%m-%d'), 1, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "98654334Y")),
    ("CONTR09",  STR_TO_DATE('2013-01-02', '%Y-%m-%d'), STR_TO_DATE('2013-01-03', '%Y-%m-%d'), 1, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "78646563O")),
    ("CONTR010", STR_TO_DATE('2021-06-07', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "78646563O")),
    ("CONTR011", STR_TO_DATE('2016-06-09', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "245625722D")),
    ("CONTR012", STR_TO_DATE('2016-12-31', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "78646563O")),
    ("CONTR013", STR_TO_DATE('2014-10-31', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "874543GBD")),
    ("CONTR014", STR_TO_DATE('2012-08-09', '%Y-%m-%d'), NULL,                                  1, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "11111111A")),
    ("CONTR015", STR_TO_DATE('2022-01-31', '%Y-%m-%d'), NULL,                                  1, (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM VIGILANTE WHERE CODIGO = "12345678Z")),
    ("CONTR016", STR_TO_DATE('2022-01-31', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "05326325Q")),
    ("CONTR017", STR_TO_DATE('2019-06-05', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM VIGILANTE WHERE CODIGO = "245625722D")),
    ("CONTR018", STR_TO_DATE('2013-07-14', '%Y-%m-%d'), NULL,                                  0, (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM VIGILANTE WHERE CODIGO = "23452342S"));


-- Banda Organizada
INSERT INTO BANDA_ORGANIZADA(CODIGO, NUM_MIEMBROS) VALUES
    ('BO0001', 12),
    ('BO0002', 4),
    ('BO0003', 1),
    ('BO0004', 6),
    ('BO0005', 312);


-- Delincuentes
INSERT INTO DELINCUENTE (IDENTIFICADOR, NOMBRE) VALUES
    ('1345423SG', 'Lutero Avilés Mayonga'),
    ('653423FGW', 'Marie Duran Peralta'),
    ('CBBD23_12', 'Nerina Luevano Angulo'),
    ('3425GF232', 'Fredeswinda Lucio Ramírez'),
    ('6542GSE2S', 'Tupac Roque Ontiveros'),
    ('23T24DSD-1', 'Abdón Tijerina Sánchez'),
    ('KYI5FGEQ', 'Eleazar Castellanos Flórez'),
    ('QEWY4456', 'Adelaide Leiva Valencia'),
    ('RT434563', 'Inaxio Rubio León'),
    ('3245654A', 'Manela Sosa Apodaca'),
    ('453YRW34', 'Claudina Perea Armendáriz'),
    ('23456543I', 'Arquímedes Valle Sedillo'),
    ('987652VSS', 'Yeruti Solano Botello'),
    ('56723234W', 'Enmanuel Rojas Duarte'),
    ('234534S', 'Elis Arreola Sarabia'),
    ('AA5676593', 'Camelia Vanegas Varela'),
    ('323G4534F', 'Yuri Raya Bañuelos'),
    ('2345244S', 'Marón Martínez Pelayo'),
    ('53632455', 'Maricruz Beltrán Alba');

UPDATE DELINCUENTE SET ID_BANDA_ORGANIZADA = (SELECT ID FROM BANDA_ORGANIZADA WHERE CODIGO = "BO0001") WHERE IDENTIFICADOR LIKE '3%';
UPDATE DELINCUENTE SET ID_BANDA_ORGANIZADA = (SELECT ID FROM BANDA_ORGANIZADA WHERE CODIGO = "BO0002") WHERE IDENTIFICADOR LIKE '2%';


-- Juez

INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_010', "José Ramón García López");
INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_011', "Lucero Álvarez Buylla");
INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_012', "Sonsoles Cifuentes Iturria");
INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_013', "Santiago Pérez Peleteiro");


-- Atracos e indicentes

INSERT INTO ATRACO (FECHA, ID_SUCURSAL, ID_DELINCUENTE) VALUES
    (STR_TO_DATE('2021-10-01', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "1345423SG")),
    (STR_TO_DATE('2021-10-02', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "653423FGW")),
    (STR_TO_DATE('2021-10-03', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "CBBD23_12")),
    (STR_TO_DATE('2021-10-03', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "3425GF232")),
    (STR_TO_DATE('2021-10-04', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "6542GSE2S")),
    (STR_TO_DATE('2021-10-05', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "23T24DSD-1")),
    (STR_TO_DATE('2021-10-06', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "KYI5FGEQ")),
    (STR_TO_DATE('2021-10-07', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "QEWY4456")),
    (STR_TO_DATE('2021-10-08', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "RT434563")),
    (STR_TO_DATE('2021-10-08', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "3245654A")),
    (STR_TO_DATE('2021-10-09', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "3245654A")),
    (STR_TO_DATE('2021-10-12', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "453YRW34")),
    (STR_TO_DATE('2021-10-12', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "23456543I")),
    (STR_TO_DATE('2021-10-16', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "987652VSS")),
    (STR_TO_DATE('2021-10-17', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "56723234W")),
    (STR_TO_DATE('2021-10-17', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "234534S")),
    (STR_TO_DATE('2021-10-03', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "AA5676593")),
    (STR_TO_DATE('2021-10-18', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "323G4534F")),
    (STR_TO_DATE('2021-10-20', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "2345244S")),
    (STR_TO_DATE('2021-10-21', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "53632455")),
    (STR_TO_DATE('2021-10-23', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "KTXBNK01"), (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "653423FGW")),
    (STR_TO_DATE('2021-10-23', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "RT434563")),
    (STR_TO_DATE('2021-10-23', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "653423FGW")),
    (STR_TO_DATE('2021-10-23', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA02"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "1345423SG")),
    (STR_TO_DATE('2021-10-25', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL02"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "RT434563")),
    (STR_TO_DATE('2021-10-26', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "1345423SG")),
    (STR_TO_DATE('2021-10-30', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01"),   (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "53632455")),
    (STR_TO_DATE('2021-10-30', '%Y-%m-%d'), (SELECT ID FROM SUCURSAL WHERE CODIGO = "SBDLL01"),  (SELECT ID FROM DELINCUENTE WHERE IDENTIFICADOR = "653423FGW"));


UPDATE ATRACO
    SET TIPO_CONDENA = 'LEVE',
        ID_JUEZ = (SELECT ID FROM JUEZ WHERE IDENTIFICADOR = 'APM_012')
WHERE FECHA <= STR_TO_DATE('2021-10-12', '%Y-%m-%d');


UPDATE ATRACO
    SET TIPO_CONDENA = 'GRAVE',
        ID_JUEZ = (SELECT ID FROM JUEZ WHERE IDENTIFICADOR = 'APM_010')
WHERE ID_SUCURSAL = (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01");


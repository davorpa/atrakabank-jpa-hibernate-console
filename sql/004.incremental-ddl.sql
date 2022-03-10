USE EjercicioAtracadores;


DROP TABLE IF EXISTS `DIRECTIVO`;
DROP TABLE IF EXISTS `ADMINISTRATIVO`;
DROP TABLE IF EXISTS `EMPLEADO`;




-- EMPLEADO

CREATE TABLE IF NOT EXISTS `EMPLEADO` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `DNI`               VARCHAR(12)
                        NOT NULL
                        COMMENT "Número de indentificación nacional. Clave única y natural.",

    `NOMBRE`            VARCHAR(42)
                        NOT NULL
                        COMMENT "Nombre del empleado.",

    `APELLIDO`          VARCHAR(120)
                        NOT NULL
                        COMMENT "Apellidos del empleado.",

    CONSTRAINT `EMPLEADO__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `EMPLEADO__DNI__UK`
        UNIQUE ( `DNI` )

)
ENGINE = INNODB
COMMENT "Los empleados son aquellas personas que trabajan en los bancos.";



-- Relacionar EMPLEADO con BANCO

ALTER TABLE `EMPLEADO` ADD COLUMN
    `BANCO_ID`          BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador del banco al que pertenece el empleado. Véase BANCO.";

ALTER TABLE EMPLEADO
ADD CONSTRAINT `EMPLEADO__BANCO__FK`
    FOREIGN KEY ( `BANCO_ID` ) REFERENCES BANCO ( `ID` );




-- ADMINISTRATIVO

CREATE TABLE IF NOT EXISTS `ADMINISTRATIVO` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Clave primaria del registro relacionada con la correspondiente de EMPLEADO.",

    `NUSS`              VARCHAR(18)
                        NOT NULL
                        COMMENT "Número de la Seguridad Social.",

    `IBAN`              VARCHAR(24)
                        NOT NULL
                        COMMENT "Número internacional de cuenta bancaria.",

    CONSTRAINT `ADMINISTRATIVO__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `ADMINISTRATIVO__NUSS__UK`
        UNIQUE ( `NUSS` ),

    CONSTRAINT `ADMINISTRATIVO__EMPLEADO__FK`
        FOREIGN KEY ( `ID` ) REFERENCES `EMPLEADO` ( `ID` )

)
ENGINE = INNODB
COMMENT "Los administrativos son una especialización de empleado.";



-- DIRECTIVO

CREATE TABLE IF NOT EXISTS `DIRECTIVO` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Clave primaria del registro relacionada con la correspondiente de EMPLEADO.",

    `NIVEL`             TINYINT UNSIGNED
                        NULL
                        DEFAULT 1,

    `CARGO`             VARCHAR(54)
                        NOT NULL
                        COMMENT "Cargo que ocupa: President@, Vicepresident@, Director@, Subdirector@, Gerent@...",

    CONSTRAINT `DIRECTIVO__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `DIRECTIVO__NIVEL_ENUM__CK`
        CHECK ( `NIVEL` IN (1, 2, 3) ),

    CONSTRAINT `DIRECTIVO__EMPLEADO__FK`
        FOREIGN KEY ( `ID` ) REFERENCES `EMPLEADO` ( `ID` )

)
ENGINE = INNODB
COMMENT "Los directivos son una especialización de empleado.";





INSERT INTO EMPLEADO (DNI, NOMBRE, APELLIDO, BANCO_ID) VALUES
    ('78945623R', 'Presidente',    '11', 1),
    ('18946623J', 'Administrativo','21', 2),
    ('98945123X', 'Directivo',     '12', 1),
    ('14845623R', 'Administrativo','21', 2),
    ('01945623B', 'Directivo',     '13', 1),
    ('55945954T', 'Administrativo','12', 1),
    ('45941123P', 'Directivo',     '21', 2),
    ('02542323W', 'Administrativo','31', 3),
    ('00005623G', 'Directivo',     '41', 4),
    ('78763324D', 'Administrativo','13', 1),
    ('78945623F', 'Presidente',    '31', 3),
    ('11111243A', 'Administrativo','41', 4);

INSERT INTO DIRECTIVO (ID, NIVEL, CARGO) VALUES
    (1, 1, 'PRESIDENTE'),
    (3, 2, 'VICEPRESIDENTE'),
    (5, 3, 'DIRECTOR'),
    (7, 1, 'SUBDIRECTOR'),
    (9, 3, 'GERENTE'),
    (11, 1, 'PRESIDENTE');

INSERT INTO ADMINISTRATIVO (ID, NUSS, IBAN) VALUES
    (2, '330254778560', 'ES0640056002645821274500'),
    (4, '331022455885', 'ES0850145112312354875444'),
    (6, '331254457822', 'ES0661422103355000023334'),
    (8, '332445214510', 'ES0661422100025000029954'),
    (10, '332545685421', 'ES0640056002645821274500'),
    (12, '312444545485', 'GB0112054547887002145680');


SELECT *
FROM EMPLEADO e
    LEFT JOIN DIRECTIVO d ON d.id = e.id
    LEFT JOIN ADMINISTRATIVO a ON a.id = e.id;


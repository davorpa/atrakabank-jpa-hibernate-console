USE EjercicioAtracadores;



-- CONTRATO

ALTER TABLE `CONTRATO` ADD COLUMN
    `PERMISO_ARMAS`     BOOLEAN
                        NOT NULL
                        DEFAULT 0
                        COMMENT "Flag que indica si el contrato permite armas. 0 será FALSE y 1 será TRUE."
    AFTER `IDENTIFICADOR`;

ALTER TABLE `CONTRATO` ADD COLUMN
    `FECHA_FIN`         DATE
                        COMMENT "Fecha de fin de la relación contractual."
    AFTER `IDENTIFICADOR`;

ALTER TABLE `CONTRATO` ADD COLUMN
    `FECHA_INICIO`      DATE
                        NOT NULL
                        COMMENT "Fecha de inicio de la relación contractual."
    AFTER `IDENTIFICADOR`;

ALTER TABLE `CONTRATO`
ADD CONSTRAINT `CONTRATO__UK_0`
    UNIQUE ( `ID_SUCURSAL`, `ID_VIGILANTE`, `FECHA_INICIO` );




UPDATE CONTRATO
SET PERMISO_ARMAS = 1
WHERE IDENTIFICADOR IN ( 'CONTR01', 'CONTR06', 'CONTR08', 'CONTR09', 'CONTR014', 'CONTR015' );




-- JUEZ

CREATE TABLE `JUEZ` (
    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.", 
    `IDENTIFICADOR`     VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación del juez. Clave única y natural.", 
    `NOMBRE`            VARCHAR(100)
                        NOT NULL
                        COMMENT "Nombre del juez.",

    CONSTRAINT `JUEZ__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `JUEZ__UK`
        UNIQUE ( `IDENTIFICADOR` )
)
ENGINE = INNODB
COMMENT "Personas encargadas de juzgar, entre otros delitos, los atracos a sucursales cometidos por los delincuentes.";




INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_010', "José Ramón García López");
INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_011', "Lucero Álvarez Buylla");
INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_012', "Sonsoles Cifuentes Iturria");
INSERT INTO JUEZ (IDENTIFICADOR, NOMBRE) VALUES ('APM_013', "Santiago Pérez Peleteiro");




-- ATRACO

ALTER TABLE `ATRACO`
ADD COLUMN
    `TIPO_CONDENA`      VARCHAR(100)
                        NULL
                        COMMENT "Campo de texto, enumerado, con la definición del tipo de condena"
    AFTER `FECHA`;

ALTER TABLE `ATRACO`
ADD CONSTRAINT `ATRACO__TIPO_CONDENA__ENUM__CK`
    CHECK ( `TIPO_CONDENA` IN ('LEVE', 'GRAVE', 'MUY_GRAVE') );

ALTER TABLE `ATRACO`
ADD COLUMN
    `ID_JUEZ`           BIGINT UNSIGNED
                        NULL
                        COMMENT "Identificador del juez que juzga el atraco. Véase JUEZ.";

ALTER TABLE `ATRACO`
ADD CONSTRAINT `ATRACO__ID_JUEZ`
    FOREIGN KEY ( `ID_JUEZ` ) REFERENCES `JUEZ` ( `ID` );




UPDATE ATRACO
    SET TIPO_CONDENA = 'LEVE',
        ID_JUEZ = (SELECT ID FROM JUEZ WHERE IDENTIFICADOR = 'APM_012')
WHERE FECHA <= STR_TO_DATE('2021-10-12', '%Y-%m-%d');


UPDATE ATRACO
    SET TIPO_CONDENA = 'GRAVE',
        ID_JUEZ = (SELECT ID FROM JUEZ WHERE IDENTIFICADOR = 'APM_010')
WHERE ID_SUCURSAL = (SELECT ID FROM SUCURSAL WHERE CODIGO = "BBVA01");



USE EjercicioAtracadores;



DROP TABLE IF EXISTS `ATRACO`;
DROP TABLE IF EXISTS `JUEZ`;
DROP TABLE IF EXISTS `DELINCUENTE`;
DROP TABLE IF EXISTS `BANDA_ORGANIZADA`;
DROP TABLE IF EXISTS `CONTRATO`;
DROP TABLE IF EXISTS `VIGILANTE`;
DROP TABLE IF EXISTS `SUCURSAL`;
DROP TABLE IF EXISTS `BANCO`;




-- BANCO

CREATE TABLE IF NOT EXISTS `BANCO` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `CODIGO`            VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación del banco. Clave única y natural.",

    `SEDE`              VARCHAR(50)
                        NOT NULL
                        COMMENT "Localización donde tiene el banco la sede principal.",

    `NUM_SUCURSALES`    INT UNSIGNED
                        NOT NULL
                        DEFAULT 0
                        COMMENT "Número de sucursales de la entidad bancaria.",

    CONSTRAINT `BANCO__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `BANCO__CODIGO__UK`
        UNIQUE ( `CODIGO` )

)
ENGINE = INNODB
COMMENT "Entidad bancaria.";




-- Sucursal

CREATE TABLE IF NOT EXISTS `SUCURSAL` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `CODIGO`            VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación de la sucursal. Clave única y natural.",

    `CIUDAD`            VARCHAR(50)
                        NOT NULL
                        COMMENT "Campo de texto con la ciudad de la sucursal.",

    `DIRECCION`         VARCHAR(100)
                        NOT NULL
                        COMMENT "Campo de texto con la dirección de la sucursal.",

    `NOMBRE_DIRECTOR`   VARCHAR(100)
                        NOT NULL
                        COMMENT "Nombre del director de la sucursal bancaria.",

    `NUM_TRABAJADORES`  INT UNSIGNED
                        NOT NULL
                        DEFAULT 0
                        COMMENT "Número de personas contratadas en la sucursal bancaria.",

    `ID_BANCO`          BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador del banco al que pertenece. Véase BANCO.",

    CONSTRAINT `SUCURSAL__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `SUCURSAL__CODIGO__UK`
        UNIQUE ( `CODIGO` ),

    CONSTRAINT `SUCURSAL__BANCO__FK`
        FOREIGN KEY ( `ID_BANCO` ) REFERENCES `BANCO` ( `ID` )

)
ENGINE = INNODB
COMMENT "Sucursal que posee cada entidad bancaria.";




-- Vigilante

CREATE TABLE IF NOT EXISTS `VIGILANTE` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `CODIGO`            VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación del vigilante. Clave única y natural.",

    `NOMBRE`            VARCHAR(100)
                        NOT NULL
                        COMMENT "Nombre del vigilante.",

    CONSTRAINT `VIGILANTE__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `VIGILANTE__CODIGO__UK`
        UNIQUE ( `CODIGO` )

)
ENGINE = INNODB
COMMENT "Vigilantes encargados de la seguridad en las sucursales bancarias.";




-- Contrato

CREATE TABLE IF NOT EXISTS `CONTRATO` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `IDENTIFICADOR`     VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación del contrato. Clave única y natural.",

    `FECHA_INICIO`      DATE
                        NOT NULL
                        COMMENT "Fecha de inicio de la relación contractual.",

    `FECHA_FIN`         DATE
                        COMMENT "Fecha de fin de la relación contractual.",

    `PERMISO_ARMAS`     BOOLEAN
                        NOT NULL
                        DEFAULT 0
                        COMMENT "Flag que indica si el contrato permite armas. 0 será FALSE y 1 será TRUE.",

    `ID_SUCURSAL`       BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador de la sucursal. Véase SUCURSAL.",

    `ID_VIGILANTE`      BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador del vigilante. Véase VIGILANTE.",

    CONSTRAINT `CONTRATO__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `CONTRATO__IDENTIFICADOR__UK`
        UNIQUE ( `IDENTIFICADOR` ),

    CONSTRAINT `CONTRATO__UK_0`
        UNIQUE ( `ID_SUCURSAL`, `ID_VIGILANTE`, `FECHA_INICIO` ),

    CONSTRAINT `CONTRATO__SUCURSAL__FK`
        FOREIGN KEY ( `ID_SUCURSAL` ) REFERENCES `SUCURSAL` ( `ID` ),

    CONSTRAINT `CONTRATO__VIGILANTE__FK`
        FOREIGN KEY ( `ID_VIGILANTE` ) REFERENCES `VIGILANTE` ( `ID` )/*,

    CONSTRAINT `CONTRATO_FECHA_FIN_GE_INI__CK`
        CHECK ( `FECHA_FIN` IS NULL OR `FECHA_FIN` >= `FECHA_INICIO` ) */

)
ENGINE = INNODB
COMMENT "Contratos de trabajo expedidos por las sucursales bancarias a los vigilantes.";




-- BandaOrganizada

CREATE TABLE IF NOT EXISTS `BANDA_ORGANIZADA` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `CODIGO`            VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación de la banda. Clave única y natural.",

    `NUM_MIEMBROS`      INT UNSIGNED
                        NOT NULL
                        DEFAULT 0
                        COMMENT "Número de miembros de la banda.",

    CONSTRAINT `BANDA_ORGANIZADA__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `BANDA_ORGANIZADA__CODIGO__UK`
        UNIQUE ( `CODIGO` )

)
ENGINE = INNODB
COMMENT "Grupos organizados a los que, como miembros, pueden afiliarse los delincuentes.";




-- Delincuente

CREATE TABLE IF NOT EXISTS `DELINCUENTE` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `IDENTIFICADOR`     VARCHAR(10)
                        NOT NULL
                        COMMENT "Código de identificación del delincuente. Clave única y natural.",

    `NOMBRE`            VARCHAR(100)
                        NOT NULL
                        COMMENT "Nombre del delincuente.",

    `ID_BANDA_ORGANIZADA`   BIGINT UNSIGNED
                            NULL
                            COMMENT "Identificador de la banda organizada a la que pertenece. Véase BANDA_ORGANIZADA.",

    CONSTRAINT `DELINCUENTE__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `DELINCUENTE__UK`
        UNIQUE ( `IDENTIFICADOR` ),

    CONSTRAINT `DELINCUENTE__BANDA_ORGANIZADA__FK`
        FOREIGN KEY ( `ID_BANDA_ORGANIZADA` ) REFERENCES `BANDA_ORGANIZADA` ( `ID` )

)
ENGINE = INNODB
COMMENT "Personas que realizan los actos delictivos sobre las sucursales bancarias.";




-- Juez

CREATE TABLE IF NOT EXISTS `JUEZ` (
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




-- Atraco

CREATE TABLE IF NOT EXISTS `ATRACO` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `FECHA`             DATE
                        NOT NULL
                        COMMENT "Fecha del incidente.",

    `TIPO_CONDENA`      VARCHAR(100)
                        NULL
                        COMMENT "Campo de texto, enumerado, con la definición del tipo de condena",

    `ID_SUCURSAL`       BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador de la sucursal donde tuvo lugar el incidente. Véase SUCURSAL.",

    `ID_DELINCUENTE`    BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador del delincuente que cometió el atraco. Véase DELINCUENTE.",

    `ID_JUEZ`           BIGINT UNSIGNED
                        NULL
                        COMMENT "Identificador del juez que juzga el atraco. Véase JUEZ.",

    CONSTRAINT `ATRACO__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `ATRACO__SUCURSAL__FK`
        FOREIGN KEY ( `ID_SUCURSAL` ) REFERENCES `SUCURSAL` ( `ID` ),

    CONSTRAINT `ATRACO__DELINCUENTE__FK`
        FOREIGN KEY ( `ID_DELINCUENTE` ) REFERENCES `DELINCUENTE` ( `ID` ),

    CONSTRAINT `ATRACO__JUEZ__FK`
        FOREIGN KEY ( `ID_JUEZ` ) REFERENCES `JUEZ` ( `ID` ),

    CONSTRAINT `ATRACO__TIPO_CONDENA__ENUM__CK`
        CHECK ( `TIPO_CONDENA` IN ('LEVE', 'GRAVE', 'MUY_GRAVE') )

)
ENGINE = INNODB
COMMENT "Las sucursales bancarias sufren incidentes delictivos en forma de atracos. Estos son realizados por los delincuentes en una fecha concreta.";


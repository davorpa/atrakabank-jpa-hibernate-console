USE EjercicioAtracadores;



DROP TABLE IF EXISTS `AFILIACION_BANDA`;




-- Banda Organizada <-M:N-> Delincuente

CREATE TABLE IF NOT EXISTS `AFILIACION_BANDA` (

    `ID`                BIGINT UNSIGNED
                        NOT NULL
                        AUTO_INCREMENT
                        COMMENT "Clave primaria del registro. Valor autogenerado.",

    `ID_BANDA`          BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador de la banda organizada con la que se relaciona. Véase BANDA_ORGANIZADA.",

    `ID_DELINCUENTE`    BIGINT UNSIGNED
                        NOT NULL
                        COMMENT "Identificador del delincuente afiliado como miembro en la banda. Véase DELINCUENTE.",

    CONSTRAINT `AFILIACION_BANDA__PK`
        PRIMARY KEY ( `ID` ),

    CONSTRAINT `AFILIACION_BANDA__UK0`
        UNIQUE ( `ID_BANDA`, `ID_DELINCUENTE` ),

    CONSTRAINT `AFILIACION_BANDA__BANDA__FK`
        FOREIGN KEY ( `ID_BANDA` ) REFERENCES `BANDA_ORGANIZADA` ( `ID` ),

    CONSTRAINT `AFILIACION_BANDA__DELINCUENTE__FK`
        FOREIGN KEY ( `ID_DELINCUENTE` ) REFERENCES `DELINCUENTE` ( `ID` )

)
ENGINE = INNODB
COMMENT "Las bandas organizadas están formadas por uno o más miembros mientras que los delincuentes pueden afiliarse en más de un grupo organizado.";



-- Migrate 1:N data

INSERT INTO AFILIACION_BANDA (ID_BANDA, ID_DELINCUENTE)
    SELECT ID_BANDA_ORGANIZADA, ID
    FROM DELINCUENTE
    WHERE ID_BANDA_ORGANIZADA IS NOT NULL;



-- Remove 1:N relationship

ALTER TABLE `DELINCUENTE` DROP FOREIGN KEY `DELINCUENTE__BANDA_ORGANIZADA__FK`;
ALTER TABLE `DELINCUENTE` DROP COLUMN `ID_BANDA_ORGANIZADA`;


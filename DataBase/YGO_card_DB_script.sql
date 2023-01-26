-- MySQL Script generated by MySQL Workbench
-- Thu Jan 19 14:21:12 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema YGO_card_org
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `YGO_card_org` ;

-- -----------------------------------------------------
-- Schema YGO_card_org
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `YGO_card_org` DEFAULT CHARACTER SET utf8 ;
USE `YGO_card_org` ;

-- -----------------------------------------------------
-- Table `YGO_card_org`.`binder`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `YGO_card_org`.`binder` ;

CREATE TABLE IF NOT EXISTS `YGO_card_org`.`binder` (
  `idBinder` INT NOT NULL AUTO_INCREMENT,
  `color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBinder`, `color`));


-- -----------------------------------------------------
-- Table `YGO_card_org`.`cTypes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `YGO_card_org`.`cTypes` ;

CREATE TABLE IF NOT EXISTS `YGO_card_org`.`cTypes` (
  `idTypes` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(25) NULL,
  PRIMARY KEY (`idTypes`));


-- -----------------------------------------------------
-- Table `YGO_card_org`.`card`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `YGO_card_org`.`card` ;

CREATE TABLE IF NOT EXISTS `YGO_card_org`.`card` (
  `idCard` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `idBinder` INT NULL,
  `idType` INT NULL,
  `count` INT NULL DEFAULT 1,
  PRIMARY KEY (`idCard`, `name`),
  INDEX `binder_idx` (`idBinder` ASC),
  INDEX `type_idx` (`idType` ASC),
  CONSTRAINT `binder`
    FOREIGN KEY (`idBinder`)
    REFERENCES `YGO_card_org`.`binder` (`idBinder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `type`
    FOREIGN KEY (`idType`)
    REFERENCES `YGO_card_org`.`cTypes` (`idTypes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

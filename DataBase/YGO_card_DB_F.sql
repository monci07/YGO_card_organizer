DELIMITER $$
CREATE FUNCTION insert_card(
  n VARCHAR(100), 
  idB int, 
  type  VARCHAR(100),
  c int)
RETURNS INT
BEGIN
  DECLARE id INT;
  SELECT ct.idTypes INTO id FROM ctypes as ct WHERE ct.type = type;
  INSERT INTO card (name,idBinder,idType,count) VALUES (n,idB,id,c);
  SELECT idCard INTO id FROM card WHERE idCard = LAST_INSERT_ID();
  RETURN id;
END$$
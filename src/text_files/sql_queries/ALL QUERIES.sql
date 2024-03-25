#ALL QUERIES








ALTER TABLE product_table
ADD comment_optional VARCHAR(160);

---- USEFUL QUERIES ----


--------------------------------------------------------------------
1. Use Database:

use diet_tracker_schema;

--------------------------------------------------------------------
2. SELECT:

SELECT * FROM product_table;

SELECT * FROM calendar_test;

--------------------------------------------------------------------
3. CREATE TABLE:

CREATE TABLE `calendar_test` (
  `day_date` date NOT NULL,
  `day_name` varchar(45) NOT NULL,
  `product_name` varchar(80) DEFAULT NULL,
  `amount_of_product` float DEFAULT NULL,
  `kcal` float DEFAULT NULL,
  `protein` float DEFAULT NULL,
  `fat` float DEFAULT NULL,
  `carbs` float DEFAULT NULL,
  `time_optional` datetime DEFAULT NULL,
  `comment_optional` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `product_table` (
  `product_name` varchar(80) NOT NULL,
  `product_brand` varchar(80) NOT NULL,
  `product_package_has` float NOT NULL,
  `product_macro_for` float NOT NULL,
  `product_kcal` float NOT NULL,
  `product_protein` float NOT NULL,
  `product_fat` float NOT NULL,
  `product_carbs` float NOT NULL,
  PRIMARY KEY (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--------------------------------------------------------------------
4. INSERT TO TABLE:

INSERT INTO `diet_tracker_schema`.`calendar_test`
(`date`,
`day_name`,
`other_info`)
VALUES
(<{date: }>,
<{day_name: }>,
<{other_info: }>);


INSERT INTO `diet_tracker_schema`.`product_table`
(`product_name`,
`product_brand`,
`product_package_has`,
`product_macro_for`,
`product_kcal`,
`product_protein`,
`product_fat`,
`product_carbs`)
VALUES
(<{product_name: }>,
<{product_brand: }>,
<{product_package_has: }>,
<{product_macro_for: }>,
<{product_kcal: }>,
<{product_protein: }>,
<{product_fat: }>,
<{product_carbs: }>);
--------------------------------------------------------------------

5. Change data in TABLE:

ALTER TABLE calendar_test
ADD time_optional DATETIME;

ALTER TABLE calendar_test
DROP COLUMN other_info;

ALTER TABLE calendar_test
RENAME COLUMN date TO day_date;






Diet Product add new Product table query

create table DietProducts(
Product_Name VARCHAR(25),
Product_Brand VARCHAR(25),
Package_Has FLOAT NOT NULL,
Macro_For FLOAT NOT NULL,
Kcal FLOAT NOT NULL,
Protein FLOAT NOT NULL,
Fat FLOAT NOT NULL,
Carbs FLOAT NOT NULL);

INSERT INTO DietProducts VALUES ('Jogurt - jagodowy', 'Fruvita', 400.0, 100.0, 85.0, 2.7, 2.5, 12.6);




 #CREATE DATABASE javabase DEFAULT CHARACTER SET utf8mb3 COLLATE utf8_unicode_ci;
 #show databases;
CREATE USER 'java'@'localhost' IDENTIFIED BY 'password';
GRANT ALL ON javabase.* TO 'java'@'localhost' IDENTIFIED BY 'password';



 SHOW VARIABLES WHERE Variable_name = 'hostname'



 #CREATE USER 'lukasz'@'localhost' IDENTIFIED BY 'password';
# GRANT ALL ON javabase.* TO 'lukasz'@'localhost' IDENTIFIED BY 'password';


GRANT ALL PRIVILEGES ON javabase.* TO 'l'@'localhost';



jdbc:mysql://localhost:3306/javabase




CREATE NEW CALENDAR DAY TEST TABLE


CREATE TABLE `diet_tracker_schema`.`calendar_test` (
  `id_day` DATE NOT NULL,
  `product` VARCHAR(45) NOT NULL,
  `amount_of_product` FLOAT NOT NULL,
  `kcal` FLOAT NOT NULL,
  `protein` FLOAT NOT NULL,
  `fat` FLOAT NOT NULL,
  `carbs` FLOAT NOT NULL,
  `time_optional` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_day`));





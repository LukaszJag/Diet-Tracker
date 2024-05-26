#PRODUCT TABLE QUERIES

#SELECT * FROM calendar;

#SELECT * FROM calendar_test;

SELECT 
    day_date,
    TRUNCATE(SUM(kcal_consume), 2) AS 'day\'s kcal',
    TRUNCATE(SUM(protein_consume), 2) AS 'proteins',
    TRUSUM(fat_consume) AS 'fats',
    SUM(carbs_consume) AS 'carbs'
FROM
    calendar
WHERE
    day_date = '2024-04-13';




/*
CREATE TABLE `calendar` (
  `day_date` date NOT NULL,
  `day_name` varchar(45) NOT NULL,
  `product_name` varchar(80) DEFAULT NULL,
  `amount_of_product` float DEFAULT NULL,
  `kcal` float DEFAULT NULL,
  `protein` float DEFAULT NULL,
  `fat` float DEFAULT NULL,
  `carbs` float DEFAULT NULL,
  `time_optional` datetime DEFAULT NULL,
  `comment_optional` varchar(80) DEFAULT NULL,
  `kcal_consume` float DEFAULT NULL,
  `carbs_consume` float DEFAULT NULL,
  `fat_consume` float DEFAULT NULL,
  `protein_consume` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
*/

/*
CREATE TABLE `calendar` (
  `day_date` date NOT NULL,
  `day_name` varchar(45) NOT NULL,
  `product_name` varchar(120) DEFAULT NULL,
  `meal_name` varchar(60) DEFAULT NULL,
  `amount_of_product` float DEFAULT NULL,
  `kcal` float DEFAULT NULL,
  `protein` float DEFAULT NULL,
  `fat` float DEFAULT NULL,
  `carbs` float DEFAULT NULL,
  `time_optional` datetime DEFAULT NULL,
  `comment_optional` varchar(80) DEFAULT NULL,
  `kcal_consume` float DEFAULT NULL,
  `carbs_consume` float DEFAULT NULL,
  `fat_consume` float DEFAULT NULL,
  `protein_consume` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
*/



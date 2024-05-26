CREATE TABLE `product_table_test` (
  `product_name` varchar(80) NOT NULL,
  `product_brand` varchar(80) NOT NULL,
  `product_package_has` float NOT NULL,
  `product_macro_for` float NOT NULL,
  `product_kcal` float NOT NULL,
  `product_protein` float NOT NULL,
  `product_fat` float NOT NULL,
  `product_carbs` float NOT NULL,
  `comment_optional` varchar(160),
  PRIMARY KEY (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

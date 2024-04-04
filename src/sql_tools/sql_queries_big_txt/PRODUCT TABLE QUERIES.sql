#PRODUCT TABLE QUERIES

SELECT * FROM product_table;

#SELECT * FROM product_table_test;

#SELECT * FROM product_text_test;


/*
Select * 
from diet_tracker_schema.product_table WHERE product_name='Kisiel owoce leśne';

SELECT * FROM diet_tracker_schema.product_table Where product_name="Burak";
Select * 
from diet_tracker_schema.product_table WHERE product_name='Kisiel owoce leśne';

Select * from diet_tracker_schema.product_table;


CREATE TABLE `product_table_test` (
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

delete from product_table where product_name='Huhu';
*/
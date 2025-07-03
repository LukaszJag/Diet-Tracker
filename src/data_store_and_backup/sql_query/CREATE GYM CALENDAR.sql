CREATE TABLE `gym_calendar` (
  `day_date` date NOT NULL,
  `day_name` varchar(45) NOT NULL,
  `workout_type` varchar(200) NOT NULL,
  `workout_location` varchar(200) NOT NULL,




  `enter_workout_area_full_time_date` float NOT NULL,


  `exit_workout_area_time_date` float NOT NULL,

  `start_workout_full_time_date` float NOT NULL,

`end_workout_full_time_date` float NOT NULL,

`shower_after_workout` float NOT NULL,

`shower_after_workout_start_time` float NOT NULL,
`shower_after_workout_end_time` float NOT NULL,


  `arrive_to_workout_location_type` float NOT NULL,


  `product_protein` float NOT NULL,
  `product_fat` float NOT NULL,
  `product_carbs` float NOT NULL,
  `comment_optional` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




CREATE TABLE `table_for_workout` (
   `ID` int DEFAULT NULL
  `day_date` date NOT NULL,
  `day_name` varchar(45) NOT NULL,
   `type_of_workout` varchar(100) DEFAULT NULL,
   `location_of_workout` varchar(100) DEFAULT NULL,
  `enter_workout_area_full_time_date` date DEFAULT NULL,
  `start_workout_time_full` datetime DEFAULT NULL,
   `end_workout_time_full` datetime DEFAULT NULL
   `duration_workout_time_seconds` int DEFAULT NULL
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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
  `protein_consume` float DEFAULT NULL,
  `meal_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



Must be fileds:
Date
Type of trening
Location

Optionals fields:
ID
Start time
End Time
Duration


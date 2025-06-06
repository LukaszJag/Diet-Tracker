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

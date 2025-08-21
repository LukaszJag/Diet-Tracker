CREATE TABLE `days_statistics_test` (
  `day_date` date DEFAULT NULL,
  `amount_of_points_from_notepad` int DEFAULT NULL,
  `amount_of_filled_points_from_notepad` int DEFAULT NULL,
  `kcal_consume` float DEFAULT NULL,
  `protein_consume` float DEFAULT NULL,
  `fat_consume` float DEFAULT NULL,
  `carbs_consume` float DEFAULT NULL,
  `day_name` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

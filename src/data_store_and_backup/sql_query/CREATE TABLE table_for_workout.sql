CREATE TABLE `table_for_workout` (
   `ID` int DEFAULT NULL,
   `day_date` date NOT NULL,
   `day_name` varchar(45) NOT NULL,
   `type_of_workout` varchar(100) DEFAULT NULL,
   `location_of_workout` varchar(100) DEFAULT NULL,
   `enter_workout_area_full_time_date` date DEFAULT NULL,
   `start_workout_time_full` datetime DEFAULT NULL,
   `end_workout_time_full` datetime DEFAULT NULL,
   `duration_workout_time_seconds` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

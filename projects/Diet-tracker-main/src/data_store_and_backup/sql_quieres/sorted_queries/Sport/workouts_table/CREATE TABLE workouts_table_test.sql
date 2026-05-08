CREATE TABLE `workouts_table_test` (
`workout_id` varchar(40) NOT NULL,
`workout_name` varchar(200) NOT NULL,
`day_date`  date NOT NULL,
`day_name` varchar(45) NOT NULL,
`exercise_id` varchar(45) NOT NULL,
`exercise_name` varchar(200) NOT NULL,
`set_number_of_sets_in_exercise` int DEFAULT NULL,
`amount_of_sets` int DEFAULT NULL,
`amount` varchar(200) DEFAULT NULL,
`comment` varchar(200) DEFAULT NULL,
 PRIMARY KEY (`workout_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `trainings_table_test` (
`training_id` varchar(40) NOT NULL,
`workout_id` varchar(40) NOT NULL,
`workout_name` varchar(200) NOT NULL,
`day_date`  date NOT NULL,
`day_name` varchar(45) NOT NULL,
`location` varchar(200) NOT NULL,
`training_start` datetime DEFAULT NULL,
`training_end` datetime DEFAULT NULL,
`duration` time DEFAULT NULL,
`comment` varchar(700) NOT NULL,
 PRIMARY KEY (`training_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


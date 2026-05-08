CREATE TABLE `exercises_library_table_test` (
`exercise_id`  varchar(40) NOT NULL,
`exercise_name` varchar(200) NOT NULL,
`exercise_target` varchar(200) DEFAULT NULL,
`instructions` varchar(700) DEFAULT NULL,
`body_part` varchar(300) DEFAULT NULL,
`added_weight` varchar(200) DEFAULT NULL,
`weight_type` varchar(200) DEFAULT NULL,
`comment` varchar(700) DEFAULT NULL,
`type_of_measure` varchar(200) DEFAULT NULL,
 PRIMARY KEY (`exercise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

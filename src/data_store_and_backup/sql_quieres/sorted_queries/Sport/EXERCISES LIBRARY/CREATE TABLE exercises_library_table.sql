CREATE TABLE `exercises_library_table` (
   `ID` int DEFAULT NULL,
   `exercise_name` date NOT NULL,
   `body_part` varchar(100) NOT NULL,
   `category` varchar(150) DEFAULT NULL,
   `weight_unit` int DEFAULT NULL,

   `best_rep` date DEFAULT NULL,
   `best_set` datetime DEFAULT NULL,
   `best_duration` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `gym_workout` (

#workout_ID
`workout_ID` int NOT NULL,

# date
`day_date` date NOT NULL,
`day_name` varchar(45) NOT NULL,

#location_of_workout
`location_of_workout` varchar(100) DEFAULT NULL,

# general_type_of_workout --> gym
`general_type_of_workout` varchar(100) DEFAULT NULL,

# examples: push, pull, legs, (chest, shoulders, triceps), cardio and more
`type_of_workout` varchar(100) DEFAULT NULL,


`name_of_exercise` varchar(80) DEFAULT NULL,
`number_of_set` int DEFAULT NULL,
`weight_in_kilograms` int DEFAULT NULL,

# barbell, dumbbells, dumbbell, dumbbells left hand, dumbbells right hand, sack, machine
`type_of_weight` int DEFAULT NULL,

`reps` int DEFAULT NULL,

`duration_of_break_seconds` int DEFAULT NULL,
`duration_in_seconds` int DEFAULT NULL,
`distance_in_meters` int DEFAULT NULL,
`amount_of_sets` int DEFAULT NULL,
`comment` varchar(200) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
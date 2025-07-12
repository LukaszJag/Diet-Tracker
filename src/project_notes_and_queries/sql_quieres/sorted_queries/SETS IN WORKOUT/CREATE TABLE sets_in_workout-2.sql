CREATE TABLE `sets_in_workout` (
   `workout_ID` int DEFAULT NULL,

   # date
   `day_date` date NOT NULL,
   `day_name` varchar(45) NOT NULL,

`location_of_workout` varchar(100) DEFAULT NULL,

# cardio, reps only, machine, weight lift, 
#? --> weight isolated exercises, weight compound exercises 
`general_type_of_workout` varchar(100) DEFAULT NULL,

#Type of trening:
#static strength/walking/running/swimming/cardio/lift weight/calenistics/street workout/stretch/riding bike/
#mobility/
#(boxing/kickboxing/MMA/BJJ/krav maga)(what type of [many varations])/
#(basketball/volleyball/football)(what type of [many varations])


`type_of_workout` varchar(100) DEFAULT NULL,

`number_of_set` int DEFAULT NULL,
`amount_of_sets` int DEFAULT NULL,
`reps` int DEFAULT NULL,
`duration_in_seconds` int DEFAULT NULL,
`distance_in_meters` int DEFAULT NULL,
`weight_in_kilograms` int DEFAULT NULL,
# barbell, dumbbells, dumbbell, dumbbells left hand, dumbbells right hand, sack, machine
`type_of_weight` int DEFAULT NULL,
# body weight
`type_of_non_weight` int DEFAULT NULL,


`duration_workout_time_seconds` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

package tools.sport_tools;

public class Exercise {

    /*
    day_date date
    day_name varchar(45)
    location_of_workout varchar(100)
    general_type_of_workout varchar(100)
    accurate_type_of_workout varchar(100)
    number_of_set int
    amount_of_sets int
    reps int
    duration_in_seconds int
    distance_in_meters int
    weight_in_kilograms int
    type_of_weight int
    type_of_non_weight in
        */
    int exerciseID;
    String exerciseName;
    String typeOfWorkout;// cardio, weight lifting, stretching
    String generalSportType; // gym, play on field

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }
}

package tools.sport_tools.id_manager;

public class IDManager {

    public void createNewID(String typeOfID) {
        String endOfIndex = getTypeOfID(typeOfID);
        String pathToIDFile = getPathForTypeOfTraining(endOfIndex);

        int lastIDNumber = -1;

    }

    public String getIDWithoutIDType(String path){

    }

    public String getTypeOfID(String inputRaw) {
        String endOfIndex = "-1";
        inputRaw = inputRaw.toLowerCase();
        if (inputRaw.equalsIgnoreCase("exercises")) {
            endOfIndex = "e";
        }
        if (inputRaw.equalsIgnoreCase("workouts")) {
            endOfIndex = "w";
        }
        if (inputRaw.equalsIgnoreCase("trainings")) {
            endOfIndex = "e";
        }

        try {
            if (endOfIndex.equals("-1")) {
                throw new Exception("Cannot read type of ID");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return endOfIndex;
    }


    public String getPathForTypeOfTraining(String typeOfID) {
        String pathToIdFiles = "-1";
        if (typeOfID.equals("e")) {
            pathToIdFiles = "src/data_store_and_backup/text_files/sport/ID/id_exercises.txt";
        }

        if (typeOfID.equals("w")) {
            pathToIdFiles = "src/data_store_and_backup/text_files/sport/ID/id_workouts.txt";
        }

        if (typeOfID.equals("t")) {
            pathToIdFiles = "src/data_store_and_backup/text_files/sport/ID/id_trainings.txt";
        }

        return pathToIdFiles;
    }
}

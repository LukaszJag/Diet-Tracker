package tools.sql_tools.general;

import tools.debug_tools.Debug;

import java.util.*;

public class RowInTable {

    private static HashMap<String, String> fields = new HashMap<>();

    //<editor-fold desc="Constructors">
    public RowInTable() {

    }

    public RowInTable(HashMap<String, String> fields) {
        setFields(fields);
    }
    //</editor-fold>

    public void putKeyAndValueToRow(String key, String value) {
        fields.put(key, value);
    }

    //<editor-fold desc="Print methods">
    public void printAlLValuesAndKey() {
        for (String key : fields.keySet()) {
            Debug.printKeyAndValue(key, fields.get(key), 6);
        }
    }

    public static void printAlLValuesAndKey(RowInTable rowInTable) {
        for (String i : rowInTable.getFields().keySet()) {
            System.out.println("key:<" + i + "> value: <" + fields.get(i) + ">");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Get methods">
    public int getSizeOfRow() {
        return fields.size();
    }

    public String getValue(String key) {

        //System.out.println(fields.get(key));
        return fields.get(key);


    }

    public String getKey(Object value) {
        if (fields == null) return null;

        String firstKey = null;
        int counter = 0;
        for (Map.Entry<String, ?> entry : fields.entrySet()) {
            String value2 = (String) entry.getValue();
            counter++;

            if (value2.equals(value)) {
                String key = entry.getKey();
                if (firstKey == null) {
                    firstKey = entry.getKey();
                } else {
                    System.out.println(
                            "Warning: multiple keys map to the given value; "
                                    + "returning first: " + firstKey
                    );
                    return firstKey;
                }
            }
        }
        return firstKey;
    }

    public String getKeyMINE(String value) {
        if (fields == null) return null;

        String firstKey = null;
        int counter = 0;
        for (Map.Entry<String, ?> entry : fields.entrySet()) {
            String value2 = (String) entry.getValue();
            counter++;

            if (value2.equals(value)) {
                String key = entry.getKey();
                if (firstKey == null) {
                    firstKey = entry.getKey();
                } else {
                    System.out.println(
                            "Warning: multiple keys map to the given value; "
                                    + "returning first: " + firstKey
                    );
                    return firstKey;
                }
            }
        }
        return firstKey;
    }

    public ArrayList<String> getAllKeys() {
        Set setOfKeys = fields.keySet();
        return new ArrayList<String>(setOfKeys);
    }

    public ArrayList<String> getAllValues() {
        return new ArrayList<>(fields.values());
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public HashMap<String, String> getFields() {
        return fields;
    }

    public static void setFields(HashMap<String, String> fields) {
        RowInTable.fields = fields;
    }
    //</editor-fold>
}

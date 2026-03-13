package tools.sql_tools.general;

import java.util.*;

public class RowInTable {

    private static HashMap<String, String> fields;

    public RowInTable() {
        this.fields = new HashMap<>();
    }

    public RowInTable(HashMap<String, String> fields) {
        setFields(fields);
    }

    public void putKeyAndValueToRow(String key, String value) {
        fields.put(key, value);
    }

    public int getSizeOfRow() {
        return fields.size();
    }

    public static String getValue(String key) {
        try {
            fields.get(key);
        } catch (Exception e) {

        }
        return "-1";
    }

    public String getKey(Object value) {
        if (fields == null) return null;

        String firstKey = null;
        int counter = 0;
        for (Map.Entry<String, ?> entry : fields.entrySet()) {
            String value2 = (String) entry.getValue();
            System.out.println(counter + ": " + value2);
            counter++;

            if (value2.equals(value)) {
                String key = entry.getKey();
                System.out.println(counter + ": " + key);
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
            System.out.println(counter + ": " + value2);
            counter++;

            if (value2.equals(value)) {
                String key = entry.getKey();
                System.out.println(counter + ": " + key);
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

    public void printAlLValuesAndKey(){
        for (String i : fields.keySet()) {
            System.out.println("key: " + i + " value: " + fields.get(i));
        }
    }
    //<editor-fold desc="Getters and Setters">
    public static HashMap<String, String> getFields() {
        return fields;
    }

    public static void setFields(HashMap<String, String> fields) {
        RowInTable.fields = fields;
    }
    //</editor-fold>
}

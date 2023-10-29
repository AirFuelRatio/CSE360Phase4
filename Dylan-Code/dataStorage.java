package application;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    private static List<String> database = new ArrayList<>();

    public static void storeData(String data) {
        database.add(data);
        // In a real-world scenario, this is where you'd integrate with a database
        // and possibly use encryption methods.
    }

    public static List<String> getStoredData() {
        return database;
    }
}

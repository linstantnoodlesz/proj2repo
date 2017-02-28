package db;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class Database {

    //The tables of the database kept in a list
    private Map<String, Table> tables;

    /**
     * Public constructor for database
     */
    public Database() {
        // YOUR CODE HERE
        tables = new TreeMap<>();
    }

    public String transact(String query) {

        return "YOUR CODE HERE";
    }

    public String createTable(String tableName) {
        //TODO: Implement multiple arguments when taking in list of strings to create table

        return "";
    }
}

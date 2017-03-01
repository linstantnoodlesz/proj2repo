package db;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class Database {

    //The tables of the database kept in a map, where each table's associated key
    //is its name as a string
    private Map<String, Table> tables;

    /**
     * Public constructor for database
     */
    public Database() {
        // YOUR CODE HERE
        tables = new TreeMap<>();
    }

    public String transact(String query) {
        Parse.eval(query);
        return "YOUR CODE HERE";
    }

    /**
     * Creates a new table; takes in column names and types, creates columns,
     * then puts the table into the tables map.
     */
    private String createTable(String tableName, String[] columnInfo) {
        //TODO: Implement multiple arguments when taking in list of strings to create table
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < columnInfo.length; i = i + 2) {
            String columnName = columnInfo[i];
            String columnType = columnInfo[i + 1];
            Column c = new Column(columnName, columnType);
            columns.add(c);
        }
        Table newTable = new Table(columns);
        tables.put(tableName, newTable);
        return "";
    }

    /**
     * Drops the table by removing its key from tables
     */
    private String dropTable(String tableName) {
        if (tables.containsKey(tableName)) {
            tables.remove(tableName);
            return "";
        }
        return "No table " + tableName + " found in database.";
    }

    /**
     * Stores the contents of the table into the file <table name>.tbl
     */
    private String store(String tableName) {
        //TODO: Learn how to write content into a file
        return "";
    }

    /**
     * Loads the contents of <table name>.tbl file into memory as a table with
     * name <table name>
     */
    private String load(String tableName) {
        //TODO: Read input from file
        return "";
    }

    /* Prints the table */
    private String printTable(String tableName) {
        //Gets the table from database and obtains the list of columns
        Table t = tables.get(tableName);
        t.printTable();
        return "";
    }
}

package db;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
        //The list returned from evaluating query using Parser class
        List<String> stringArgs = Parser.eval(query);

        String command = stringArgs.get(0);
        //Gets table name
        String tableName = stringArgs.get(1);
        //Gets length of the list string args from query
        int stringArgsLength = stringArgs.size();

        switch (command) {
            case "create new table":
                List<String> colInfo = new LinkedList<>();
                for (int i = 2; i < stringArgsLength; i++) {
                    String[] colSplit = stringArgs.get(i).split(" ");
                    for (String col : colSplit) {
                        colInfo.add(col);
                    }
                }
                return createTable(tableName, colInfo);

            case "insert":
                List<String> rowVals = new LinkedList<>();
                for (int i = 2; i < stringArgsLength; i++) {
                    rowVals.add(stringArgs.get(i));
                }
                return insertRow(tableName, rowVals);

            case "print":
                //Prints the table
                return printTable(tableName);
        }


        return "Malformed Command";
    }

    /**
     * Creates a new table; takes in column names and types, creates columns,
     * then puts the table into the tables map.
     */
    private String createTable(String tableName, List<String> columnInfo) {
        if (columnInfo.size() == 0) {
            return "Error: table must have at least one column";
        }
        if (tables.containsKey(tableName)) {
            return "Error: Already a table " + tableName + " in database";
        }
        List<Column> columns = new ArrayList<>();
        int colInfoLength = columnInfo.size();
        for (int i = 0; i < colInfoLength; i = i + 2) {
            String columnName = columnInfo.get(i);
            String columnType = columnInfo.get(i+1);
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
        Table t = tables.get(tableName);
        try {
            PrintWriter writer = new PrintWriter(tableName + ".tbl", "UTF-8");
            writer.print(t.printTable());
            writer.close();
        } catch (IOException e){
            System.out.println("No such table written");
        }
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
        System.out.println(t.printTable());
        return "";
    }

    /* Inserts a row into the table */
    private String insertRow(String tableName, List<String> rowInfo) {
        Table table = tables.get(tableName);
        return table.addRow(rowInfo);
    }
}

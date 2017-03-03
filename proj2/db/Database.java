package db;

import java.io.*;
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

        try {
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
                    return createNewTable(tableName, colInfo);

                case "create selected table":
                    //Gets table names
                    String tableNameString = stringArgs.get(3).replace(" ", "");
                    String[] tableNames = tableNameString.split(",");

                    Table selectedTable = tables.get(tableNames[0]);
                    for (int i = 1; i < tableNames.length; i++) {
                        selectedTable = selectedTable.join(tables.get(tableNames[i]));
                    }
                    //Select all from the tables
                    if (stringArgs.get(2).equals("*")) {
                        tables.put(tableName, selectedTable);
                    }
                    return "";

                case "insert":
                    List<String> rowVals = new LinkedList<>();
                    for (int i = 2; i < stringArgsLength; i++) {
                        rowVals.add(stringArgs.get(i));
                    }
                    return insertRow(tableName, rowVals);

                case "store":
                    return store(tableName);

                case "load":
                    return load(tableName);

                case "print":
                    //Prints the table
                    return printTable(tableName);

                case "drop":
                    return dropTable(tableName);
            }
        } catch (NullPointerException e) {
            return "";
        }

        return "Malformed Command";
    }

    /**
     * Creates a new table; takes in column names and types, creates columns,
     * then puts the table into the tables map. The list columnInfo must be
     * ordered such that even-indexed items are the column names, and odd-indexed
     * items are the corresponding column types.
     */
    private String createNewTable(String tableName, List<String> columnInfo) {
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
     * Creates a selected table, takes in column names from given tables, joins the tables,
     * //TODO: Implement column and conditional expressions
     */
    private String simpleCreateSelectedTable(List<String> cols, List<String> tableNames) {
        try {
            Table table1 = tables.get(tableNames.get(0));
            Table table2 = tables.get(tableNames.get(1));
            Table joinedTable = table1.join(table2);
            return joinedTable.print();
        }
        catch (NullPointerException e) {
            return "Malformed select query: Cannot find one of the tables given";
        }
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
        try {
            PrintWriter writer = new PrintWriter(tableName + ".tbl", "UTF-8");
            String printedTable = printTable(tableName);
            for (String s : printedTable.split("\n")) {
                writer.println(s);
            }
            writer.close();
        } catch (IOException e){
            System.out.println("No such table written");
        }
        return "";
    }

    /**
     * Loads the contents of <table name>.tbl file into memory as a table with
     * name <table name>. Takes string of
     */
    private String load(String tableName) {
        //TODO: Read input from file
        try (BufferedReader br = new BufferedReader(new FileReader(tableName + ".tbl"))) {

            //This string contains column information
            String line = br.readLine();
            String[] colInfoString = line.split(",|\\ ");
            List<String> colInfoList = new ArrayList<>();
            for (String colInfo : colInfoString) {
                colInfoList.add(colInfo);
            }
            //Creates a new table in database with table name and column info given in colInfoList
            createNewTable(tableName, colInfoList);

            //Read next lines, which are the rows
            line = br.readLine();
            while (line != null) {
                String[] rowInfoString = line.split(",");
                List<String> rowInfo = new ArrayList<>();
                for (String item : rowInfoString) {
                    rowInfo.add(item);
                }
                insertRow(tableName, rowInfo);
                line = br.readLine();
            }
            return "";
        } catch (FileNotFoundException e) {
            return "ERROR: .";
        } catch (IOException e) {
            return "Error: IO Exception!";
        }
    }

    /* Prints the table */
    private String printTable(String tableName) {
        if (!tables.containsKey(tableName)) {
            return "No table " + tableName + " in database.";
        }
        //Gets the table from database and obtains the list of columns
        Table t = tables.get(tableName);
        return t.print();
    }

    /* Inserts a row into the table */
    private String insertRow(String tableName, List<String> rowInfo) {
        if (tables.containsKey(tableName)) {
            Table table = tables.get(tableName);
            return table.addRow(rowInfo);
        }
        return "No table " + tableName + " in database.";
    }
}

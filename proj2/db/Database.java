package db;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database {

    private class Parse {
        // Various common constructs, simplifies parsing.
        private static final String REST = "\\s*(.*)\\s*",
                COMMA = "\\s*,\\s*",
                AND = "\\s+and\\s+";

        // Stage 1 syntax, contains the command name.
        private final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
                LOAD_CMD = Pattern.compile("load " + REST),
                STORE_CMD = Pattern.compile("store " + REST),
                DROP_CMD = Pattern.compile("drop table " + REST),
                INSERT_CMD = Pattern.compile("insert into " + REST),
                PRINT_CMD = Pattern.compile("print " + REST),
                SELECT_CMD = Pattern.compile("select " + REST);

        // Stage 2 syntax, contains the clauses of commands.
        private final Pattern CREATE_NEW = Pattern.compile("(\\S+)\\s+\\((\\S+\\s+\\S+\\s*" +
                "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
                SELECT_CLS = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                        "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                        "([\\w\\s+\\-*/'<>=!]+?(?:\\s+and\\s+" +
                        "[\\w\\s+\\-*/'<>=!]+?)*))?"),
                CREATE_SEL = Pattern.compile("(\\S+)\\s+as select\\s+" +
                        SELECT_CLS.pattern()),
                INSERT_CLS = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                        "\\s*(?:,\\s*.+?\\s*)*)");

        void eval(String query) {
            Matcher m;
            if ((m = CREATE_CMD.matcher(query)).matches()) {
                createTable(m.group(1));
            } else if ((m = LOAD_CMD.matcher(query)).matches()) {
                load(m.group(1));
            } else if ((m = STORE_CMD.matcher(query)).matches()) {
                store(m.group(1));
            } else if ((m = DROP_CMD.matcher(query)).matches()) {
                dropTable(m.group(1));
            } else if ((m = INSERT_CMD.matcher(query)).matches()) {
                insertRow(m.group(1));
            } else if ((m = PRINT_CMD.matcher(query)).matches()) {
                printTable(m.group(1));
            } else if ((m = SELECT_CMD.matcher(query)).matches()) {
                select(m.group(1));
            } else {
                System.err.printf("Malformed query: %s\n", query);
            }
        }
    }

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
        Parse parser = new Parse();
        parser.eval(query);
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
    private String insertRow(String tableName, )
}

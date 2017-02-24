package db;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Joseph on 2/21/2017.
 */
public class Table {

    // Table is implemented as a list of columns
    private List<Column> table;

    // The names of the columns of the table cached in a list; used for joins operation
    private List<String> columnNames;

    // Column class; has attributes column name, next column in table, and
    // a list that holds that items in the columns, ie. the rows.
    private class Column {

        private String columnName;

        private List items;

        private Column(String columnName, String type) {
            this.columnName = columnName;
            if (type == "int") {
                items = new ArrayList<Integer>();
            } else if (type == "float") {
                items = new ArrayList<Float>();
            } else {
                items = new ArrayList<String>();
            }
        }

    }

    /**
     *  Public constructor method for table to create new table; sets table name,
     *  creates table, and stores a list of its column names
     */
    public Table(List<Column> columns) {
        table = columns;
        columnNames = new ArrayList<>();

        // Adds the names of every column to the list of column names
        for (Column c : table) {
            columnNames.add(c.columnName);
        }
    }

    /**
     * Adds row to table
     */
    public void addRow(List<Object> row) throws RuntimeException {
        if (row.size() != table.size()) {
            throw new RuntimeException("Row size and column size must match");
        }

        for (Column c : table) {
            if 
        }
    }

    /**
     * Performs join operation of two tables. If they share columns, append the rows
     * of the table where the values of the shared columns are the same. If the shared
     * columns don't share any values, then return an empty table. If they don't share
     * any columns, then return the Cartesian product of the tables.
     */
    public static Table joinTables(String name, Table table1, Table table2) {
        List<Column> sharedColumns = new ArrayList<>();
        List<String> table2Names = table2.columnNames;

        // Iterates through column names of table 1 and checks for shared columns in
        // table 2.
        for (String c : table1.columnNames) {
            if (table2Names.contains(c)) {
                sharedColumns.add(table1.getColumn(c));
            }
        }

        // If tables 1 & 2 don't share any columns, return the cartesian product
        if (sharedColumns.isEmpty()) {
            return cartesianProduct(table1, table2);
        }

        //Adds remaining un
        Table joinedTable = new Table(sharedColumns);

    }

    /**
     * Returns the cartesian product of tables 1 & 2. That is, for each row in table 1,
     * we append to it a row in table 2 for each row in table 2.
     */
    public static Table cartesianProduct(Table table1, Table table2) {
        List<Column> tableColumns = new ArrayList<>(table1.table);
        for (Column c : table2.table) {
            tableColumns.add(c);
        }
        Table joinedTable = new Table(tableColumns);

    }

    /**
     * Returns the column in the table with the given name
     */
    public Column getColumn(String name) {
        for (Column column : table) {
            if (column.columnName == name) {
                return column;
            }
        }
        return null;
    }
}
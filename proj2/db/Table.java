package db;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Joseph on 2/21/2017.
 */
public class Table {

    // Table is implemented as a list of columns
    private List<Column> table;

    // The names of the columns of the table cached in a list; used for joins operation
    private Set<String> columnNames;

    /**
     *  Public constructor method for table to create new table; sets table name,
     *  creates table, and stores a list of its column names
     */
    public Table(List<Column> columns) {
        table = columns;
        columnNames = new HashSet<>();

        // Adds the names of every column to the list of column names
        for (Column c : table) {
            columnNames.add(c.columnName);
        }
    }

    /**
     * Adds row to table; implements insert command
     */
    void addRow(List<Object> row) throws RuntimeException {
        if (row.size() != table.size()) {
            throw new RuntimeException("Row size and column size must match");
        }

        //TODO: Finish
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
    Table joinTables(String name, Table otherTable) {
        List<Column> sharedColumns = new ArrayList<>();
        Set<String> table2Names = otherTable.columnNames;

        // Iterates through column names of table 1 and checks for shared columns in
        // table 2.
        for (String c : columnNames) {
            if (table2Names.contains(c)) {
                sharedColumns.add(getColumn(c));
            }
        }

        // If tables 1 & 2 don't share any columns, return the cartesian product
        if (sharedColumns.isEmpty()) {
            return cartesianProduct(otherTable);
        }

        //Adds remaining un
        Table joinedTable = new Table(sharedColumns);
        //TODO: Finish join operation for tables with shared columns
    }

    /**
     * Returns the cartesian product of tables 1 & 2. That is, for each row in table 1,
     * we append to it a row in table 2 for each row in table 2.
     */
    Table cartesianProduct(Table otherTable) {
        List<Column> tableColumns = new ArrayList<>(table);
        for (Column c : otherTable.table) {
            tableColumns.add(c);
        }
        Table joinedTable = new Table(tableColumns);
        //TODO: Finish!
    }

    /**
     * Returns the column in the table with the given name
     */
    Column getColumn(String name) {
        for (Column column : table) {
            if (column.columnName == name) {
                return column;
            }
        }
        return null;
    }
}
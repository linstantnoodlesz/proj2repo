package db;

import javax.management.AttributeList;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Created by Joseph on 2/21/2017.
 */
public class Table {

    // Table is implemented as a list of columns
    private List<Column> table;

    // The names of the columns of the table cached in a list; used for joins operation
    private List<String> columnNames;

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
     * Adds row to table; implements insert command
     */
    void addRow(List<Object> row) throws RuntimeException {
        //Gets the number of columns
        int cols = table.size();
        if (row.size() != cols) {
            throw new RuntimeException("Row size and column size must match");
        }
        //Checks if the types match up; gets class of the item in rows, then converts
        //it into a string in lowercase
        for (int i = 0; i < cols; i++) {
            Class rowClass = row.get(i).getClass();
            String rowType = rowClass.getTypeName().toLowerCase();
            //Converts double to float
            if (rowType == "double") {
                rowType = "float";
            }
            Column c = table.get(i);
            //If the types do not match, throw exception
            if (!rowType.equals(c.columnType)) {
                throw new RuntimeException("Type of " + row.get(i) + ", " + rowType +
                        ", does not match " + c.columnType);
            }
        }
        //If no errors in types, then iterate through columns to add the row
        for (int j = 0; j < cols; j++) {
            Column column = table.get(j);
            column.items.add(row.get(j));
        }
    }

    /**
     * Performs join operation of two tables. If they share columns, append the rows
     * of the table where the values of the shared columns are the same. If the shared
     * columns don't share any values, then return an empty table. If they don't share
     * any columns, then return the Cartesian product of the tables.
     */
    Table join(Table otherTable) {
        List<String> joinedColumnNames = new ArrayList<>();
        //Holds temporary sets of table1 and table 2 names for utility
        List<String> table1Names = new ArrayList<>(columnNames);
        List<String> table2Names = new ArrayList<>(otherTable.columnNames);

        //Iterates through column names in this table and compares to column names in
        //other table; if shared, then add to joined column names and remove from sets
        for (Column col : table) {
            String colName = col.columnName;
            if (table2Names.contains(colName)) {
                joinedColumnNames.add(colName);
                table1Names.remove(colName);
                table2Names.remove(colName);
            }
        }

        //Adds the rest of the columns to joined column names
        for (String table1name : table1Names) {
            joinedColumnNames.add(table1name);
        }
        for (String table2name : table2Names) {
            joinedColumnNames.add(table2name);
        }
        

    }

    /**
     * Returns the cartesian product of tables 1 & 2. That is, for each row in table 1,
     * we append to it a row in table 2 for each row in table 2.
     */
    private Table cartesianProduct(Table otherTable) {
        List<Column> tableColumns = new ArrayList<>(table);
        for (Column c : otherTable.table) {
            tableColumns.add(c);
        }
        Table joinedTable = new Table(tableColumns);

        //Gets length of column items in this table and in other table
        Column firstCol1 = table.get(0);
        int thisLength = firstCol1.items.size();

        Column firstCol2 = otherTable.table.get(0);
        int otherLength = firstCol2.items.size();

        //Iterate through the rows in this table and through each row in other table,
        //adding each item to the row to be added to joined table
        for (int i = 0; i < thisLength; i++) {

            List<Object> rowToAdd = new ArrayList<>();

            //Iterate through the columns in this table to add items to rowToAdd
            for (Column c : table) {
                List colItemsThis = c.items;
                rowToAdd.add(colItemsThis.get(i));
            }

            for (int j = 0; j < otherLength; j++) {

                //Iterate through the columns in other table to add items to rowToAdd
                for (Column c : otherTable.table) {
                    List colItemsOther = c.items;
                    rowToAdd.add(colItemsOther.get(j));
                }

                //Iteration for one row complete; adds row to joined table
                joinedTable.addRow(rowToAdd);
            }
        }
        return joinedTable;
    }

    /**
     * Returns the column in the table with the given name
     */
    private Column getColumn(String name) {
        for (Column column : table) {
            if (column.columnName == name) {
                return column;
            }
        }
        return null;
    }

    /*Prints the table */
    void printTable() {
        //Prints the first row which is the list of column names and types
        for (int i = 0; i < table.size(); i++) {
            if (i > 0) {
                System.out.print(",");
            }
            Column column = table.get(i);
            System.out.print(column.columnName + " " + column.columnType);
        }
        System.out.println("");
        //Iterates through the rows, defined by the size of the list of items of each column
        int rows = table.get(0).items.size();

        for (int j = 0; j < rows; j++) {

            //For each row, iterate through the columns
            for (int i = 0; i < table.size(); i++) {
                if (i > 0) {
                    System.out.print(",");
                }
                //Gets the items list of the column
                Column c = table.get(i);
                List items = c.items;
                if (c.columnType == "string") {
                    System.out.println("\'" + items.get(j) + "\'");
                } else {
                    System.out.print(items.get(j));
                }
            }
            System.out.println("");
        }
    }
}
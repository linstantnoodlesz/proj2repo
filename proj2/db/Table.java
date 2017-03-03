package db;

import javax.management.AttributeList;
import java.util.*;
import java.lang.Object;

/**
 * Created by Joseph on 2/21/2017.
 */
public class Table {

    // Table is implemented as a list of columns
    private List<Column> table;

    // The names of the columns of the table cached in a set; used for joins operation
    private Set<String> columnNames;

    /**
     *  Public constructor method for table to create new table; sets table name,
     *  creates table, and stores a list of its column names
     */
    Table(List<Column> columns) {
        table = columns;
        columnNames = new LinkedHashSet<>();

        // Adds the names of every column to the list of column names
        for (Column c : table) {
            columnNames.add(c.columnName);
        }
    }

    /**
     * Adds row to table; implements insert command
     */
    String addRow(List<String> row) throws RuntimeException {
        //Gets the number of columns
        int numCols = table.size();
        //Throws exception if row and column sizes differ
        if (row.size() != numCols) {
            return "Row size and column size must match";
        }
        //Checks if the types match up; gets class of the item in rows, then converts
        //it into a string in lowercase
        for (int i = 0; i < numCols; i++) {
            Column column = table.get(i);
            //Gets the class of the items in the column
            String colType = column.columnType;
            if (colType.equals("int")) {
                try {
                    int item = Integer.parseInt(row.get(i));
                    column.items.add(item);
                } catch (NumberFormatException e) {

                }
            } else if (colType.equals("float")) {
                try {
                    float item = Float.parseFloat(row.get(i));
                    column.items.add(item);
                } catch (NumberFormatException e) {

                }
            } else if (colType.equals("string")) {
                String item = row.get(i);
                column.items.add(item);
            } else {
                return "Types do not match";
            }
        }
        return "";
    }

    /**
     * Performs join operation of two tables. If they share columns, append the rows
     * of the table where the values of the shared columns are the same. If the shared
     * columns don't share any values, then return an empty table. If they don't share
     * any columns, then return the Cartesian product of the tables.
     */
    Table join(Table otherTable) {
        Set<String> joinedColumnNames = new LinkedHashSet<>();
        //Holds sets of table1, table 2, & shared column names for utility
        Set<String> table1Names = new LinkedHashSet<>(columnNames);
        Set<String> table2Names = new LinkedHashSet<>(otherTable.columnNames);
        Set<String> sharedColumnNames = new LinkedHashSet<>();

        //Iterates through column names in this table and compares to column names in
        //other table; if shared, then add to joined column names and shared column names
        //and remove from sets
        for (Column col : table) {
            String colName = col.columnName;
            if (table2Names.contains(colName)) {
                joinedColumnNames.add(colName);
                sharedColumnNames.add(colName);
                table1Names.remove(colName);
                table2Names.remove(colName);
            }
        }

        //If the tables don't share any columns, return their cartesian product
        if (sharedColumnNames.isEmpty()) {
            return cartesianProduct(otherTable);
        }

        //Adds the rest of the columns to joined column names
        for (String table1name : table1Names) {
            joinedColumnNames.add(table1name);
        }
        for (String table2name : table2Names) {
            joinedColumnNames.add(table2name);
        }

        //Iterates through joined table names to create the joined table
        List<Column> joinedTableColumns = new ArrayList<>();
        Column colToAdd;
        for (String name : joinedColumnNames) {
            if (columnNames.contains(name)) {
                colToAdd = new Column(name, getColumn(name).columnType);
            } else {
                colToAdd = new Column(name, otherTable.getColumn(name).columnType);
            }
            joinedTableColumns.add(colToAdd);
        }

        //Joined table to return
        Table joinedTable = new Table(joinedTableColumns);

        //Gets length of items in this table
        int thisLength = table.get(0).items.size();
        //Gets length of items in other table
        int otherLength = otherTable.table.get(0).items.size();

        //Iterates through the rows in this table to check for same values for shared columns
        List<String> potentialRow;
        for (int i = 0; i < thisLength; i++) {

            potentialRow = new ArrayList<>();

            //Iterates through the rows in the other table
            for (int j = 0; j < otherLength; j++) {
                //Whether to add a row or not
                boolean addRow = true;
                //Iterates through shared column names in this table
                for (String s : sharedColumnNames) {
                    Column sharedColThis = getColumn(s);
                    Object item1 = sharedColThis.items.get(i);
                    Column sharedColOther = otherTable.getColumn(s);
                    Object item2 = sharedColOther.items.get(j);
                    //If value is unequal, break from for-loop and move on to next row
                    if (!item1.equals(item2)) {
                        addRow = false;
                        break;
                    }
                }
                if (addRow) {
                    for (String nameToAdd : joinedColumnNames) {
                        if (table1Names.contains(nameToAdd)) {
                            //Adds the i-th item in this table's column
                            potentialRow.add(toString(getColumn(nameToAdd).items.get(i)));
                        } else {
                            //Adds the j-th item in the other table's column
                            potentialRow.add(toString(otherTable.getColumn(nameToAdd).items.get(j)));
                        }
                    }
                    joinedTable.addRow(potentialRow);
                }
            }
        }
        return joinedTable;
    }

    private String toString(Object o) {
        if (o instanceof Integer) {
            return Integer.toString((Integer) o);
        } else if (o instanceof Float) {
            return Float.toString((Float) o);
        }
        return (String) o;
    }


    /**
     * Returns the cartesian product of tables 1 & 2. That is, for each row in table 1,
     * we append to it a row in table 2 for each row in table 2.
     */
    private Table cartesianProduct(Table otherTable) {
        List<Column> tableColumns = new ArrayList<>(table);
        List<Column> otherTableColumns = new ArrayList<>(otherTable.table);
        for (Column c : otherTableColumns) {
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

            //Iterate through the columns in this table to add items to rowToAdd
            List colItemsThis;
            List rowToAdd = new ArrayList<>();
            for (Column c1 : table) {
                colItemsThis = c1.items;
                rowToAdd.add(toString(colItemsThis.get(i)));
            }
            //Keeps track of the items of the row of this table
            List rowThisPart = new ArrayList<>(rowToAdd);
            for (int j = 0; j < otherLength; j++) {
                //Iterate through the columns in other table to add items to rowToAdd
                List colItemsOther;
                for (Column c2 : otherTable.table) {
                    colItemsOther = c2.items;
                    rowToAdd.add(toString(colItemsOther.get(j)));
                }

                //Iteration for one row complete; adds row to joined table
                joinedTable.addRow(rowToAdd);
                rowToAdd = rowThisPart;
            }
        }

        return joinedTable;
    }

    /**
     * Returns the column in the table with the given name
     */
    private Column getColumn(String name) {
        for (Column column : table) {
            if (column.columnName.equals(name)) {
                return column;
            }
        }
        throw new RuntimeException("No column " + name + " found.");
    }

    /*Prints the table */
    String print() {
        String tablePrinted = "";
        //Prints the first row which is the list of column names and types
        for (int i = 0; i < table.size(); i++) {
            if (i > 0) {
                tablePrinted += ",";
            }
            Column column = table.get(i);
            tablePrinted += column.columnName + " " + column.columnType;
        }
        tablePrinted += "\r\n";
        //Iterates through the rows, defined by the size of the list of items of each column
        int rows = table.get(0).items.size();
        for (int j = 0; j < rows; j++) {

            //For each row, iterate through the columns
            for (int i = 0; i < table.size(); i++) {
                if (i > 0) {
                    tablePrinted += ",";
                }
                //Gets the items list of the column
                Column c = table.get(i);
                List items = c.items;
                if (c.columnType.equals("string")) {
                    tablePrinted += "\'" + items.get(j) + "\'";
                } else {
                    tablePrinted += items.get(j);
                }
            }
            if (j != rows - 1) {
                tablePrinted += "\r\n";
            }
        }
        return tablePrinted;
    }
}
package db;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Joseph on 2/27/2017.
 */

// Column class; has attributes column name and a list that holds the
// items in the columns, ie. the rows.
class Column {

    String columnName;

    String columnType;

    //A list of each item under the column; implemented with array list for efficiency
    //in select operations, since array lists have constant run-time get method
    List items;

    Column(String columnName, String type) {
        this.columnName = columnName;
        if (type.equals("int")) {
            items = new ArrayList<Integer>();
        } else if (type.equals("float")) {
            items = new ArrayList<Float>();
        } else if (type.equals("string")) {
            items = new ArrayList<String>();
        } else {
            throw new RuntimeException(type + " is an invalid type.");
        }
        columnType = type.toLowerCase();
    }
}

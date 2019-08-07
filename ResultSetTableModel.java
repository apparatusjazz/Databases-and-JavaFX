package sample;

import java.sql.*;
import javax.swing.table.AbstractTableModel;

// ResultSet rows and columns are counted from 1 and JTable
// rows and columns are counted from 0. When processing
// ResultSet rows or columns for use in a JTable, it is
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class ResultSetTableModel extends AbstractTableModel {
    private final Connection connection;
    private final PreparedStatement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;

    // keep track of database connection status
    private boolean connectedToDatabase = false;

    // constructor initializes resultSet and obtains its metadata object;
    // determines number of rows
    public ResultSetTableModel(String url, String username, String query) throws SQLException {
        // connect to database
        connection = DriverManager.getConnection(url);

        // create Statement to query database
        statement = connection.prepareStatement(query);

        // update database connection status
        connectedToDatabase = true;

        // set query and execute it
        statement.execute();
    }

    // get class that represents column type
    public Class getColumnClass(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine Java class of column
        try {
            String className = metaData.getColumnClassName(column + 1);

            // return Class object that represents className
            return Class.forName(className);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        return Object.class; // if problems occur above, assume type Object
    }

    // get number of columns in ResultSet
    public int getColumnCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine number of columns
        try {
            return metaData.getColumnCount();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return 0; // if problems occur above, return 0 for number of columns
    }

    // get name of a particular column in ResultSet
    public String getColumnName(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // determine column name
        try {
            return metaData.getColumnName(column + 1);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return ""; // if problems, return empty string for column name
    }

    // return number of rows in ResultSet
    public int getRowCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        return numberOfRows;
    }

    // obtain value in particular row and column
    public Object getValueAt(int row, int column)
            throws IllegalStateException {

        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return ""; // if problems, return empty string object
    }

    // set new database query string
    public void setQuery(String query)
            throws SQLException, IllegalStateException {

        // ensure database connection is available
        if (!connectedToDatabase) {
            throw new IllegalStateException("Not Connected to Database");
        }

        // specify query and execute it
        resultSet = statement.executeQuery(query);

        // obtain metadata for ResultSet
        metaData = resultSet.getMetaData();

        // determine number of rows in ResultSet
        resultSet.last(); // move to last row
        numberOfRows = resultSet.getRow(); // get row number


        fireTableStructureChanged(); // notify JTable that model has changed
    }

    // close Statement and Connection
    public void disconnectFromDatabase() {
        if (connectedToDatabase) {
            // close Statement and Connection
            try {
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            finally { // update database connection status
                connectedToDatabase = false;
            }
        }
    }
}



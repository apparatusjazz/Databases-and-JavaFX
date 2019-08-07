package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DataAccess {

    public static final String DATABASE_URL =
            "jdbc:sqlite:C:\\Users\\Maui Arcuri\\Desktop\\Projects\\DatabaseJava\\employees.db";

    public static Employee searchEmployee (String name) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employees WHERE firstName="+name;

        //Execute SELECT statement
        try {

            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement statement = connection.prepareStatement(selectStmt);

            //Get ResultSet
            ResultSet rsEmp = statement.executeQuery();

            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            Employee employee = getEmployeeFromResultSet(rsEmp);

            return employee;

        } catch (SQLException e) {
            System.out.println("While searching an employee with " + name + " id, an error occurred: " + e);
            //Return exception
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Employee Object's attributes and return employee object.
    private static Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException
    {
        Employee emp = null;
        if (rs.next()) {
            emp = new Employee();
            emp.setSocialSecurityNumber(rs.getString("socialSecurityNumber"));
            emp.setFirstName(rs.getString("firstName"));
            emp.setLastName(rs.getString("lastName"));
            emp.setBirthday(rs.getString("birthday"));
            emp.setEmployeeType(rs.getString("employeeType"));
            emp.setDepartmentName(rs.getString("departmentName"));
        }
        return emp;
    }

    public static ObservableList<Employee> searchEmployees () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM employees";

        //Execute SELECT statement
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement statement = connection.prepareStatement(selectStmt);

            //Get ResultSet
            ResultSet rsEmps = statement.executeQuery();
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Employee> empList = getEmployeeList(rsEmps);

            //Return employee object
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }

    private static ObservableList<Employee> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Employee> empList = FXCollections.observableArrayList();

        while (rs.next()) {
            Employee emp = new Employee();
            emp = new Employee();
            emp.setSocialSecurityNumber(rs.getString("socialSecurityNumber"));
            emp.setFirstName(rs.getString("firstName"));
            emp.setLastName(rs.getString("lastName"));
            emp.setBirthday(rs.getString("birthday"));
            emp.setEmployeeType(rs.getString("employeeType"));
            emp.setDepartmentName(rs.getString("departmentName"));
            //Add employee to the ObservableList
            empList.add(emp);
        }
        //return empList (ObservableList of Employees)
        return empList;
    }

    public static void deleteEmpWithName (String name) throws SQLException, ClassNotFoundException {
        //Declare a DELETE statement
        String stmt =
                "BEGIN\n" +
                        "   DELETE FROM employees\n" +
                        "         WHERE firstName ="+ name +";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement statement = connection.prepareStatement(stmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

    public static void addEmployee(String[] info) {
        String insert =
                "INSERT INTO employees (socialSecurityNumber, firstName, lastName, birthday, " +
                        "employeeType, departmentName)" +
                        "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement line = connection.prepareStatement(insert);

            for(int i = 0; i < info.length; i++) {
                line.setString(i + 1, info[i]);
            }
            line.executeUpdate();
        }

        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

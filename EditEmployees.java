package sample;

import java.sql.*;

public class EditEmployees {

    public static final String DATABASE_URL =
            "jdbc:sqlite:C:\\Users\\Maui Arcuri\\Desktop\\Projects\\DatabaseJava\\employees.db";

    public static void removeEmployee(String tableName, String identifier, String value) {

        final String REMOVE = "DELETE FROM employees WHERE firstName = 'Maui'";
        final String[] values = {tableName, identifier, value};
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement line = connection.prepareStatement(REMOVE);
            line.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
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


    public static void addFullEmployee(String[] employee, String[] payroll) {
        addEmployee(employee);
        String stmt = "";

        switch (employee[4]){
            case "salariedEmployee":
                stmt = "INSERT INTO salariedEmployees (socialSecurityNumber, weeklySalary, bonus)" +
                        "VALUES (?, ?, ?);";
                break;
            case "commissionEmployee":
                stmt = "INSERT INTO commissionEmployees (socialSecurityNumber, grossSales, commissionRate, bonus)" +
                        "VALUES (?, ?, ?, ?);";
                break;
            case "basePlusCommissionEmployee":
                stmt = "INSERT INTO basePlusCommissionEmployees (socialSecurityNumber, grossSales, commissionRate, baseSalary, bonus)" +
                        "VALUES (?, ?, ?, ?, ?);";
                break;
            case "hourlyEmployee":
                stmt = "INSERT INTO hourlyEmployees (socialSecurityNumber, hours, wage, bonus)" +
                        "VALUES (?, ?, ?, ?);";
                break;
        }
        try {

            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement line = connection.prepareStatement(stmt);

            for(int i = 0; i < payroll.length; i++) {
                line.setString(i + 1, payroll[i]);
            }
            line.executeUpdate();
        }

        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }


}

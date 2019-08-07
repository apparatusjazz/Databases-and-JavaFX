package sample;


public class SelectStatements {


    public static String getSampleStmt(String selection) {

        String stmt = "";

        switch (selection) {
            case "Select all employees working in department SALES":
                stmt = "SELECT * FROM employees WHERE employees.departmentName = 'SALES'";

                break;
            case "Select hourly employees working over 30 hours":
                stmt = "SELECT * " +
                        " FROM employees INNER JOIN hourlyEmployees ON " +
                        " employees.socialSecurityNumber = hourlyEmployees.socialSecurityNumber " +
                        " WHERE hourlyEmployees.hours > 30";
                break;
            case "Select all commission employees in descending order of the commission rate":
                stmt = "SELECT * " +
                        " FROM employees INNER JOIN commissionEmployees ON " +
                        " employees.socialSecurityNumber = commissionEmployees.socialSecurityNumber " +
                        " ORDER BY commissionEmployees.commissionRate DESC";
                break;
            case "Increase base salary by 10% for all base-plus-commission employees":
                stmt = "UPDATE basePlusCommissionEmployees SET bonus = bonus * 1.1 ";
                break;
            case "If the employee’s birthday is in the current month, add a $100 bonus":
                //Note sure how to parse string to get only month
                break;
            case "For all commission employees with gross sales over $10,000, add a $100 bonus":
                stmt = "UPDATE commissionEmployees SET bonus = bonus + 100 WHERE grossSales > 10000";
                break;
        }

        return stmt;

    }
}

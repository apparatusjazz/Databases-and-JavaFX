package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Database Project");

        Text queryText = new Text("Enter Query");
        Text selectText = new Text("Select Choice");
        Text selectUpdateText = new Text("Select Update");

        TextField queryTextField = new TextField();

        String[] selections = {"Select all employees working in department SALES",
                "Select hourly employees working over 30 hours",
                "Select all commission employees in descending order of the commission rate"};
        String[] updateSelect = {"Increase base salary by 10% for all base-plus-commission employees",
                "If the employee’s birthday is in the current month, add a $100 bonus",
                "For all commission employees with gross sales over $10,000, add a $100 bonus"};

        ComboBox selectStatement = new ComboBox(FXCollections.observableArrayList(selections));
        ComboBox selectUpdate = new ComboBox(FXCollections.observableArrayList(updateSelect));

        Button submitQuery = new Button("Submit Query");
        Button executeChoice = new Button("Execute Choice");
        Button executeSelectUpdate = new Button("Execute Update");


        TableView<Employee> employeeTable = new TableView<>();
        ObservableList<Employee> data = FXCollections.observableArrayList();

        TableColumn column1 = new TableColumn("socialSecurityNumber");
        column1.setMinWidth(150);
        column1.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));

        TableColumn column2 = new TableColumn("firstName");
        column2.setMinWidth(150);
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn column3 = new TableColumn("lastName");
        column3.setMinWidth(150);
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn column4 = new TableColumn("birthday");
        column4.setMinWidth(150);
        column4.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        TableColumn column5 = new TableColumn("employeeType");
        column5.setMinWidth(150);
        column5.setCellValueFactory(new PropertyValueFactory<>("employeeType"));

        TableColumn column6 = new TableColumn("departmentName");
        column6.setMinWidth(150);
        column6.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

        employeeTable.getColumns().addAll(column1, column2, column3, column4, column5, column6);


        EventHandler<ActionEvent> sampleQuery = new EventHandler<>() {
            public void handle(ActionEvent e)
            {
                String stmt = SelectStatements.getSampleStmt(selectStatement.getValue().toString());
                try {

                    Connection connection = DriverManager.getConnection(EditEmployees.DATABASE_URL);
                    PreparedStatement line = connection.prepareStatement(stmt);

                    ResultSet rs = line.executeQuery();
                    while(rs.next()) {
                        Employee a = new Employee();
                        a.setSocialSecurityNumber(rs.getString("socialSecurityNumber"));
                        a.setFirstName(rs.getString("firstName"));
                        a.setLastName(rs.getString("lastName"));
                        a.setBirthday(rs.getString("birthday"));
                        a.setEmployeeType(rs.getString("employeeType"));
                        a.setDepartmentName(rs.getString("departmentName"));
                        data.add(a);

                        System.out.println(a.getBirthday() + a.getFirstName() + a.getLastName());

                    }
                    employeeTable.setItems(data);
                    line.close();
                    rs.close();

                }
                catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        };

        EventHandler<ActionEvent> submitQueryEvent = new EventHandler<>() {
            public void handle(ActionEvent e)
            {
                String stmt = queryTextField.getText();

                try {

                    Connection connection = DriverManager.getConnection(EditEmployees.DATABASE_URL);
                    PreparedStatement line = connection.prepareStatement(stmt);

                    ResultSet rs = line.executeQuery();
                    while(rs.next()) {
                        Employee a = new Employee();
                        a.setSocialSecurityNumber(rs.getString("socialSecurityNumber"));
                        a.setFirstName(rs.getString("firstName"));
                        a.setLastName(rs.getString("lastName"));
                        a.setBirthday(rs.getString("birthday"));
                        a.setEmployeeType(rs.getString("employeeType"));
                        a.setDepartmentName(rs.getString("departmentName"));
                        data.add(a);

                        employeeTable.setItems(data);

                    }
                    line.close();
                    rs.close();

                }
                catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        };

        EventHandler<ActionEvent> executeUpdate = new EventHandler<>() {
            public void handle(ActionEvent e) {

                try {
                    Connection connection = DriverManager.getConnection(EditEmployees.DATABASE_URL);
                    PreparedStatement line = connection.prepareStatement(
                            SelectStatements.getSampleStmt(selectUpdate.getValue().toString()));

                    line.executeUpdate();
                }
                catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        };

        submitQuery.setOnAction(submitQueryEvent);
        executeChoice.setOnAction(sampleQuery);
        executeSelectUpdate.setOnAction(executeUpdate);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.add(employeeTable, 0, 0, 3, 1);
        gridPane.add(queryText, 0, 1);
        gridPane.add(selectText, 0, 2);
        gridPane.add(selectUpdateText, 0, 3);
        gridPane.add(queryTextField, 1, 1);
        gridPane.add(selectStatement, 1, 2);
        gridPane.add(selectUpdate, 1, 3);
        gridPane.add(submitQuery, 2, 1);
        gridPane.add(executeChoice, 2, 2);
        gridPane.add(executeSelectUpdate, 2, 3);


        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();


        String[] info = {"000-00-0005", "Mark", "Avacado", "909-98-243", "commissionEmployee", "SALES"};
        String[] payroll = {"000-00-0005", "50000", "15006", "102"};

        //EditEmployees.addFullEmployee(info, payroll);
        EditEmployees.removeEmployee("Employees", "Maui", "");



        DisplayEmployees.show();

    }


    public static void main(String[] args) {


        launch(args);
    }
}


//Prints queries, used for testing
class DisplayEmployees {
    public static void show() {
        //final String DATABASE_URL = "jdbc:derby:books";
        final String SELECT_QUERY =
                "select * from employees";

        // use try-with-resources to connect to and query the database
        try (
                Connection connection = DriverManager.getConnection(EditEmployees.DATABASE_URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY) ) {

            // get ResultSet's meta data
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            //System.out.printf("Authors Table of Books Database:%n%n");

            // display the names of the columns in the ResultSet
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.printf("%-20s\t", metaData.getColumnName(i) );
            }
            System.out.println();

            // display query results
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("%-20s\t", resultSet.getObject(i) );
                }
                System.out.println();
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
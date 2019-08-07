package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

    private SimpleStringProperty socialSecurityNumber, firstName, lastName, birthday, employeeType, departmentName;

    public Employee() {
        this.socialSecurityNumber = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.birthday = new SimpleStringProperty();
        this.employeeType = new SimpleStringProperty();
        this.departmentName = new SimpleStringProperty();
    }


    public String getSocialSecurityNumber() {
        return socialSecurityNumber.get();
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber.set(socialSecurityNumber);
    }
    public StringProperty socialSecurityNumberProperty() {
        return socialSecurityNumber;
    }



    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }



    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }



    public String getBirthday() {
        return birthday.get();
    }
    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }
    public StringProperty birthdayProperty() {
        return birthday;
    }



    public String getEmployeeType() {
        return employeeType.get();
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType.set(employeeType);
    }
    public StringProperty employeeTypeProperty() {
        return employeeType;
    }



    public String getDepartmentName() {
        return departmentName.get();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }
    public StringProperty departmentNameProperty() {
        return departmentName;
    }






}

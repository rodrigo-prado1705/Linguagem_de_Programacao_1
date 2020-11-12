package com.rodrigo.prado;

import com.rodrigo.prado.dbconnection.Database;
import com.rodrigo.prado.dbconnection.Department;
import com.rodrigo.prado.dbconnection.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class NewEmployeeController {
    @FXML
    private TextField textName;
    @FXML
    private ComboBox<Employee> cmbManager;
    @FXML
    private DatePicker dateHireDate;
    @FXML
    private ComboBox<Department> cmbDepartment;

    private static Employee updateEmployee;

    public void initialize() {
        cmbManager.setConverter(new StringConverter<>() {
            @Override
            public String toString(Employee employee) {
                return employee == null ? null : employee.getName();
            }

            @Override
            public Employee fromString(String s) {
                return cmbManager.getItems().stream().filter(data -> data.getName().equals(s)).findFirst().orElse(null);
            }
        });

        cmbDepartment.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department department) {
                return department == null ? null : department.getName();
            }

            @Override
            public Department fromString(String s) {
                return cmbDepartment.getItems().stream().filter(data -> data.getName().equals(s)).findFirst().orElse(null);
            }
        });

        cmbManager.setItems(Employee.getEmployees());
        cmbDepartment.setItems(Department.getDepartments());

        dateHireDate.setEditable(true);
    }

    public void preset(Employee employee) {
        textName.setText(employee.getName());
        cmbManager.setValue(
                cmbManager.getItems().stream().filter(data -> data.getId() == employee.getManagerID()
                ).findFirst().orElse(null));
        cmbManager.setItems(cmbManager.getItems().filtered(employeeTest -> employeeTest.getId() != employee.getId()));
        dateHireDate.setValue(employee.getHireDate());
        cmbDepartment.setValue(employee.getDepartment());

        updateEmployee = employee;

        dateHireDate.setEditable(false);
    }

    public Employee processUpdate() {
        updateEmployee.setName(textName.getText().trim());
        updateEmployee.setManagerID(cmbManager.getValue().getId());
        updateEmployee.setDepartment(cmbDepartment.getValue());

        return updateEmployee;
    }

    public Employee processResult() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String name = textName.getText().trim();
        Employee manager = cmbManager.getValue();
        String hireDate = formatter.format(dateHireDate.getValue());
        Department department = cmbDepartment.getValue();

        Employee employee = new Employee(name, manager.getId(), hireDate, department);
        Employee.addEmployee(employee);

        return employee;
    }
}

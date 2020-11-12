package com.rodrigo.prado;

import com.rodrigo.prado.dbconnection.Department;
import com.rodrigo.prado.dbconnection.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import com.rodrigo.prado.dbconnection.Database;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class HomeController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private TableView<Employee> tableViewEmployee;
    @FXML
    private TableColumn<Employee, Integer> colID;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TableColumn<Employee, Integer> colManagerID;
    @FXML
    private TableColumn<Employee, LocalDate> colHireDate;
    @FXML
    private TableColumn<Employee, String> colDepartmentName;
    @FXML
    private ContextMenu tableContextMenu;
    
    public void initialize() {
        tableContextMenu = new ContextMenu();

        MenuItem deleteMenuItem = new MenuItem("Deletar");
        deleteMenuItem.setOnAction(actionEvent -> {
            Employee employee = tableViewEmployee.getSelectionModel().getSelectedItem();

            deleteEmployee(employee);
        });

        MenuItem updateMenuItem = new MenuItem("Editar");
        updateMenuItem.setOnAction(actionEvent -> {
            Employee employee = tableViewEmployee.getSelectionModel().getSelectedItem();

            showUpdateEmployeeDialog(employee);
        });

        tableContextMenu.getItems().addAll(deleteMenuItem, updateMenuItem);

        tableViewEmployee.setRowFactory(new Callback<TableView<Employee>, TableRow<Employee>>() {
            @Override
            public TableRow<Employee> call(TableView<Employee> employeeTableView) {
                TableRow<Employee> row = new TableRow<>();
                row.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        row.setContextMenu(null);
                    } else {
                        row.setContextMenu(tableContextMenu);
                    }
                });

                return row;
            }
        });

        refreshEmployeeTable();
        refreshDepartmentTable();

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colManagerID.setCellValueFactory(new PropertyValueFactory<>("managerID"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        colDepartmentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDepartmentName.setCellValueFactory(data -> new SimpleStringProperty(
                                                            data.getValue().getDepartment().getName()));
        tableViewEmployee.setItems(Employee.getEmployees());
    }

    @FXML
    public void showNewEmployeeDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("Adicione um novo profissional");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newEmployee.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewEmployeeController controller = fxmlLoader.getController();
            Employee newEmployee = controller.processResult();

            refreshEmployeeTable();

            tableViewEmployee.getSelectionModel().select(newEmployee);
        }
    }

    @FXML
    public void showUpdateEmployeeDialog(Employee employee) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("Atualizar dados de " + employee.getName());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newEmployee.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        NewEmployeeController controller = fxmlLoader.getController();
        controller.preset(employee);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Employee updateEmployee = controller.processUpdate();

            Employee.updateEmployee(employee, updateEmployee);
        }
    }

    public void deleteEmployee(Employee employee) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Deletar profissional");
        alert.setHeaderText("Deletar " + employee.getName());
        alert.setContentText("Tem certeza? Esta ação é irreversível. Aperte OK (continuar) ou CANCELAR (voltar).");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Employee.deleteEmployee(employee);
        }
    }

    public void refreshDepartmentTable() {
        Database database = new Database();

        try {
            Connection connection = database.open();

            ResultSet resultSet = connection.prepareStatement("SELECT * FROM dep_department").executeQuery();

            while (resultSet.next()) {
                Department department = new Department(resultSet.getInt(1), resultSet.getString(2));

                Department.addDepartments(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }

    public void refreshEmployeeTable() {
        Database database = new Database();

        // Populating table view
        try {
            Connection connection = database.open();

            ResultSet resultSet = connection.prepareStatement("SELECT emp.emp_id, emp.emp_name, " +
                    "emp.emp_manager_id, emp.emp_hire_date, dep.* FROM emp_employee emp " +
                    "LEFT JOIN dep_department dep on emp.dep_id = dep.dep_id").executeQuery();

            Employee.clearEmployees();

            while (resultSet.next()) {
                Department department = new Department(resultSet.getInt(5),
                        resultSet.getString(6));
                Employee employee = new Employee(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        department);

                Employee.addEmployee(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }
}

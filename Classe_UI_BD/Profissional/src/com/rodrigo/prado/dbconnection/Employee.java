package com.rodrigo.prado.dbconnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;

public class Employee {
    private int id;
    private String name;
    private int managerID;
    private LocalDate hireDate;
    private Department department;

    private static final ObservableList<Employee> employees = FXCollections.observableArrayList();

    public Employee(String name, int managerID, String hireDate, Department department) {
        Database database = new Database();

        try {
            Connection connection = database.open();

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO emp_employee (emp_id, emp_name, " +
                    "emp_manager_id, emp_hire_date, dep_id) VALUES (0, ?, ?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setInt(2, managerID);
            pstmt.setString(3, hireDate);
            pstmt.setInt(4, department.getId());

            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }

    public Employee(int id, String name, int managerID, String hireDate, Department department) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.managerID = managerID;
        this.hireDate = LocalDate.parse(hireDate, formatter);
        this.department = department;
    }

    public static ObservableList<Employee> getEmployees() {
        return employees;
    }

    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public static void updateEmployee(Employee oldEmployee, Employee newEmployee) {
        Database database = new Database();

        try {
            Connection connection = database.open();

            PreparedStatement pstmt = connection.prepareStatement("UPDATE emp_employee SET " +
                    " emp_name = ?, emp_manager_id = ?, dep_id = ? WHERE emp_id = ?");
            pstmt.setString(1, newEmployee.getName());
            pstmt.setInt(2, newEmployee.getManagerID());
            pstmt.setInt(3, newEmployee.getDepartment().getId());
            pstmt.setInt(4, oldEmployee.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }

        int i = employees.indexOf(oldEmployee);
        employees.set(i, newEmployee);
    }

    public static void deleteEmployee(Employee employee) {
        Database database = new Database();

        try {
            Connection connection = database.open();

            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM emp_employee " +
                    "WHERE emp_id = ?");
            pstmt.setInt(1, employee.getId());

            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }

        employees.remove(employee);
    }

    public static void clearEmployees() {
        employees.clear();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

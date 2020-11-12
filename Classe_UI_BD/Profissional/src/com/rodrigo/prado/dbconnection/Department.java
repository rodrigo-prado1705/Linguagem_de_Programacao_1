package com.rodrigo.prado.dbconnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department {
    private final int id;
    private String name;
    
    private static final ObservableList<Department> departments = FXCollections.observableArrayList();

    public Department(int id, String departmentName) {
        this.id = id;
        this.name = departmentName;
    }

    public static ObservableList<Department> getDepartments() {
        return departments;
    }

    public static void addDepartments(Department department) {
        departments.add(department);
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
}

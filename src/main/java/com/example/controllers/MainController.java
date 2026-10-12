package com.example.controllers;

import java.util.List;

import com.example.models.Employee;
import com.example.models.EmployeeService;
// import com.example.models.Mariadb;
import com.example.models.Sqlite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML
    private TableColumn<Employee, String> cityCol;

    @FXML
    private TableColumn<Employee, Integer> idCol;

    @FXML
    private TableColumn<Employee, String> nameCol;

    @FXML
    private TableColumn<Employee, Integer> salaryCol;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TextField cityField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField salaryField;

    EmployeeService employeeService;

    public MainController() {
        employeeService = new EmployeeService(new Sqlite());
    }


    @FXML
    void initialize() {
        initColumns();
        List<Employee> empList = employeeService.getEmployees();
        table.getItems().addAll(empList);
    }

    void initColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    @FXML 
    void goButton(ActionEvent event) {
        System.out.println("Teszt...");
    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        startAdding();
    }

    void startAdding() {
        System.out.println("Hozzáad...");

        Employee employee = new Employee(
            nameField.getText(), 
            cityField.getText(), 
            Integer.parseInt(salaryField.getText())
        );
        employeeService.createEmployee(employee);

        List<Employee> empList = employeeService.getEmployees();
        table.getItems().clear();
        table.getItems().addAll(empList);
        nameField.clear();
        cityField.clear();
        salaryField.clear();
    }

    @FXML
    void onClickDeleteButton(ActionEvent event) {
        startDeleting();
    }

    void startDeleting() {
        System.out.println("Törlés...");

        int selected = table.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            employeeService.deleteEmployee(table.getItems().get(selected).getId());
        }
        List<Employee> empList = employeeService.getEmployees();
        table.getItems().clear();
        table.getItems().addAll(empList);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        if(event.getClickCount() == 2) {
            startUpdating();
        }
    }

    void startUpdating() {
        System.out.println("Mozgás...");
        int selected = table.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            Employee employee = table.getItems().get(selected);
            idField.setText(String.valueOf(employee.getId()));
            nameField.setText(employee.getName());
            cityField.setText(employee.getCity());
            salaryField.setText(String.valueOf(employee.getSalary()));
        }
    }

    @FXML
    void onClickModifyButton(ActionEvent event) {
        startModifying();
    }

    void startModifying() {
        System.out.println("Frissítés...");

        int selected = table.getSelectionModel().getSelectedIndex();
        if (selected >= 0) {
            Employee employee = table.getItems().get(selected);
            employee.setName(nameField.getText());
            employee.setCity(cityField.getText());
            employee.setSalary(Integer.parseInt(salaryField.getText()));
            employeeService.updateEmployee(employee);
        }
        List<Employee> empList = employeeService.getEmployees();
        table.getItems().clear();
        table.getItems().addAll(empList);
        nameField.clear();
        cityField.clear();
        salaryField.clear();
    }

}

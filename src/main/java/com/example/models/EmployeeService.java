package com.example.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    
    Database database;

    public EmployeeService(Database database) {
        this.database = database;
    }
    
    public List<Employee> getEmployees() {
        try {
            return tryGetEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<Employee> tryGetEmployees() throws SQLException {
        Connection con = database.connect();
        if (con == null) {
            System.out.println("No connection");
            return null;
        }

        String sql = "SELECT * FROM employees";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Employee> empList = new ArrayList<>();

        while(resultSet.next()) {
            Employee employee = new Employee(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("city"),
                resultSet.getInt("salary")
            );
            empList.add(employee);
        }
        con.close();
        return empList;
    }

    public void createEmployee(Employee employee) {
        try {
            tryCreateEmployee(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tryCreateEmployee(Employee employee) throws SQLException {
        Connection con = database.connect();
        if (con == null) {
            System.out.println("No connection");
        }

        String sql = "INSERT INTO employees (name, city, salary) VALUES ('" + employee.getName() + "', '" + employee.getCity() + "', " + employee.getSalary() + ")";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        con.close();
    }

    public void deleteEmployee(int id) {
        try {
            tryDeleteEmployee(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tryDeleteEmployee(int id) throws SQLException {
        Connection con = database.connect();
        if (con == null) {
            System.out.println("No connection");
        }
        String sql = "DELETE FROM employees WHERE id=" + id;
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        con.close();
    }

    public void updateEmployee(Employee employee) {
        try {
            tryUpdateEmployee(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tryUpdateEmployee(Employee employee) throws SQLException {
        Connection con = database.connect();
        if (con == null) {
            System.out.println("No connection");
        }
        String sql = "UPDATE employees SET name='" + employee.getName() + "', city='" + employee.getCity() + "', salary=" + employee.getSalary() + " WHERE id=" + employee.getId();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        con.close();
    }
}

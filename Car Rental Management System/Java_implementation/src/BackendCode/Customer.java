package BackendCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer extends Person {

    private int Bill; // increases after every HOUR when a customer has booked car(s)

    public Customer() {
        super();
        this.Bill = 0;
    }

    public Customer(int Bill, int ID, String CNIC, String Name, String Contact_No) {
        super(ID, CNIC, Name, Contact_No);
        this.Bill = Bill;
    }

    public int getBill() {
        return Bill;
    }

    public void setBill(int Bill) {
        this.Bill = Bill;
    }

    @Override
    public void Add() {
        String sql = "INSERT INTO customers (ID, CNIC, Name, Contact_No, Bill) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);
            preparedStatement.setString(2, this.CNIC);
            preparedStatement.setString(3, this.Name);
            preparedStatement.setString(4, this.Contact_No);
            preparedStatement.setInt(5, this.Bill);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void Update() {
        String sql = "UPDATE customers SET CNIC=?, Name=?, Contact_No=?, Bill=? WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.CNIC);
            preparedStatement.setString(2, this.Name);
            preparedStatement.setString(3, this.Contact_No);
            preparedStatement.setInt(4, this.Bill);
            preparedStatement.setInt(5, this.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void Remove() {
        String sql = "DELETE FROM customers WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Customer> SearchByName(String name) {
        ArrayList<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE Name=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String CNIC = resultSet.getString("CNIC");
                    String contactNo = resultSet.getString("Contact_No");
                    int bill = resultSet.getInt("Bill");

                    customerList.add(new Customer(bill, ID, CNIC, name, contactNo));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return customerList;
    }

    public static Customer SearchByCNIC(String customerCNIC) {
        String sql = "SELECT * FROM customers WHERE CNIC=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customerCNIC);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String name = resultSet.getString("Name");
                    String contactNo = resultSet.getString("Contact_No");
                    int bill = resultSet.getInt("Bill");

                    return new Customer(bill, ID, customerCNIC, name, contactNo);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static Customer SearchByID(int id) {
        String sql = "SELECT * FROM customers WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String CNIC = resultSet.getString("CNIC");
                    String name = resultSet.getString("Name");
                    String contactNo = resultSet.getString("Contact_No");
                    int bill = resultSet.getInt("Bill");

                    return new Customer(bill, id, CNIC, name, contactNo);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<Customer> View() {
        ArrayList<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String CNIC = resultSet.getString("CNIC");
                String name = resultSet.getString("Name");
                String contactNo = resultSet.getString("Contact_No");
                int bill = resultSet.getInt("Bill");

                customerList.add(new Customer(bill, ID, CNIC, name, contactNo));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return customerList;
    }
}

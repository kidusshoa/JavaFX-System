package BackendCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarOwner extends Person {

    private int balance;

    public CarOwner() {
        super();
    }

    public CarOwner(int balance, int ID, String CNIC, String Name, String Contact_No) {
        super(ID, CNIC, Name, Contact_No);
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return super.toString() + " CarOwner{" + "balance=" + balance + '}' + "\n";
    }

    @Override
    public void Add() {
        String sql = "INSERT INTO carowners (ID, CNIC, Name, Contact_No, Balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);
            preparedStatement.setString(2, this.CNIC);
            preparedStatement.setString(3, this.Name);
            preparedStatement.setString(4, this.Contact_No);
            preparedStatement.setInt(5, this.balance);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void Update() {
        String sql = "UPDATE carowners SET CNIC=?, Name=?, Contact_No=?, Balance=? WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.CNIC);
            preparedStatement.setString(2, this.Name);
            preparedStatement.setString(3, this.Contact_No);
            preparedStatement.setInt(4, this.balance);
            preparedStatement.setInt(5, this.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void Remove() {
        String sql = "DELETE FROM carowners WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<CarOwner> SearchByName(String name) {
        ArrayList<CarOwner> carOwners = new ArrayList<>();
        String sql = "SELECT * FROM carowners WHERE Name=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String CNIC = resultSet.getString("CNIC");
                    String ownerName = resultSet.getString("Name");
                    String contactNo = resultSet.getString("Contact_No");
                    int balance = resultSet.getInt("Balance");

                    carOwners.add(new CarOwner(balance, ID, CNIC, ownerName, contactNo));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return carOwners;
    }

    public static CarOwner SearchByCNIC(String carOwnerCNIC) {
        String sql = "SELECT * FROM carowners WHERE CNIC=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, carOwnerCNIC);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String CNIC = resultSet.getString("CNIC");
                    String ownerName = resultSet.getString("Name");
                    String contactNo = resultSet.getString("Contact_No");
                    int balance = resultSet.getInt("Balance");

                    return new CarOwner(balance, ID, CNIC, ownerName, contactNo);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static CarOwner SearchByID(int id) {
        String sql = "SELECT * FROM carowners WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String CNIC = resultSet.getString("CNIC");
                    String ownerName = resultSet.getString("Name");
                    String contactNo = resultSet.getString("Contact_No");
                    int balance = resultSet.getInt("Balance");

                    return new CarOwner(balance, ID, CNIC, ownerName, contactNo);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = Car.View();
        ArrayList<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE CarOwnerID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int carID = resultSet.getInt("ID");
                    String maker = resultSet.getString("Maker");
                    String carName = resultSet.getString("Name");
                    String colour = resultSet.getString("Colour");
                    String type = resultSet.getString("Type");
                    int seatingCapacity = resultSet.getInt("SeatingCapacity");
                    String model = resultSet.getString("Model");
                    String condition = resultSet.getString("Condition");
                    String regNo = resultSet.getString("RegNo");
                    int rentPerHour = resultSet.getInt("RentPerHour");

                    carList.add(new Car(carID, maker, carName, colour, type, seatingCapacity, model, condition, regNo, rentPerHour, this));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return carList;
    }
public static ArrayList<CarOwner> View() {
    ArrayList<CarOwner> carOwnerList = new ArrayList<>();

    String sql = "SELECT * FROM carowners";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
            int ID = resultSet.getInt("ID");
            String CNIC = resultSet.getString("CNIC");
            String ownerName = resultSet.getString("Name");
            String contactNo = resultSet.getString("Contact_No");
            int balance = resultSet.getInt("Balance");

            carOwnerList.add(new CarOwner(balance, ID, CNIC, ownerName, contactNo));
        }

    } catch (SQLException e) {
        System.out.println(e);
    }

    return carOwnerList;
}

}


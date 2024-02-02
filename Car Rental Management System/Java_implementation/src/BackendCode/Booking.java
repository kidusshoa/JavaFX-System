package BackendCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//

public class Booking {

    private int ID;
    private Customer customer;
    private Car car;
    private long RentTime, ReturnTime;
    
    public Booking(int ID, Customer customer, Car car, long RentTime, long ReturnTime) {
        this.ID = ID;
        this.customer = customer;
        this.car = car;
        this.RentTime = RentTime;
        this.ReturnTime = ReturnTime;
    }

    // Constructors, getters, setters, and other methods...
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public long getRentTime() {
        return RentTime;
    }

    public void setRentTime(long RentTime) {
        this.RentTime = RentTime;
    }

    public long getReturnTime() {
        return ReturnTime;
    }

    public void setReturnTime(long ReturnTime) {
        this.ReturnTime = ReturnTime;
    }

    public void Add() {
        String sql = "INSERT INTO bookings (ID, CustomerID, CarID, RentTime, ReturnTime) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);
            preparedStatement.setInt(2, this.customer.getID());
            preparedStatement.setInt(3, this.car.getID());
            preparedStatement.setLong(4, this.RentTime);
            preparedStatement.setLong(5, this.ReturnTime);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Update() {
        String sql = "UPDATE bookings SET CustomerID=?, CarID=?, RentTime=?, ReturnTime=? WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.customer.getID());
            preparedStatement.setInt(2, this.car.getID());
            preparedStatement.setLong(3, this.RentTime);
            preparedStatement.setLong(4, this.ReturnTime);
            preparedStatement.setInt(5, this.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Remove() {
       
        String sql = "DELETE FROM bookings WHERE ID=?";
System.out.println("SQL Query: " + sql);  // Print the SQL query
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<Booking> SearchByCustomerID(int customerID) {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE CustomerID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    int carID = resultSet.getInt("CarID");
                    long rentTime = resultSet.getLong("RentTime");
                    long returnTime = resultSet.getLong("ReturnTime");

                    Customer customer = Customer.SearchByID(customerID);
                    Car car = Car.SearchByID(carID);

                    bookingList.add(new Booking(ID, customer, car, rentTime, returnTime));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return bookingList;
    }

    public static ArrayList<Booking> SearchByCarRegNo(String carRegNo) {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE CarID IN (SELECT ID FROM cars WHERE RegNo=?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, carRegNo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    int customerID = resultSet.getInt("CustomerID");
                    long rentTime = resultSet.getLong("RentTime");
                    long returnTime = resultSet.getLong("ReturnTime");

                    Customer customer = Customer.SearchByID(customerID);
                    Car car = Car.SearchByRegNo(carRegNo);

                    bookingList.add(new Booking(ID, customer, car, rentTime, returnTime));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return bookingList;
    }

    public static ArrayList<Booking> View() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                int customerID = resultSet.getInt("CustomerID");
                int carID = resultSet.getInt("CarID");
                long rentTime = resultSet.getLong("RentTime");
                long returnTime = resultSet.getLong("ReturnTime");

                Customer customer = Customer.SearchByID(customerID);
                Car car = Car.SearchByID(carID);

                bookingList.add(new Booking(ID, customer, car, rentTime, returnTime));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return bookingList;
    }

    // Other methods...
    
    public static ArrayList<Car> getBookedCars() {
    ArrayList<Car> bookedCars = new ArrayList<>();
    String sql = "SELECT c.* FROM cars c " +
                 "JOIN bookings b ON c.ID = b.CarID " +
                 "WHERE b.ReturnTime = 0";
    
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
            int ID = resultSet.getInt("ID");
            String maker = resultSet.getString("Maker");
            String name = resultSet.getString("Name");
            String colour = resultSet.getString("Colour");
            String type = resultSet.getString("Type");
            int seatingCapacity = resultSet.getInt("SeatingCapacity");
            String model = resultSet.getString("Model");
            String condition = resultSet.getString("Car_Condition");
            String regNo = resultSet.getString("RegNo");
            int rentPerHour = resultSet.getInt("RentPerHour");
            
            // Fetch the CarOwner from the database using carOwnerID
            int carOwnerID = resultSet.getInt("CarOwnerID");
            CarOwner carOwner = CarOwner.SearchByID(carOwnerID);

            bookedCars.add(new Car(ID, maker, name, colour, type, seatingCapacity, model, condition, regNo, rentPerHour, carOwner));
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return bookedCars;
}

public static ArrayList<Car> getUnbookedCars() {
    ArrayList<Car> allCars = Car.View();
    ArrayList<Car> bookedCars = Booking.getBookedCars();
    
    allCars.removeAll(bookedCars);
    
    return allCars;
}
public static ArrayList<Booking> SearchByCarID(int carID) {
    ArrayList<Booking> bookingList = new ArrayList<>();
    String sql = "SELECT * FROM bookings WHERE CarID=?";
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, carID);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int bookingID = resultSet.getInt("ID");
                int customerID = resultSet.getInt("CustomerID");
                long rentTime = resultSet.getLong("RentTime");
                long returnTime = resultSet.getLong("ReturnTime");

                // Fetch the Customer and Car objects using the IDs
                Customer customer = Customer.SearchByID(customerID);
                Car car = Car.SearchByID(resultSet.getInt("CarID"));

                bookingList.add(new Booking(bookingID, customer, car, rentTime, returnTime));
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return bookingList;
}

public int calculateBill() {
    // Calculate the bill based on your business logic
  

    // Replace this with your actual calculation logic
    int rentRatePerHour = 10; // Replace with your actual rate
    long durationInHours = (ReturnTime - RentTime) / (1000 * 60 * 60); // Convert milliseconds to hours

    return (int) (rentRatePerHour * durationInHours);
}



}

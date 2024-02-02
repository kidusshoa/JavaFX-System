package BackendCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Car {

    private int ID;
    private String Maker, Name, Colour, Type;
    int SeatingCapacity;
    String Model, Condition, RegNo;
    private int RentPerHour;
    private CarOwner carOwner;

    // Other methods and properties...
      public Car() {
    }

    public Car(int ID, String Maker, String Name, String Colour, String Type, int SeatingCapacity, String Model, String Condition, String RegNo, int RentPerHour, CarOwner carOwner) {
        this.ID = ID;
        this.Maker = Maker;
        this.Name = Name;
        this.Colour = Colour;
        this.Type = Type;
        this.SeatingCapacity = SeatingCapacity;
        this.Model = Model;
        this.Condition = Condition;
        this.RegNo = RegNo;
        this.RentPerHour = RentPerHour;
        this.carOwner = carOwner;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaker() {
        return Maker;
    }

    public void setMaker(String Maker) {
        this.Maker = Maker;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String Colour) {
        this.Colour = Colour;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getSeatingCapacity() {
        return SeatingCapacity;
    }

    public void setSeatingCapacity(int SeatingCapacity) {
        this.SeatingCapacity = SeatingCapacity;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String Condition) {
        this.Condition = Condition;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String RegNo) {
        this.RegNo = RegNo;
    }

    public int getRentPerHour() {
        return RentPerHour;
    }

    public void setRentPerHour(int RentPerHour) {
        this.RentPerHour = RentPerHour;
    }

    public CarOwner getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(CarOwner carOwner) {
        this.carOwner = carOwner;
    }

    public void Add() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus")) {
            String sql = "INSERT INTO cars (Maker, Name, Colour, Type, SeatingCapacity, Model, Car_Condition, RegNo, RentPerHour, CarOwnerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, this.Maker);
                preparedStatement.setString(2, this.Name);
                preparedStatement.setString(3, this.Colour);
                preparedStatement.setString(4, this.Type);
                preparedStatement.setInt(5, this.SeatingCapacity);
                preparedStatement.setString(6, this.Model);
                preparedStatement.setString(7, this.Condition);
                preparedStatement.setString(8, this.RegNo);
                preparedStatement.setInt(9, this.RentPerHour);
                preparedStatement.setInt(10, this.carOwner.getID());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println( e );
        }
    }

    public void Update() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus")) {
            String sql = "UPDATE cars SET Maker=?, Name=?, Colour=?, Type=?, SeatingCapacity=?, Model=?, Car_Condition=?, RentPerHour=?, CarOwnerID=? WHERE ID=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, this.Maker);
                preparedStatement.setString(2, this.Name);
                preparedStatement.setString(3, this.Colour);
                preparedStatement.setString(4, this.Type);
                preparedStatement.setInt(5, this.SeatingCapacity);
                preparedStatement.setString(6, this.Model);
                preparedStatement.setString(7, this.Condition);
                preparedStatement.setInt(8, this.RentPerHour);
                preparedStatement.setInt(9, this.carOwner.getID());
                preparedStatement.setInt(10, this.ID);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
           System.out.println( e );
        }
    }

    public void Remove() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus")) {
            String sql = "DELETE FROM cars WHERE ID=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, this.ID);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println( e );
        }
    }

    public static ArrayList<Car> View() {
        ArrayList<Car> carList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus")) {
            String sql = "SELECT * FROM cars";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String Maker = resultSet.getString("Maker");
                    String Name = resultSet.getString("Name");
                    String Colour = resultSet.getString("Colour");
                    String Type = resultSet.getString("Type");
                    int SeatingCapacity = resultSet.getInt("SeatingCapacity");
                    String Model = resultSet.getString("Model");
                    String Condition = resultSet.getString("Car_Condition");
                    String RegNo = resultSet.getString("RegNo");
                    int RentPerHour = resultSet.getInt("RentPerHour");
                    int CarOwnerID = resultSet.getInt("CarOwnerID");

                    CarOwner carOwner = CarOwner.SearchByID(CarOwnerID);

                    carList.add(new Car(ID, Maker, Name, Colour, Type, SeatingCapacity, Model, Condition, RegNo, RentPerHour, carOwner));
                }
            }
        } catch (SQLException e) {
           System.out.println( e );
        }

        return carList;
    }

    // Other methods...
    
  public static ArrayList<Car> SearchByName(String name) {
        ArrayList<Car> result = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE Name LIKE ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String Maker = resultSet.getString("Maker");
                    // ...(populate other fields accordingly)
                    String Name = resultSet.getString("Name");
                    String Colour = resultSet.getString("Colour");
                    String Type = resultSet.getString("Type");
                    int SeatingCapacity = resultSet.getInt("SeatingCapacity");
                    String Model = resultSet.getString("Model");
                    String Condition = resultSet.getString("Car_Condition");
                    String RegNo = resultSet.getString("RegNo");
                    int RentPerHour = resultSet.getInt("RentPerHour");
                    int CarOwnerID = resultSet.getInt("CarOwnerID");

                    CarOwner carOwner = CarOwner.SearchByID(CarOwnerID);

                    Car car = new Car(ID, Maker, Name, Colour, Type, SeatingCapacity, Model, Condition, RegNo, RentPerHour, carOwner);
                    result.add(car);
                }
            }
        } catch (SQLException e) {
            System.out.println( e );
        }
        return result;
    }

    public static Car SearchByID(int id) {
        String sql = "SELECT * FROM cars WHERE ID=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String Maker = resultSet.getString("Maker");
                    // ...(populate other fields accordingly)
                    String Name = resultSet.getString("Name");
                    String Colour = resultSet.getString("Colour");
                    String Type = resultSet.getString("Type");
                    int SeatingCapacity = resultSet.getInt("SeatingCapacity");
                    String Model = resultSet.getString("Model");
                    String Condition = resultSet.getString("Car_Condition");
                    String RegNo = resultSet.getString("RegNo");
                    int RentPerHour = resultSet.getInt("RentPerHour");
                    int CarOwnerID = resultSet.getInt("CarOwnerID");

                    CarOwner carOwner = CarOwner.SearchByID(CarOwnerID);

                    return new Car(ID, Maker, Name, Colour, Type, SeatingCapacity, Model, Condition, RegNo, RentPerHour, carOwner);
                }
            }
        } catch (SQLException e) {
            System.out.println( e );
        }
        return null;
    }

    public static Car SearchByRegNo(String regNo) {
        String sql = "SELECT * FROM cars WHERE RegNo=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, regNo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    String Maker = resultSet.getString("Maker");
                    // ...(populate other fields accordingly)
                    String Name = resultSet.getString("Name");
                    String Colour = resultSet.getString("Colour");
                    String Type = resultSet.getString("Type");
                    int SeatingCapacity = resultSet.getInt("SeatingCapacity");
                    String Model = resultSet.getString("Model");
                    String Condition = resultSet.getString("Car_Condition");
                    String RegNo = resultSet.getString("RegNo");
                    int RentPerHour = resultSet.getInt("RentPerHour");
                    int CarOwnerID = resultSet.getInt("CarOwnerID");

                    CarOwner carOwner = CarOwner.SearchByID(CarOwnerID);

                    return new Car(ID, Maker, Name, Colour, Type, SeatingCapacity, Model, Condition, RegNo, RentPerHour, carOwner);
                }
            }
        } catch (SQLException e) {
            System.out.println( e );
        }
        return null;
    }

    
public static boolean isNameValid(String Name) {
    // Assuming the Name column in the database allows alphanumeric characters and spaces
    return Name.matches("[a-zA-Z0-9 ]+");
}
public static boolean isRegNoValid(String RegNo) {
    // Assuming the RegNo column in the database follows the format "XXX-9999"
    return RegNo.matches("[a-zA-Z]{3}-\\d{4}");
}

public boolean isRented() {
    // Assuming there is a column named 'IsRented' in the 'cars' table
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "kidus", "kidus")) {
        String sql = "SELECT IsRented FROM cars WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.ID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    boolean isRented = resultSet.getBoolean("IsRented");
                    System.out.println("isRented(): " + isRented);
                    return isRented;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return false;
}



}

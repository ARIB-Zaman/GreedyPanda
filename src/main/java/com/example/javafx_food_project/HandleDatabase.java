package com.example.javafx_food_project;

import com.example.javafx_food_project.misc_classes.FoodItem;
import com.example.javafx_food_project.misc_classes.Restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandleDatabase {
    static public void addNewCustomer(String name, String email, String password){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS Customer (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "email TEXT," +
                        "password TEXT)";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO Customer(name, email, password) VALUES ('"+name+"','"+email+"','"+password+"')";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public void addNewRestaurantManager(String name, String email, String password){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS RestaurantManager (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "email TEXT," +
                        "password TEXT)";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO RestaurantManager(name, email, password) VALUES ('"+name+"','"+email+"','"+password+"')";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public void addNewAdmin(String name, String email, String password){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS Admin (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "email TEXT," +
                        "password TEXT)";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO Admin(name, email, password) VALUES ('"+name+"','"+email+"','"+password+"')";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public void addRestaurant(String name, String location, int manager_id, int totalrating, double rating, String openingtime, String closingtime){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS Restaurant (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "location TEXT," +
                        "manager_id INTEGER,"+
                        "totalrating INTEGER,"+
                        "rating REAL,"+
                        "openingtime TEXT,"+
                        "closingtime TEXT,"+
                        "FOREIGN KEY (manager_id) REFERENCES RestaurantManager(id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO Restaurant(name, location, manager_id, totalrating, rating, openingtime, closingtime) "+
                        "VALUES ('"+name+"','"+location+"',"+manager_id+", "+totalrating+", "+rating+",'"+openingtime+"','"+closingtime+"')";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public void addToPurchaseHistory(String date, String time, int customer_id){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS PurchaseHistory (" +
                        "order_id INTEGER PRIMARY KEY," +
                        "date TEXT," +
                        "time TEXT," +
                        "customer_id INTEGER,"+
                        "FOREIGN KEY (customer_id) REFERENCES Customer(id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO PurchaseHistory(date, time, customer_id) VALUES ('"+date+"','"+time+"',"+customer_id+")";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public void addToGlobalFoodTable(String name, String imgpath, int totalrating, double rating, int restaurant_id, double price, String tag){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS Food (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "img_path TEXT," +
                        "totalrating INTEGER,"+
                        "rating REAL,"+
                        "restaurant_id INTEGER,"+
                        "price REAL,"+
                        "tag TEXT," +
                        "calories INTEGER," +
                        "FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO Food(name, img_path, totalrating, rating, restaurant_id, price, tag, calories) "+
                                                            "VALUES ('"+name+"','"+imgpath+"',"+totalrating+","+rating+","+restaurant_id+","+price+",'"+tag+"',"+ 0 +")";
                conn.createStatement().execute(insertCusInfo);
                System.out.println("Food added: "+ name + ", img: " + imgpath);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    static public void addPlatterMenu(String name, String imgpath, int totalrating, double rating, int restaurant_id, double price){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS PlatterMenu (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "img_path TEXT," +
                        "totalrating INTEGER,"+
                        "rating REAL,"+
                        "restaurant_id INTEGER,"+
                        "price REAL,"+
                        "FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO PlatterMenu(name, img_path, totalrating, rating, restaurant_id, price) "+
                        "VALUES ('"+name+"','"+imgpath+"',"+totalrating+","+rating+","+restaurant_id+","+price+")";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    static public void addToCart(int customer_id, int food_id, int food_amount){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS Cart (" +
                        "customer_id INTEGER," +
                        "food_id INTEGER," +
                        "food_amount INTEGER,"+
                        "FOREIGN KEY (customer_id) REFERENCES Customer(id),"+
                        "FOREIGN KEY (food_id) REFERENCES Food(id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO Cart(customer_id, food_id, food_amount) "+
                        "VALUES ("+customer_id+","+food_id+","+food_amount+")";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    static public void addToPlatterMenuFoodList(int platter_id, int food_id, int food_amount){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS PlatterMenuFoodList (" +
                        "platter_id INTEGER," +
                        "food_id INTEGER," +
                        "food_amount INTEGER,"+
                        "FOREIGN KEY (platter_id) REFERENCES PlatterMenu(id),"+
                        "FOREIGN KEY (food_id) REFERENCES Food(id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO PlatterMenuFoodList(platter_id, food_id, food_amount) "+
                        "VALUES ("+platter_id+","+food_id+","+food_amount+")";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    static public void addToBoughtPlatterList(int order_id, int platter_id, int platter_amount, double platter_price){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS BoughtPlatterList (" +
                        "transaction_id INTEGER PRIMARY KEY," +
                        "order_id INTEGER," +
                        "platter_id INTEGER,"+
                        "platter_amount INTEGER,"+
                        "platter_price REAL,"+
                        "FOREIGN KEY (platter_id) REFERENCES PlatterMenu(id),"+
                        "FOREIGN KEY (order_id) REFERENCES PurchaseHistory(order_id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO BoughtPlatterList(order_id, platter_id, platter_amount, platter_price) "+
                        "VALUES ("+order_id+","+platter_id+","+platter_amount+","+platter_price+")";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public void addToBoughtFoodList(int order_id, int food_id, int food_amount, double food_price){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to database");

                // STEP 1: Create a table
                String createTable = "CREATE TABLE IF NOT EXISTS BoughtFoodList (" +
                        "transaction_id INTEGER PRIMARY KEY," +
                        "order_id INTEGER," +
                        "food_id INTEGER,"+
                        "food_amount INTEGER,"+
                        "food_price REAL,"+
                        "FOREIGN KEY (food_id) REFERENCES Food(id),"+
                        "FOREIGN KEY (order_id) REFERENCES PurchaseHistory(order_id)"+
                        ")";

                conn.createStatement().execute(createTable);
                String insertCusInfo = "INSERT INTO BoughtFoodList(order_id, food_id, food_amount, food_price) "+
                        "VALUES ("+order_id+","+food_id+","+food_amount+","+food_price+")";
                conn.createStatement().execute(insertCusInfo);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static public boolean verifyCustomer(String name, String pass){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)){

            String query = "SELECT 1 FROM Customer WHERE name = '" + name +
                    "' AND password = '" + pass + "'";

            ResultSet rs = conn.createStatement().executeQuery(query);
            return rs.next(); // Returns true if a matching record exists

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }
    public static List<Restaurant> makeRestaurantList() {
        String url = "jdbc:sqlite:Database.db";
        List<Restaurant> restaurants = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url);
            //Statement stmt = conn.createStatement();
            ResultSet rs = conn.createStatement().executeQuery("SELECT name, id, totalrating, rating, openingtime, closingtime FROM Restaurant");

            System.out.println("Connected to database");

            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                int totalRating = rs.getInt("totalrating");
                double rating = rs.getDouble("rating");
                String openingTime = rs.getString("openingtime");
                String closingTime = rs.getString("closingtime");

                Restaurant restaurant = new Restaurant(name, id, totalRating, rating, openingTime, closingTime);
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
            e.printStackTrace();
        }

        return restaurants;
    }


    public static boolean validCusForSignup(String name, String email){
        String url = "jdbc:sqlite:Database.db";
        try (Connection conn = DriverManager.getConnection(url)){

            String query = "SELECT 1 FROM Customer WHERE name = '" + name +
                    "' OR email = '" + email + "'";

            ResultSet rs = conn.createStatement().executeQuery(query);
            return !rs.next(); // Returns true if a matching doesn't record exists

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }

    public static List<FoodItem> makeFoodItemListByCategory(String category){
        List<FoodItem> foodList = new ArrayList<>();
        String url = "jdbc:sqlite:Database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                String query = "SELECT name, img_path, price, totalrating, rating FROM Food WHERE tag = '" + category + "'";

                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    String name = rs.getString("name");
                    String imgPath = rs.getString("img_path");
                    double price = rs.getDouble("price");
                    int totalRating = rs.getInt("totalrating");
                    double rating = rs.getDouble("rating");

                    FoodItem food = new FoodItem(name, imgPath, price, totalRating, rating);
                    foodList.add(food);
                }

                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodList;
    }
    public static List<FoodItem> makeFoodItemListByRestID(int id){
        List<FoodItem> foodList = new ArrayList<>();
        String url = "jdbc:sqlite:Database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                String query = "SELECT name, img_path, price, totalrating, rating FROM Food WHERE restaurant_id = " + id;

                ResultSet rs = conn.createStatement().executeQuery(query);
                while (rs.next()) {
                    String name = rs.getString("name");
                    String imgPath = rs.getString("img_path");
                    double price = rs.getDouble("price");
                    int totalRating = rs.getInt("totalrating");
                    double rating = rs.getDouble("rating");

                    FoodItem food = new FoodItem(name, imgPath, price, totalRating, rating);
                    foodList.add(food);
                }

                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foodList;
    }
}

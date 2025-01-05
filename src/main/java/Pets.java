import java.sql.*;

public class Pets {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/animals?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "user";
        String password = "Arthur010279+";

        String createTable = "CREATE TABLE IF NOT EXISTS `pets` (" +
                "`id` int(11) NOT NULL," +
                "`animal_type` VARCHAR(50) NOT NULL," +
                "`breed` VARCHAR(50) NOT NULL," +
                "`age` int(5) NOT NULL," +
                "`vaccinations` VARCHAR(100)," + // Fixed: Added a comma here
                "PRIMARY KEY (`id`)" +
                ")";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement()) {

                statement.executeUpdate(createTable);
                int rows = statement.executeUpdate("INSERT INTO pets (id, animal_type, breed, age, vaccinations) VALUES " +
                        "(1, 'dog', 'french mastiff', 11, 'all'), " +
                        "(2, 'dog', 'german shepherd', 7, 'all'), " +
                        "(3, 'cat', 'domestic cat', 500, 'none'), " +
                        "(4, 'dog', 'direwolf', 4, 'unknown'), " +
                        "(5, 'dog', 'big red dog', 2, 'unknown')");

                ResultSet resultSet = statement.executeQuery("SELECT * FROM pets");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String animalType = resultSet.getString("animal_type");
                    String breed = resultSet.getString("breed");
                    int age = resultSet.getInt("age");
                    String vaccinations = resultSet.getString("vaccinations");
                    System.out.printf("ID: %d, Type: %s, Breed: %s, Age: %d, Vaccinations: %s\n", id, animalType, breed, age, vaccinations);
                }

                System.out.printf("Added %d rows\n", rows);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

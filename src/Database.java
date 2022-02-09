import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private Connection connection;

    public Database() {}

    public void connectToDB() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost/movie_rental?"
                            + "user=root&password=sqlpass123");
        } catch (Exception e) {
            throw e;
        }
    }

    // method to add a movie in the DB
    public void addMovie(Movie movie) throws Exception {
        PreparedStatement preparedStatement = connection
                .prepareStatement("insert into  movie_rental.Movie (movie_name, release_year, price, genre, rented) values (?, ?, ?, ?, ?)");

        preparedStatement.setString(1, movie.getMovieName());
        preparedStatement.setInt(2, movie.getReleaseYear());
        preparedStatement.setInt(3, movie.getPrice());
        preparedStatement.setString(4, movie.getGenre());
        preparedStatement.setBoolean(5, movie.isRented());
        preparedStatement.executeUpdate();
    }

    // method to update the price in the DB
    public void updatePrice(int id, int price) throws Exception {
        PreparedStatement preparedStatement = connection
                .prepareStatement("update movie_rental.Movie set price=? where id=?");

        preparedStatement.setInt(1, price);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    //method that gets the information for all the movies from the DB and puts it in a list
    public ArrayList<Movie> retrieveAllMovies() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Movie ORDER BY price  ASC;");

        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            movies.add(new Movie(
                    resultSet.getInt("id"),
                    resultSet.getString("movie_name"),
                    resultSet.getInt("release_year"),
                    resultSet.getInt("price"),
                    resultSet.getString("genre"),
                    resultSet.getBoolean("rented")
            ));
        }
        return movies;
    }

    //method that gets all movie genres and the number of movies from that genre from the DB and puts them in a HashMap
    public HashMap<String, Integer> retrieveAllMoviesByGenre() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT genre, COUNT(id) as movie_count FROM Movie group by genre;");

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        while (resultSet.next()) {
            map.put(resultSet.getString("genre"), resultSet.getInt("movie_count"));
        }
        return map;
    }

    //method to set the rent status for a movie in the DB
    public void rentMovie(int id, boolean rented) throws Exception {
        PreparedStatement preparedStatement = connection
                .prepareStatement("update movie_rental.Movie set rented=? where id=?");

        preparedStatement.setBoolean(1, rented);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    //method to retrieve the rent status for a movie from the DB
    public boolean rentStatus(int id) throws Exception {
        PreparedStatement preparedStatement = connection
                .prepareStatement("select rented from movie_rental.Movie where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getBoolean("rented");
    }

    //method that gets the information for all rented movies from the DB and puts it in a list
    public ArrayList<Movie> retrieveAllRentedMovies() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Movie WHERE rented=true ORDER BY price  ASC;");

        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            movies.add(new Movie(
                    resultSet.getInt("id"),
                    resultSet.getString("movie_name"),
                    resultSet.getInt("release_year"),
                    resultSet.getInt("price"),
                    resultSet.getString("genre"),
                    resultSet.getBoolean("rented")
            ));
        }
        return movies;
    }
}

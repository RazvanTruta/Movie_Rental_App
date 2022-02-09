import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final Database DB;

    public UserConsole(Database DB) {
        this.DB = DB;
    }

    // console to show the user the options he has
    public void showConsole() {
        do {
            System.out.println("Options:");
            System.out.println("1 - Add movie");
            System.out.println("2 - Update price of movie");
            System.out.println("3 - Show all movies");
            System.out.println("4 - Show all genres and the nr. of movies for each one");
            System.out.println("5 - Rent a movie");
            System.out.println("6 - Release a movie");
            System.out.println("7 - Show all rented movies");
            System.out.println("0 - Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    this.addMovie();
                    break;
                case "2":
                    this.updatePrice();
                    break;
                case "3":
                    this.retrieveAllMovies();
                    break;
                case "4":
                    this.retrieveAllMoviesByGenre();
                    break;
                case "5":
                    this.rentMovie();
                    break;
                case "6":
                    this.releaseMovie();
                    break;
                case "7":
                    this.retrieveAllRentedMovies();
                    break;
                case "0":
                    scanner.close();
                    return;
                default:
                    System.out.println("Wrong Input, Please enter a number from 0 to 6");
                    break;
            }
        } while (true);
    }

    //collects the movie details from the user so they can be inserted in the DB with the Database.addMovie() method
    public void addMovie() {
        Scanner scanner = new Scanner(System.in);
        Movie movie = new Movie();
        System.out.println("Enter movie name: ");
        String name = scanner.nextLine();
        movie.setMovieName(name);
        System.out.println("Enter movie release year: ");
        int year = scanner.nextInt();
        movie.setReleaseYear(year);
        System.out.println("Enter movie rental price: ");
        int price = scanner.nextInt();
        scanner.nextLine();
        if (!movie.setPrice(price)) {
            return;
        }
        System.out.println("Enter movie genre: ");
        String genre = scanner.nextLine();
        movie.setGenre(genre);
        try {
            DB.addMovie(movie);
        } catch (Exception e) {
            System.out.println("Movie can not be inserted");
            System.out.println(e.toString());
        }
    }

    //collects new price for specific movie so that it can be updated in the DB with the Database.updatePrice() method
    public void updatePrice() {
        Scanner scanner = new Scanner(System.in);
        Movie movie = new Movie();
        System.out.println("Enter movie id: ");
        int id = scanner.nextInt();
        System.out.println("Enter movie rental price: ");
        int price = scanner.nextInt();
        scanner.nextLine();
        if (!movie.setPrice(price)) {
            return;
        }
        try {
            DB.updatePrice(id, price);
        } catch (Exception e) {
            System.out.println("Price can not be updated");
            System.out.println(e.toString());
        }
    }

    //prints for the user the information about the movie genres and the number for each one retrieved from the DB
    public void retrieveAllMoviesByGenre() {
        HashMap<String, Integer> map;
        try {
            map = DB.retrieveAllMoviesByGenre();
        } catch (Exception e) {
            System.out.println("Unexpected error");
            System.out.println(e.toString());
            return;
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ": " + value);
        }

    }

    //prints for the user the information about all the movies retrieved from the DB
    public void retrieveAllMovies() {
        ArrayList<Movie> movies;
        try {
            movies = DB.retrieveAllMovies();
        } catch (Exception e) {
            System.out.println("Unexpected error");
            System.out.println(e.toString());
            return;
        }
        if (movies.size() == 0) {
            System.out.println("No movies in DB");
        }
        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.get(i));
        }
    }

    //prints for the user the information about all rented movies retrieved from the DB
    public void retrieveAllRentedMovies() {
        ArrayList<Movie> movies;
        try {
            movies = DB.retrieveAllRentedMovies();
        } catch (Exception e) {
            System.out.println("Unexpected error");
            System.out.println(e.toString());
            return;
        }
        if (movies.size() == 0) {
            System.out.println("No rented movies");
        }
        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.get(i));
        }
    }

    //method to rent a movie
    public void rentMovie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter movie id: ");
        int id = scanner.nextInt();
        try {
            if (DB.rentStatus(id)) {
                System.out.println("Movie is already rented");
                return;
            }
            DB.rentMovie(id, true);
        } catch (Exception e) {
            System.out.println("Movie can not be rented");
            System.out.println(e.toString());
        }
    }

    //method to release a movie
    public void releaseMovie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter movie id: ");
        int id = scanner.nextInt();

        try {
            if (!DB.rentStatus(id)) {
                System.out.println("Movie is not rented");
                return;
            }
            DB.rentMovie(id, false);
        } catch (Exception e) {
            System.out.println("Movie can not be released");
            System.out.println(e.toString());
        }
    }
}

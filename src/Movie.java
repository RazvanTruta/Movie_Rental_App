public class Movie {
    private int id;
    private String movie_name;
    private int release_year;
    private int price;
    private String genre;
    private boolean rented;

    public Movie() {
    }

    public Movie(String movie_name, int release_year, int price, String genre) {
        this.movie_name = movie_name;
        this.release_year = release_year;
        this.price = price;
        this.genre = genre;
        this.rented = false;
    }

    public Movie(int id, String movie_name, int release_year, int price, String genre, boolean rented) {
        this.id = id;
        this.movie_name = movie_name;
        this.release_year = release_year;
        this.price = price;
        this.genre = genre;
        this.rented = rented;
    }

    public int getMovieID() {
        return id;
    }

    public String getMovieName() {
        return movie_name;
    }

    public void setMovieName(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getReleaseYear() {
        return release_year;
    }

    public void setReleaseYear(int release_year) {
        this.release_year = release_year;
    }

    public int getPrice() {
        return price;
    }

    // setter for Price in which it is verified if the price we want to set is in correlation with the movie year
    public boolean setPrice(int price) {
        if (this.release_year < 2010 && price > 5) {
            System.out.println("Price too high, must be maximum 5 euro");
            return false;
        } else {
            if ((this.release_year >= 2010 && this.release_year <= 2020) && price > 10) {
                System.out.println("Price too high, must be maximum 10 euro");
                return false;
            } else {
                if (this.release_year > 2020 && price < 13) {
                    System.out.println("Price too low, must be minimum 13 euro");
                    return false;
                } else {
                    this.price = price;
                    return true;
                }
            }
        }

    }

    public String getGenre() {
        return genre;
    }

    // setter for Genre with only 3 options
    public void setGenre(String genre) {
        if (genre.equalsIgnoreCase("comedy")
                || genre.equalsIgnoreCase("drama")
                || genre.equalsIgnoreCase("thriller")) {
            this.genre = genre.toLowerCase();
        } else {
            System.out.println("Movie Genre not supported");
        }
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    // method made to output in a specific way the movie information from a retrieval
    public String toString() {
        return "id: " + this.id + " | Name: " + this.movie_name + " | Release Year: " + this.release_year +
                " | Price: " + this.price + " | Genre: " + this.genre + " | Rented: " + this.rented;
    }
}

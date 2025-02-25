package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    // TODO add more properties here
    private List <String> genres;



    public Movie(String title, String description, List<String> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {return genres;}

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        movies.add(new Movie("Interstellar", "Eine Reise durch das Weltall und die Zeit.", new ArrayList<>(List.of("Science_Fiction", "Adventure", "Drama"))));
        movies.add(new Movie("Joker", "Die düstere Geschichte eines gebrochenen Mannes.", new ArrayList<>(List.of("Crime", "Drama", "Thriller"))));
        movies.add(new Movie("Parasite", "Eine arme Familie dringt in das Leben einer reichen Familie ein.", new ArrayList<>(List.of("Drama", "Thriller"))));
        movies.add(new Movie("Mad Max: Fury Road", "Eine postapokalyptische Verfolgungsjagd.", new ArrayList<>(List.of("Action", "Adventure", "Science_Fiction"))));
        movies.add(new Movie("The Grand Budapest Hotel", "Die Abenteuer eines legendären Hotelportiers.", new ArrayList<>(List.of("Comedy", "Drama"))));
        movies.add(new Movie("Spirited Away", "Ein magisches Abenteuer in einer geheimnisvollen Welt.", new ArrayList<>(List.of("Animation", "Fantasy", "Adventure"))));
        movies.add(new Movie("Gladiator", "Ein römischer General wird zum Gladiator.", new ArrayList<>(List.of("Action", "Drama", "History"))));
        movies.add(new Movie("Schindler’s List", "Die bewegende Geschichte eines Mannes im Zweiten Weltkrieg.", new ArrayList<>(List.of("History", "Drama", "War"))));
        movies.add(new Movie("Get Out", "Ein unheimlicher Besuch bei den Schwiegereltern.", new ArrayList<>(List.of("Horror", "Thriller", "Mystery"))));
        movies.add(new Movie("The Social Network", "Die Geschichte hinter der Gründung von Facebook.", new ArrayList<>(List.of("Biography", "Drama"))));

        return movies;
    }
}

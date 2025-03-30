package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class HomeControllerTest {
    private HomeController homeController;
    private List<Movie> movies;
    @BeforeAll
    static void setupJavaFX(){ //Methode um JavaFX Simulation zu initialisieren
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUpInstance(){                           //Methode wird vor jedem Test (before each) aufgerufen, um Instanz zu erstellen
        homeController = new HomeController();
        homeController.genreComboBox = new JFXComboBox<>(); //JFX methoden manuell initialisieren um JavaFX Umgebung zu simulieren
        homeController.searchField = new JFXTextField();
        homeController.movieListView = new JFXListView<>();
        homeController.searchBtn = new JFXButton();
        homeController.sortBtn = new JFXButton();


        //Liste mit Testdaten befüllen
        homeController.allMovies = new ArrayList<>();

        homeController.allMovies.add(new Movie("Zodiac", "Thriller über einen Serienmörder.",
                List.of("Crime", "Drama"), "id1",
                List.of("Jake Gyllenhaal", "Mark Ruffalo"), List.of("David Fincher"), 2007, 7.7
        ));

        homeController.allMovies.add(new Movie("Inception", "Ein Traum im Traum.",
                List.of("Sci-Fi", "Thriller"), "id2",
                List.of("Leonardo DiCaprio"), List.of("Christopher Nolan"), 2010, 8.8));

        homeController.allMovies.add(new Movie("Avatar", "Blauer Planet und Naturverbundenheit.",
                List.of("Sci-Fi", "Adventure"), "id3",
                List.of("Sam Worthington"), List.of("James Cameron"), 2009, 7.8));


        homeController.movieListView.setItems(FXCollections.observableArrayList(homeController.allMovies));
        homeController.initialize(null,null);
    }

    @Test
    void testInitializeMovieList() {
        HomeController homeController = new HomeController();
    }

    @Test
    void movies_sorted_ascending_alphabetic(){                  //Aufruf sortMovies function aus HomeController

        homeController.sortMovies(true);

                                                                //Überprüfung korrekte Reihenfolge

        assertEquals("Avatar", homeController.allMovies.get(0).getTitle());
        assertEquals("Inception", homeController.allMovies.get(1).getTitle());
        assertEquals("Zodiac", homeController.allMovies.get(2).getTitle());
    }


    @Test
    void movies_sorted_descending_alphabetic() { // Test für absteigende Sortierung

        homeController.sortMovies(false); // Sortiere absteigend

        // Überprüfung korrekte Reihenfolge (Z -> A)
        assertEquals("Zodiac", homeController.allMovies.get(0).getTitle());
        assertEquals("Inception", homeController.allMovies.get(1).getTitle());
        assertEquals("Avatar", homeController.allMovies.get(2).getTitle());
    }


    @Test
    void filter_no_results_found(){                             // Wenn es keine passenden Filme gibt, bleibt die Liste leer
        homeController.searchField.setText("xyz");
        homeController.genreComboBox.setValue("Horror");
        homeController.applyFilters();

        assertTrue(homeController.movieListView.getItems().isEmpty());
    }



    @Test
    void does_applyFilters_filterGenreCorrectly(){
        homeController.genreComboBox.setValue("Science_Fiction");
        homeController.applyFilters();
        ObservableList<Movie> filteredMovies = homeController.movieListView.getItems();
        assertTrue(filteredMovies.stream().allMatch(movie -> movie.getGenres().contains("Science_Fiction"))); //haben alle Filme in der gefilterten Liste die Genre
        long expectedCount = homeController.allMovies.stream().filter(movie -> movie.getGenres().contains("Science_Fiction")).count(); //überprüft Anzahl der FIlme mit dem Filter
        assertEquals(expectedCount, filteredMovies.size());
    }

    @Test
    //Wenn kein Filter gesetzt ist, sollten alle Filme bleiben
    void applyFilters_without_filters_should_show_all_movies() {
        homeController.applyFilters(); // Keine Filter gesetzt

        assertEquals(homeController.allMovies.size(), homeController.movieListView.getItems().size());
    }

    @Test
    void applyFilters_with_search_text_should_show_only_matching_movie() {
        //Wenn nach Text gefiltert wird, soll nur gesuchter Film gezeigt werden
        homeController.searchField.setText("Inception");
        homeController.applyFilters();

        assertEquals(1, homeController.movieListView.getItems().size());
    }
    @Test
    void applyFilters_with_search_text_should_show_given_Movie() {
        //Wenn nach Text gefiltert wird, soll nur gesuchter Film gezeigt werden
        homeController.searchField.setText("Avatar");
        homeController.applyFilters();

        assertEquals(1, homeController.movieListView.getItems().size());
    }

    @Test
    void applyFilters_with_non_matching_search_text_should_show_no_movies() {
        //Test ob Liste leer bleibt, wenn man nach nicht vorhandenem Text sucht
        homeController.searchField.setText("NichtVorhanden");
        homeController.applyFilters();

        assertEquals(0, homeController.movieListView.getItems().size()); // Kein Film gefunden
    }

    @Test
    void sortMovies_twice_should_not_change_order() {
        //Test ob Sortierung gleich bleibt, wenn man 2 mal soertieren drückt
        homeController.sortMovies(true); // 1. Mal sortieren
        List<String> firstSort = homeController.allMovies.stream().map(Movie::getTitle).toList();

        homeController.sortMovies(true); // 2. Mal sortieren
        List<String> secondSort = homeController.allMovies.stream().map(Movie::getTitle).toList();

        assertEquals(firstSort, secondSort); // Reihenfolge bleibt gleich
    }

}


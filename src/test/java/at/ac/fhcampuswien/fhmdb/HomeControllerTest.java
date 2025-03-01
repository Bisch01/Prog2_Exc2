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
        homeController.allMovies = new ArrayList<>();
        homeController.allMovies.add(new Movie("Zodiac", "Thriller über einen Serienmörder.", List.of("Crime", "Drama")));
        homeController.allMovies.add(new Movie("Inception", "Ein Traum im Traum.", List.of("Sci-Fi", "Thriller")));
        homeController.allMovies.add(new Movie("Avatar", "Blauer Planet und Naturverbundenheit.", List.of("Sci-Fi", "Adventure")));
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
    void does_applyFilters_filterGenreCorrectly(){
        homeController.genreComboBox.setValue("Science_Fiction");
        homeController.applyFilters();
        ObservableList<Movie> filteredMovies = homeController.movieListView.getItems();
        assertTrue(filteredMovies.stream().allMatch(movie -> movie.getGenres().contains("Science_Fiction"))); //haben alle Filme in der gefilterten Liste die Genre
        long expectedCount = homeController.allMovies.stream().filter(movie -> movie.getGenres().contains("Science_Fiction")).count(); //überprüft Anzahl der FIlme mit dem Filter
        assertEquals(expectedCount, filteredMovies.size());
    }

}
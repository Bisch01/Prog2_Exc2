package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private HomeController homeController;
    private List<Movie> movies;


    @BeforeEach
    void setUpInstance(){                           //Methode wird vor jedem Test (before each) aufgerufen, um Instanz zu erstellen
        homeController = new HomeController();
        homeController.allMovies = new ArrayList<>();
        homeController.allMovies.add(new Movie("Zodiac", "Thriller über einen Serienmörder.", List.of("Crime", "Drama")));
        homeController.allMovies.add(new Movie("Inception", "Ein Traum im Traum.", List.of("Sci-Fi", "Thriller")));
        homeController.allMovies.add(new Movie("Avatar", "Blauer Planet und Naturverbundenheit.", List.of("Sci-Fi", "Adventure")));
    }

    @Test
    void movies_sorted_ascending_alphabetic(){                  //Aufruf sortMovies function aus HomeController

        homeController.sortMovies(true);

                                                                //Überprüfung korrekte Reihenfolge

        assertEquals("Avatar", homeController.allMovies.get(0).getTitle());
        assertEquals("Inception", homeController.allMovies.get(1).getTitle());
        assertEquals("Zodiac", homeController.allMovies.get(2).getTitle());
    }

}
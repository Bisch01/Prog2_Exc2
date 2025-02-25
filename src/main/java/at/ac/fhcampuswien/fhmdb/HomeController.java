package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView;

    @FXML
    public JFXComboBox<String> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        // Genre-Filter initialisieren
        genreComboBox.setPromptText("Filter by Genre");
        List<String> uniqueGenres = allMovies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        uniqueGenres.add(0, "Alle Genres");
        genreComboBox.getItems().addAll(uniqueGenres);
        genreComboBox.setValue("Alle Genres");

        // Event-Handler für Filter setzen
        searchBtn.setOnAction(event -> applyFilters());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        genreComboBox.setOnAction(event -> applyFilters());

        // Event-Handler für Sortierung (JETZT an der richtigen Stelle!)
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                observableMovies.sort((m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()));
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.sort((m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()));
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    private void applyFilters() {
        String selectedGenre = genreComboBox.getValue();
        String searchText = (searchField.getText() != null) ? searchField.getText().toLowerCase().trim() : "";

        if ((selectedGenre == null || selectedGenre.equals("Alle Genres")) && searchText.isEmpty()) {
            observableMovies.setAll(allMovies);
            return;
        }

        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> {
                    boolean matchesGenre = (selectedGenre == null || selectedGenre.equals("Alle Genres") || movie.getGenres().contains(selectedGenre));
                    boolean matchesSearchText = searchText.isEmpty() ||
                            movie.getTitle().toLowerCase().contains(searchText) ||
                            (movie.getDescription() != null && movie.getDescription().toLowerCase().contains(searchText));

                    return matchesGenre && matchesSearchText;
                })
                .collect(Collectors.toList());

        observableMovies.setAll(filteredMovies);
    }
}

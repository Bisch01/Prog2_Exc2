package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        //Führt API-Anfrage durch und gibt Ergebnis in Konsole aus
        try {
            MovieAPI movie = new MovieAPI();  // Instanz erstellen
            String response = movie.run("https://prog2.fh-campuswien.ac.at/swagger-ui/index.html");
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Fehler beim Abrufen der API-Daten: " + e.getMessage());
        }
    }

    }

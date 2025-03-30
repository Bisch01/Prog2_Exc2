package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.List;



public class MovieAPI {

    // Initialisierung OkHttp Client
    private final OkHttpClient client = new OkHttpClient();
    // Initialisierung Gson
    private final Gson gson = new Gson();

    // Methode run bekommt eine URL übergeben
    // Sendet HTTP-GET Anfrage an URL
    public List<Movie> fetchMovies() throws IOException {

        String url = "https://prog2.fh-campuswien.ac.at/movies";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();

        //Ausführung der HTTP-Anfrage und Antwrtverarbeitung
        try (Response response = client.newCall(request).execute()) {
            if(!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            String json = response.body().string();
            Type movieListType = new TypeToken<List<Movie>>() {}.getType();
            return gson.fromJson(json, movieListType);
        }
    }

    public List<Movie> fetchMoviesWithParams(String query, String genre, String releaseYear, String ratingFrom) throws IOException{
        // Baue URL mit optionalen Parametern für query und genre
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://prog2.fh-campuswien.ac.at/movies").newBuilder();

        // Wenn ein Suchtext vorhanden ist, als Parameter anhängen (Freitextsuche)
        if (query != null && !query.isBlank()) {
            urlBuilder.addQueryParameter("query", query);
        }

        // Wenn ein Genre gewählt wurde (außer "Alle Genres"), Parameter anhängen
        if (genre != null && !genre.equals("Alle Genres")) {
            urlBuilder.addQueryParameter("genre", genre.toUpperCase()); // API erwartet Großbuchstaben
        }

        // Füge Erscheinungsjahr als Filter hinzu, falls angegeben
        if (releaseYear != null && !releaseYear.isBlank()) {
            urlBuilder.addQueryParameter("releaseYear", releaseYear);
        }

        //Füge Bewertung als Filter hinzu
        if (ratingFrom != null && !ratingFrom.isBlank()) {
            urlBuilder.addQueryParameter("ratingFrom", ratingFrom);
        }


        // Baue vollständige URL aus dem Builder
        String url = urlBuilder.build().toString();

        // Erstelle den HTTP-Request mit User-Agent Header (Pflicht für die API!)
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent")
                .build();

        // Führe den HTTP-Request aus und verarbeite die Antwort
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Fehler beim API-Aufruf: " + response);
            }

            // Lese den JSON-String aus dem Response-Body
            String json = response.body().string();

            // Verwende Gson, um JSON in eine Liste von Movie-Objekten zu parsen
            Type movieListType = new TypeToken<List<Movie>>() {}.getType();
            return gson.fromJson(json, movieListType);
        }
    }
}

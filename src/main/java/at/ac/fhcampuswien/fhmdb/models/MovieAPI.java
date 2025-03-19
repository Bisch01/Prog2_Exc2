package at.ac.fhcampuswien.fhmdb.models;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class MovieAPI {

    // Initialisierung OkHttp Client
    final OkHttpClient client = new OkHttpClient();

    // Methode run bekommt eine URL übergeben
    // Sendet HTTP-GET Anfrage an URL
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        //Ausführung der HTTP-Anfrage und Antwrtverarbeitung
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}

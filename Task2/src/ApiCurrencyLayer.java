import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApiCurrencyLayer {
    private static final String API_URL = "http://api.currencylayer.com/live";
    private static final String API_URL_1 = "http://api.currencylayer.com/list";
    private static final String API_URL_2 = "http://api.currencylayer.com/historical";
    private static final String API_URL_3 = "http://api.currencylayer.com/timeframe";
    private static final String API_KEY = "bcfb9f28e78e8707b4a32b647a43caa7";

    public static void main(String[] args) {
        try {
            String url = API_URL + "?access_key=" + API_KEY + "&currencies=EUR,GBP&format=1";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println("Ответ API:\n" + json);

            //1
            String url1 = API_URL_1 + "?access_key=" + API_KEY + "&currencies=EUR,GBP&format=1";

            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(url1))
                    .GET()
                    .build();

            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

            String json1 = response1.body();
            System.out.println("Ответ API:\n" + json1);

            // 2
            String url2 = API_URL_2 +
                    "?access_key=" + API_KEY +
                    "&date=2018-02-22" +
                    "&source=USD" +
                    "&currencies=EUR,GBP,JPY";

            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create(url2))
                    .GET()
                    .build();

            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
            String json2 = response2.body();

            System.out.println("Ответ API:");
            System.out.println(json2);

            //3
            LocalDate start = LocalDate.of(2016, 2, 25);
            LocalDate end = LocalDate.of(2017, 2, 21);

            System.out.println("Ответ API:");

            String url3 = API_URL_3 +
                    "?access_key=" + API_KEY +
                    "&start_date=" + start +
                    "&end_date=" + end +
                    "&source=USD" +
                    "&currencies=EUR";

            HttpRequest request3 = HttpRequest.newBuilder()
                    .uri(URI.create(url3))
                    .GET()
                    .build();

            HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
            String json3 = response3.body();
            System.out.println(json3);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

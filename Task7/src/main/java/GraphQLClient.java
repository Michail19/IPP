import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GraphQLClient {

    private static final String GRAPHQL_URL = "http://localhost:8080/graphql";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) {
        testCreateCar();
        testGetAllCars();
        testGetCarsByBrand();
    }

    private static void testCreateCar() {
        String mutation = """
        {
          "query": "mutation { createCar(carInput: { title: \\"Tesla Model 3\\", brand: \\"Tesla\\", price: 45000.0, age: 2 }) { id title brand price age } }"
        }
        """;

        String response = executeGraphQL(mutation);
        System.out.println("Создан автомобиль: " + response);
    }

    private static void testGetAllCars() {
        String query = """
        {
          "query": "query { getAllCars { id title brand price age } }"
        }
        """;

        String response = executeGraphQL(query);
        System.out.println("Все автомобили: " + response);
    }

    private static void testGetCarsByBrand() {
        String query = """
        {
          "query": "query { getCarsByBrand(brand: \\"Toyota\\") { id title price age } }"
        }
        """;

        String response = executeGraphQL(query);
        System.out.println("Автомобили Toyota: " + response);
    }

    private static String executeGraphQL(String requestBody) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GRAPHQL_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}

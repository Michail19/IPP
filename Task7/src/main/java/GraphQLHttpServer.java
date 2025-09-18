import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import graphql.ExecutionInput;
import graphql.ExecutionResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class GraphQLHttpServer {

    private final CarGraphQL carGraphQL;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GraphQLHttpServer(CarGraphQL carGraphQL) {
        this.carGraphQL = carGraphQL;
    }

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/graphql", new GraphQLHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("GraphQL сервер запущен на http://localhost:" + port + "/graphql");
        System.out.println("Пример запроса: ");
        System.out.println("POST http://localhost:" + port + "/graphql");
        System.out.println("Content-Type: application/json");
        System.out.println("""
        {
          "query": "query { getAllCars { id title brand price age } }"
        }
        """);
    }

    private class GraphQLHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            } else {
                sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            }
        }

        private void handlePostRequest(HttpExchange exchange) throws IOException {
            try {
                InputStream requestBody = exchange.getRequestBody();
                Map<String, Object> request = objectMapper.readValue(requestBody, HashMap.class);

                String query = (String) request.get("query");
                Map<String, Object> variables = (Map<String, Object>) request.get("variables");

                if (variables == null) {
                    variables = new HashMap<>();
                }

                ExecutionResult executionResult = carGraphQL.getGraphQL()
                        .execute(ExecutionInput.newExecutionInput()
                                .query(query)
                                .variables(variables)
                                .build());

                Map<String, Object> response = new HashMap<>();
                response.put("data", executionResult.getData());
                if (executionResult.getErrors() != null && !executionResult.getErrors().isEmpty()) {
                    response.put("errors", executionResult.getErrors());
                }

                sendResponse(exchange, 200, objectMapper.writeValueAsString(response));

            } catch (Exception e) {
                sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
            }
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

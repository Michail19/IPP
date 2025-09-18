public class GraphQLServer {
    public static void main(String[] args) {
        try {
            CarService carService = new CarService();
            carService.initializeTestData();

            CarGraphQL carGraphQL = new CarGraphQL(carService);

            GraphQLHttpServer httpServer = new GraphQLHttpServer(carGraphQL);
            httpServer.start(8080);

            System.out.println("Сервер успешно запущен!");
            System.out.println("Доступные endpoint:");
            System.out.println("  POST http://localhost:8080/graphql");

        } catch (Exception e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

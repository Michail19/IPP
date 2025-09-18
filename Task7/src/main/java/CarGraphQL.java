import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.GraphQL;

import java.util.List;
import java.util.Map;

public class CarGraphQL {

    private final CarService carService;
    private final GraphQL graphQL;

    public CarGraphQL(CarService carService) {
        this.carService = carService;
        this.graphQL = createGraphQL();
    }

    private GraphQL createGraphQL() {
        String schema = """
            type Car {
                id: ID!
                title: String!
                brand: String!
                price: Float!
                age: Int!
            }

            input CarInput {
                title: String!
                brand: String!
                price: Float!
                age: Int!
            }

            type Query {
                getAllCars: [Car!]!
                getCarById(id: ID!): Car
                getCarsByBrand(brand: String!): [Car!]!
            }

            type Mutation {
                createCar(carInput: CarInput!): Car!
                updateCar(id: ID!, carInput: CarInput!): Car!
                deleteCar(id: ID!): Boolean!
            }
        """;

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("getAllCars", this::getAllCars)
                        .dataFetcher("getCarById", this::getCarById)
                        .dataFetcher("getCarsByBrand", this::getCarsByBrand)
                )
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("createCar", this::createCar)
                        .dataFetcher("updateCar", this::updateCar)
                        .dataFetcher("deleteCar", this::deleteCar)
                )
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(
                typeDefinitionRegistry, runtimeWiring
        );

        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    // Data fetchers
    private List<Car> getAllCars(DataFetchingEnvironment env) {
        return carService.getAllCars();
    }

    private Car getCarById(DataFetchingEnvironment env) {
        String id = env.getArgument("id");
        return carService.getCarById(id);
    }

    private List<Car> getCarsByBrand(DataFetchingEnvironment env) {
        String brand = env.getArgument("brand");
        return carService.getCarsByBrand(brand);
    }

    private Car createCar(DataFetchingEnvironment env) {
        Map<String, Object> carInput = env.getArgument("carInput");
        String title = (String) carInput.get("title");
        String brand = (String) carInput.get("brand");
        double price = ((Number) carInput.get("price")).doubleValue();
        int age = ((Number) carInput.get("age")).intValue();

        return carService.createCar(title, brand, price, age);
    }

    private Car updateCar(DataFetchingEnvironment env) {
        String id = env.getArgument("id");
        Map<String, Object> carInput = env.getArgument("carInput");
        String title = (String) carInput.get("title");
        String brand = (String) carInput.get("brand");
        double price = ((Number) carInput.get("price")).doubleValue();
        int age = ((Number) carInput.get("age")).intValue();

        return carService.updateCar(id, title, brand, price, age);
    }

    private Boolean deleteCar(DataFetchingEnvironment env) {
        String id = env.getArgument("id");
        return carService.deleteCar(id);
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}

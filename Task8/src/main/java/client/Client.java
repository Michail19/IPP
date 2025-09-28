package client;

import com.example.grpc.ProductResponse;

import java.util.List;
import java.util.Scanner;

public class Client {
    private final ShoppingListClient client;
    private final Scanner scanner;

    public Client(String host, int port) {
        this.client = new ShoppingListClient(host, port);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Shopping List Manager ===");

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createProduct();
                    break;
                case "2":
                    listProducts();
                    break;
                case "3":
                    getProduct();
                    break;
                case "4":
                    updateProduct();
                    break;
                case "5":
                    deleteProduct();
                    break;
                case "6":
                    markAsPurchased();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Create product");
        System.out.println("2. List all products");
        System.out.println("3. Get product by ID");
        System.out.println("4. Update product");
        System.out.println("5. Delete product");
        System.out.println("6. Mark product as purchased");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void createProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter category: ");
        String category = scanner.nextLine().trim();

        try {
            ProductResponse response = client.createProduct(name, quantity, category);
            System.out.println("Product created successfully:");
            printProduct(response);
        } catch (Exception e) {
            System.err.println("Error creating product: " + e.getMessage());
        }
    }

    private void listProducts() {
        System.out.print("Include purchased products? (y/n): ");
        boolean includePurchased = scanner.nextLine().trim().equalsIgnoreCase("y");

        try {
            List<ProductResponse> products = client.getAllProducts(includePurchased);
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                System.out.println("Products:");
                for (ProductResponse product : products) {
                    printProduct(product);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.err.println("Error listing products: " + e.getMessage());
        }
    }

    private void getProduct() {
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine().trim();

        try {
            ProductResponse product = client.getProduct(id);
            System.out.println("Product found:");
            printProduct(product);
        } catch (Exception e) {
            System.err.println("Error getting product: " + e.getMessage());
        }
    }

    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter new quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter new category: ");
        String category = scanner.nextLine().trim();

        try {
            ProductResponse response = client.updateProduct(id, name, quantity, category);
            System.out.println("Product updated successfully:");
            printProduct(response);
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        String id = scanner.nextLine().trim();

        try {
            boolean success = client.deleteProduct(id);
            if (success) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    private void markAsPurchased() {
        System.out.print("Enter product ID to mark as purchased: ");
        String id = scanner.nextLine().trim();

        try {
            ProductResponse response = client.markAsPurchased(id);
            System.out.println("Product marked as purchased:");
            printProduct(response);
        } catch (Exception e) {
            System.err.println("Error marking product as purchased: " + e.getMessage());
        }
    }

    private void printProduct(ProductResponse product) {
        System.out.printf("ID: %s\n", product.getId());
        System.out.printf("Name: %s\n", product.getName());
        System.out.printf("Quantity: %d\n", product.getQuantity());
        System.out.printf("Category: %s\n", product.getCategory());
        System.out.printf("Purchased: %s\n", product.getPurchased() ? "Yes" : "No");
        System.out.printf("Created: %s\n", product.getCreatedAt());
        System.out.printf("Updated: %s\n", product.getUpdatedAt());
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 9090;

        if (args.length >= 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        Client cli = new Client(host, port);
        try {
            cli.start();
        } finally {
            try {
                cli.client.shutdown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
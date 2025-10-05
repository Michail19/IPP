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
        System.out.println("=== Менеджер списка покупок ===");

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
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nВыберите опцию:");
        System.out.println("1. Создать продукт");
        System.out.println("2. Показать все продукты");
        System.out.println("3. Получить продукт по ID");
        System.out.println("4. Обновить продукт");
        System.out.println("5. Удалить продукт");
        System.out.println("6. Отметить продукт как купленный");
        System.out.println("0. Выход");
        System.out.print("Введите ваш выбор: ");
    }

    private void createProduct() {
        System.out.print("Введите название продукта: ");
        String name = scanner.nextLine().trim();

        System.out.print("Введите количество: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Введите категорию: ");
        String category = scanner.nextLine().trim();

        try {
            ProductResponse response = client.createProduct(name, quantity, category);
            System.out.println("Продукт успешно создан:");
            printProduct(response);
        } catch (Exception e) {
            System.err.println("Ошибка при создании продукта: " + e.getMessage());
        }
    }

    private void listProducts() {
        System.out.print("Включая купленные продукты? (д/н): ");
        boolean includePurchased = scanner.nextLine().trim().equalsIgnoreCase("д");

        try {
            List<ProductResponse> products = client.getAllProducts(includePurchased);
            if (products.isEmpty()) {
                System.out.println("Продукты не найдены.");
            } else {
                System.out.println("Продукты:");
                for (ProductResponse product : products) {
                    printProduct(product);
                    System.out.println("---");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при выводе списка продуктов: " + e.getMessage());
        }
    }

    private void getProduct() {
        System.out.print("Введите ID продукта: ");
        String id = scanner.nextLine().trim();

        try {
            ProductResponse product = client.getProduct(id);
            System.out.println("Продукт найден:");
            printProduct(product);
        } catch (Exception e) {
            System.err.println("Ошибка при получении продукта: " + e.getMessage());
        }
    }

    private void updateProduct() {
        System.out.print("Введите ID продукта для обновления: ");
        String id = scanner.nextLine().trim();

        System.out.print("Введите новое название: ");
        String name = scanner.nextLine().trim();

        System.out.print("Введите новое количество: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Введите новую категорию: ");
        String category = scanner.nextLine().trim();

        try {
            ProductResponse response = client.updateProduct(id, name, quantity, category);
            System.out.println("Продукт успешно обновлен:");
            printProduct(response);
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении продукта: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        System.out.print("Введите ID продукта для удаления: ");
        String id = scanner.nextLine().trim();

        try {
            boolean success = client.deleteProduct(id);
            if (success) {
                System.out.println("Продукт успешно удален.");
            } else {
                System.out.println("Продукт не найден.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении продукта: " + e.getMessage());
        }
    }

    private void markAsPurchased() {
        System.out.print("Введите ID продукта для отметки как купленный: ");
        String id = scanner.nextLine().trim();

        try {
            ProductResponse response = client.markAsPurchased(id);
            System.out.println("Продукт отмечен как купленный:");
            printProduct(response);
        } catch (Exception e) {
            System.err.println("Ошибка при отметке продукта как купленного: " + e.getMessage());
        }
    }

    private void printProduct(ProductResponse product) {
        System.out.printf("ID: %s\n", product.getId());
        System.out.printf("Название: %s\n", product.getName());
        System.out.printf("Количество: %d\n", product.getQuantity());
        System.out.printf("Категория: %s\n", product.getCategory());
        System.out.printf("Куплен: %s\n", product.getPurchased() ? "Да" : "Нет");
        System.out.printf("Создан: %s\n", product.getCreatedAt());
        System.out.printf("Обновлен: %s\n", product.getUpdatedAt());
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
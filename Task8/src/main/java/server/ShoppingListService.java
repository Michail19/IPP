package server;

import com.example.grpc.*;
import model.Product;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingListService extends ShoppingListServiceGrpc.ShoppingListServiceImplBase {
    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Product product = new Product(
                    request.getName(),
                    request.getQuantity(),
                    request.getCategory()
            );

            products.put(product.getId(), product);

            ProductResponse response = convertToProductResponse(product);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            System.out.println("Создан продукт: " + product);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Ошибка при создании продукта: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = products.get(request.getId());
        if (product == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Продукт не найден с id: " + request.getId())
                    .asRuntimeException());
            return;
        }

        ProductResponse response = convertToProductResponse(product);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProducts(GetAllProductsRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            products.values().stream()
                    .filter(product -> request.getIncludePurchased() || !product.isPurchased())
                    .map(this::convertToProductResponse)
                    .forEach(responseObserver::onNext);

            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Ошибка при получении продуктов: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void updateProduct(UpdateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = products.get(request.getId());
        if (product == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Продукт не найден с id: " + request.getId())
                    .asRuntimeException());
            return;
        }

        try {
            product.setName(request.getName());
            product.setQuantity(request.getQuantity());
            product.setCategory(request.getCategory());
            product.setUpdatedAt(java.time.LocalDateTime.now());

            ProductResponse response = convertToProductResponse(product);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

            System.out.println("Обновлен продукт: " + product);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Ошибка при обновлении продукта: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<DeleteProductResponse> responseObserver) {
        Product removed = products.remove(request.getId());
        if (removed == null) {
            responseObserver.onNext(DeleteProductResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Продукт не найден с id: " + request.getId())
                    .build());
        } else {
            responseObserver.onNext(DeleteProductResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Продукт успешно удален")
                    .build());
            System.out.println("Удален продукт: " + removed);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void markAsPurchased(MarkAsPurchasedRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = products.get(request.getId());
        if (product == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Продукт не найден с id: " + request.getId())
                    .asRuntimeException());
            return;
        }

        product.setPurchased(true);
        product.setUpdatedAt(java.time.LocalDateTime.now());

        ProductResponse response = convertToProductResponse(product);
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        System.out.println("Отмечен как купленный: " + product);
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.newBuilder()
                .setId(product.getId())
                .setName(product.getName())
                .setQuantity(product.getQuantity())
                .setCategory(product.getCategory())
                .setPurchased(product.isPurchased())
                .setCreatedAt(product.getCreatedAt().format(formatter))
                .setUpdatedAt(product.getUpdatedAt().format(formatter))
                .build();
    }
}
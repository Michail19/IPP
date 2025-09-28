package client;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ShoppingListClient {
    private final ManagedChannel channel;
    private final ShoppingListServiceGrpc.ShoppingListServiceBlockingStub blockingStub;
    private final ShoppingListServiceGrpc.ShoppingListServiceStub asyncStub;

    public ShoppingListClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.blockingStub = ShoppingListServiceGrpc.newBlockingStub(channel);
        this.asyncStub = ShoppingListServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ProductResponse createProduct(String name, int quantity, String category) {
        try {
            CreateProductRequest request = CreateProductRequest.newBuilder()
                    .setName(name)
                    .setQuantity(quantity)
                    .setCategory(category)
                    .build();

            return blockingStub.createProduct(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            throw e;
        }
    }

    public ProductResponse getProduct(String id) {
        try {
            GetProductRequest request = GetProductRequest.newBuilder()
                    .setId(id)
                    .build();

            return blockingStub.getProduct(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            throw e;
        }
    }

    public List<ProductResponse> getAllProducts(boolean includePurchased) {
        List<ProductResponse> products = new ArrayList<>();
        CountDownLatch finishLatch = new CountDownLatch(1);

        try {
            GetAllProductsRequest request = GetAllProductsRequest.newBuilder()
                    .setIncludePurchased(includePurchased)
                    .build();

            asyncStub.getAllProducts(request, new StreamObserver<ProductResponse>() {
                @Override
                public void onNext(ProductResponse product) {
                    products.add(product);
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Error streaming products: " + t.getMessage());
                    finishLatch.countDown();
                }

                @Override
                public void onCompleted() {
                    finishLatch.countDown();
                }
            });

            finishLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while waiting for products");
        }

        return products;
    }

    public ProductResponse updateProduct(String id, String name, int quantity, String category) {
        try {
            UpdateProductRequest request = UpdateProductRequest.newBuilder()
                    .setId(id)
                    .setName(name)
                    .setQuantity(quantity)
                    .setCategory(category)
                    .build();

            return blockingStub.updateProduct(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            throw e;
        }
    }

    public boolean deleteProduct(String id) {
        try {
            DeleteProductRequest request = DeleteProductRequest.newBuilder()
                    .setId(id)
                    .build();

            DeleteProductResponse response = blockingStub.deleteProduct(request);
            return response.getSuccess();
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return false;
        }
    }

    public ProductResponse markAsPurchased(String id) {
        try {
            MarkAsPurchasedRequest request = MarkAsPurchasedRequest.newBuilder()
                    .setId(id)
                    .build();

            return blockingStub.markAsPurchased(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            throw e;
        }
    }
}
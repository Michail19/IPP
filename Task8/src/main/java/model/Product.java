package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    private final String id;
    private String name;
    private int quantity;
    private String category;
    private boolean purchased;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(String name, int quantity, String category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.purchased = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isPurchased() { return purchased; }
    public void setPurchased(boolean purchased) { this.purchased = purchased; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', quantity=%d, category='%s', purchased=%s}",
                id, name, quantity, category, purchased);
    }
}

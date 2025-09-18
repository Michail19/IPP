public class Car {
    private String id;
    private String title;
    private String brand;
    private double price;
    private int age;

    public Car() {}

    public Car(String id, String title, String brand, double price, int age) {
        this.id = id;
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.age = age;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return String.format("Car{id='%s', title='%s', brand='%s', price=%.2f, age=%d}",
                id, title, brand, price, age);
    }
}
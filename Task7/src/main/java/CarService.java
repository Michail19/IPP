import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CarService {

    private final Map<String, Car> cars = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    public Car getCarById(String id) {
        return cars.get(id);
    }

    public List<Car> getCarsByBrand(String brand) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars.values()) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                result.add(car);
            }
        }
        return result;
    }

    public Car createCar(String title, String brand, double price, int age) {
        String id = String.valueOf(idCounter.getAndIncrement());
        Car car = new Car(id, title, brand, price, age);
        cars.put(id, car);
        return car;
    }

    public Car updateCar(String id, String title, String brand, double price, int age) {
        Car car = cars.get(id);
        if (car != null) {
            car.setTitle(title);
            car.setBrand(brand);
            car.setPrice(price);
            car.setAge(age);
        }
        return car;
    }

    public boolean deleteCar(String id) {
        return cars.remove(id) != null;
    }

    // Инициализация тестовыми данными
    public void initializeTestData() {
        createCar("Toyota Camry", "Toyota", 25000.0, 2);
        createCar("BMW X5", "BMW", 65000.0, 1);
        createCar("Honda Civic", "Honda", 22000.0, 3);
    }
}
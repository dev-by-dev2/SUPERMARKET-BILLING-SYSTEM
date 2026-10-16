// Demonstrates Encapsulation
public class Item {
    private String name;
    private double price;
    private Discount discount;

    public Item(String name, double price, Discount discount) {
        this.name = name;
        this.price = price;
        this.discount = discount != null ? discount : new NoDiscount();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}

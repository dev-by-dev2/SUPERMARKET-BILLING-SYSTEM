// Abstract base class for different discount strategies
// Demonstrates Abstraction and Inheritance
public abstract class Discount {
    // Demonstrates Polymorphism (to be overridden)
    public abstract double calculateDiscount(double price, int quantity);
    public abstract String getDescription();
}

// Strategy for Percentage Discount
class PercentageDiscount extends Discount {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double price, int quantity) {
        return (price * quantity) * (percentage / 100.0);
    }

    @Override
    public String getDescription() {
        return percentage + "% off";
    }
}

// Strategy for Fixed Amount Discount per item
class FixedDiscount extends Discount {
    private double fixedAmount;

    public FixedDiscount(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double calculateDiscount(double price, int quantity) {
        return Math.min(price * quantity, fixedAmount * quantity);
    }

    @Override
    public String getDescription() {
        return String.format("%.2f", fixedAmount) + " off per item";
    }
}

// Default No Discount strategy
class NoDiscount extends Discount {
    @Override
    public double calculateDiscount(double price, int quantity) {
        return 0; // No discount
    }

    @Override
    public String getDescription() {
        return "No discount";
    }
}

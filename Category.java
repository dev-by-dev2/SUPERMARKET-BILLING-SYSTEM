import java.util.ArrayList;
import java.util.List;

// Demonstrates Encapsulation
public class Category {
    private String name;
    private List<Item> items;

    public Category(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }
}

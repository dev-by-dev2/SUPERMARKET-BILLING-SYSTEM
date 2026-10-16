import java.util.ArrayList;
import java.util.List;

// Demonstrates Encapsulation
public class Cart {
    // Public Inner class holding item and quantity so Bill can use it
    public static class CartItem {
        private Item item;
        private int quantity;

        public CartItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public Item getItem() { return item; }
        public int getQuantity() { return quantity; }
    }

    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addItem(Item item, int quantity) {
        cartItems.add(new CartItem(item, quantity));
    }

    public List<CartItem> getItems() {
        return cartItems;
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}

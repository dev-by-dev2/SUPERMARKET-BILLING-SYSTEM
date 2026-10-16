import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Bill {
    private Cart cart;
    private boolean wantsBag;
    private static final double BAG_CHARGE = 15.0; // ₹15 charge

    public Bill(Cart cart, boolean wantsBag) {
        this.cart = cart;
        this.wantsBag = wantsBag;
    }

    public String generateInvoiceString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==========================================================================\n");
        sb.append("                              FINAL INVOICE                               \n");
        sb.append("==========================================================================\n");
        
        if (cart.isEmpty()) {
            sb.append("Your cart is empty. Nothing to bill.\n");
            sb.append("==========================================================================\n");
            return sb.toString();
        }

        double subTotal = 0.0;
        double totalDiscount = 0.0;
        
        sb.append(String.format("%-30s | %-4s | %-12s | %-12s\n", "Item", "Qty", "Price/unit", "Final"));
        sb.append("--------------------------------------------------------------------------\n");

        for (Cart.CartItem ci : cart.getItems()) {
            Item item = ci.getItem();
            int qty = ci.getQuantity();
            
            double originalPrice = item.getPrice() * qty;
            double discountAmount = item.getDiscount().calculateDiscount(item.getPrice(), qty);
            double finalPrice = originalPrice - discountAmount;
            
            subTotal += originalPrice;
            totalDiscount += discountAmount;
            
            String itemName = item.getName();
            if (itemName.length() > 28) {
                itemName = itemName.substring(0, 25) + "...";
            }
            
            sb.append(String.format("%-30s | %-4d | %-12.2f | %-12.2f\n", 
                    itemName, qty, item.getPrice(), finalPrice));
            
            if (discountAmount > 0) {
                sb.append(String.format("  [ Discount applied: %-20s (-%.2f) ]\n", 
                    item.getDiscount().getDescription(), discountAmount));
            }
        }
        
        sb.append("--------------------------------------------------------------------------\n");
        sb.append(String.format("%-52s %.2f\n", "Subtotal:", subTotal));
        sb.append(String.format("%-51s -%.2f\n", "Total Discount:", totalDiscount));
        
        double finalPayable = subTotal - totalDiscount;
        
        if (wantsBag) {
            sb.append(String.format("%-52s +%.2f\n", "Carry Bag:", BAG_CHARGE));
            finalPayable += BAG_CHARGE;
        }

        sb.append("==========================================================================\n");
        sb.append(String.format("%-52s %.2f\n", "FINAL PAYABLE AMOUNT:", finalPayable));
        sb.append("==========================================================================\n");
        sb.append("                   Thank you for shopping with us!                        \n");
        sb.append("==========================================================================\n");
        
        return sb.toString();
    }

    public void generateBill() {
        System.out.print(generateInvoiceString());
    }

    public void saveToFile(String invoice) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bill.txt"))) {
            writer.write(invoice);
        } catch (IOException e) {
            System.err.println("Error saving bill to file: " + e.getMessage());
        }
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Category> categories = initializeInventory();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Supermarket!");
        System.out.println("Please add your desirable items to the list by selecting the category and item number.");
        System.out.println("Type 'finish' at any prompt to stop adding items and proceed to checkout.");

        while (true) {
            System.out.println("\n--- Available Categories ---");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getName());
            }

            System.out.print("Select a category number (or 'finish'): ");
            String catInput = scanner.nextLine().trim();

            if (catInput.equalsIgnoreCase("finish"))
                break;

            int catIndex;
            try {
                catIndex = Integer.parseInt(catInput) - 1;
                if (catIndex < 0 || catIndex >= categories.size()) {
                    System.out.println("Invalid selection. Please try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            Category selectedCat = categories.get(catIndex);
            List<Item> items = selectedCat.getItems();

            System.out.println("\n--- Items in " + selectedCat.getName() + " ---");
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                System.out.printf("%2d. %-30s - %6.2f (%s)\n",
                        (i + 1), item.getName(), item.getPrice(), item.getDiscount().getDescription());
            }

            System.out.print("Select an item number (type 'cancel' to go back, or 'finish'): ");
            String itemInput = scanner.nextLine().trim();

            if (itemInput.equalsIgnoreCase("finish"))
                break;
            if (itemInput.equalsIgnoreCase("cancel"))
                continue;

            int itemIndex;
            try {
                itemIndex = Integer.parseInt(itemInput) - 1;
                if (itemIndex < 0 || itemIndex >= items.size()) {
                    System.out.println("Invalid item selection.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            Item selectedItem = items.get(itemIndex);

            System.out.print("Enter quantity for " + selectedItem.getName() + " (or 'finish'): ");
            String qtyInput = scanner.nextLine().trim();

            if (qtyInput.equalsIgnoreCase("finish"))
                break;

            int quantity;
            try {
                quantity = Integer.parseInt(qtyInput);
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
                continue;
            }

            cart.addItem(selectedItem, quantity);
            System.out.println("=> Added " + quantity + " x " + selectedItem.getName() + " to your cart.");
        }

        // Checkout process
        boolean wantsBag = false;
        if (!cart.isEmpty()) {
            while (true) {
                System.out.print("\nDo you want a carry bag for 15.00? (yes/no): ");
                String bagInput = scanner.nextLine().trim().toLowerCase();
                if (bagInput.equals("yes") || bagInput.equals("y")) {
                    wantsBag = true;
                    break;
                } else if (bagInput.equals("no") || bagInput.equals("n")) {
                    wantsBag = false;
                    break;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            }
        } else {
            System.out.println("Your cart is empty. Checkout aborted.");
        }

        Bill finalBill = new Bill(cart, wantsBag);
        finalBill.generateBill();

        // Step 2: Ask for a saved copy
        String invoice = finalBill.generateInvoiceString();
        System.out.print("\nDo you want a copy of this bill saved? (yes/no): ");
        String saveInput = scanner.nextLine().trim().toLowerCase();
        if (saveInput.equals("yes") || saveInput.equals("y")) {
            finalBill.saveToFile(invoice);
            System.out.println("Bill successfully saved to bill.txt");
        }

        scanner.close();
    }

    private static List<Category> initializeInventory() {
        List<Category> catalog = new ArrayList<>();

        // 1. Fresh Fruits & Vegetables
        Category c1 = new Category("Fresh Fruits & Vegetables");
        c1.addItem(new Item("Organic Bananas (Dole)", 80, new NoDiscount()));
        c1.addItem(new Item("Washington Apples", 250, new PercentageDiscount(10)));
        c1.addItem(new Item("Alphonso Mangoes", 500, new FixedDiscount(50)));
        c1.addItem(new Item("Dragon Fruit", 150, new NoDiscount()));
        c1.addItem(new Item("Avocados (Hass)", 300, new PercentageDiscount(15)));
        c1.addItem(new Item("Baby Spinach", 90, new NoDiscount()));
        c1.addItem(new Item("Broccoli", 120, new NoDiscount()));
        c1.addItem(new Item("Cherry Tomatoes", 100, new FixedDiscount(10)));
        c1.addItem(new Item("Purple Cabbage", 60, new NoDiscount()));
        c1.addItem(new Item("Zucchini", 80, new PercentageDiscount(5)));
        c1.addItem(new Item("Sweet Corn (Del Monte)", 110, new NoDiscount()));
        c1.addItem(new Item("Iceberg Lettuce", 70, new NoDiscount()));
        catalog.add(c1);

        // 2. Dairy & Alternatives
        Category c2 = new Category("Dairy & Alternatives");
        c2.addItem(new Item("Amul Gold Milk", 66, new NoDiscount()));
        c2.addItem(new Item("Nestle A+ Milk", 85, new NoDiscount()));
        c2.addItem(new Item("Mother Dairy Curd", 40, new NoDiscount()));
        c2.addItem(new Item("Epigamia Greek Yogurt", 70, new PercentageDiscount(10)));
        c2.addItem(new Item("Amul Butter", 58, new NoDiscount()));
        c2.addItem(new Item("Britannia Cheese Slices", 140, new PercentageDiscount(5)));
        c2.addItem(new Item("Yakult Probiotic Drink", 80, new NoDiscount()));
        c2.addItem(new Item("Almond Milk", 300, new FixedDiscount(30)));
        c2.addItem(new Item("Soy Milk (Sofit)", 140, new PercentageDiscount(10)));
        c2.addItem(new Item("Oat Milk (Oatly)", 400, new FixedDiscount(50)));
        c2.addItem(new Item("Paneer (Amul)", 90, new NoDiscount()));
        c2.addItem(new Item("Whipping Cream (Tropolite)", 180, new NoDiscount()));
        catalog.add(c2);

        // 3. Bakery & Bread
        Category c3 = new Category("Bakery & Bread");
        c3.addItem(new Item("Britannia Brown Bread", 50, new NoDiscount()));
        c3.addItem(new Item("Harvest Gold White Bread", 40, new NoDiscount()));
        c3.addItem(new Item("Multigrain Bread", 65, new PercentageDiscount(5)));
        c3.addItem(new Item("Croissants (La Folie)", 120, new NoDiscount()));
        c3.addItem(new Item("Garlic Breadsticks", 90, new FixedDiscount(10)));
        c3.addItem(new Item("Donuts (Mad Over Donuts)", 350, new PercentageDiscount(15)));
        c3.addItem(new Item("Muffins", 30, new NoDiscount()));
        c3.addItem(new Item("Burger Buns", 40, new NoDiscount()));
        c3.addItem(new Item("Pizza Base", 50, new NoDiscount()));
        c3.addItem(new Item("Sourdough Bread", 150, new FixedDiscount(20)));
        c3.addItem(new Item("Pita Bread", 80, new NoDiscount()));
        c3.addItem(new Item("Wrap Tortillas (Mission)", 200, new PercentageDiscount(10)));
        catalog.add(c3);

        // 4. Snacks & Namkeen
        Category c4 = new Category("Snacks & Namkeen");
        c4.addItem(new Item("Lay's Chips", 20, new NoDiscount()));
        c4.addItem(new Item("Doritos Nachos", 50, new NoDiscount()));
        c4.addItem(new Item("Kurkure Masala Munch", 20, new NoDiscount()));
        c4.addItem(new Item("Haldiram's Bhujia", 100, new FixedDiscount(10)));
        c4.addItem(new Item("Bingo Mad Angles", 20, new NoDiscount()));
        c4.addItem(new Item("Pringles", 110, new PercentageDiscount(10)));
        c4.addItem(new Item("Too Yumm Chips", 40, new NoDiscount()));
        c4.addItem(new Item("Cornitos Nachos", 90, new PercentageDiscount(5)));
        c4.addItem(new Item("Popcorn (Act II)", 40, new NoDiscount()));
        c4.addItem(new Item("Trail Mix (Nutraj)", 300, new FixedDiscount(50)));
        c4.addItem(new Item("Protein Chips (RiteBite)", 80, new NoDiscount()));
        c4.addItem(new Item("Roasted Makhana (Farmley)", 150, new PercentageDiscount(10)));
        catalog.add(c4);

        // 5. Chocolates & Confectionery
        Category c5 = new Category("Chocolates & Confectionery");
        c5.addItem(new Item("Dairy Milk Silk", 80, new NoDiscount()));
        c5.addItem(new Item("KitKat", 25, new NoDiscount()));
        c5.addItem(new Item("Ferrero Rocher", 450, new PercentageDiscount(15)));
        c5.addItem(new Item("Snickers", 50, new NoDiscount()));
        c5.addItem(new Item("Mars Bar", 60, new NoDiscount()));
        c5.addItem(new Item("Toblerone", 180, new FixedDiscount(20)));
        c5.addItem(new Item("Lindt Excellence", 350, new PercentageDiscount(10)));
        c5.addItem(new Item("Kinder Joy", 45, new NoDiscount()));
        c5.addItem(new Item("M&M's", 150, new PercentageDiscount(5)));
        c5.addItem(new Item("Hershey's Kisses", 140, new NoDiscount()));
        c5.addItem(new Item("Galaxy Chocolate", 80, new NoDiscount()));
        c5.addItem(new Item("Chupa Chups", 10, new NoDiscount()));
        catalog.add(c5);

        // 6. Beverages
        Category c6 = new Category("Beverages");
        c6.addItem(new Item("Coca-Cola", 40, new NoDiscount()));
        c6.addItem(new Item("Pepsi", 40, new NoDiscount()));
        c6.addItem(new Item("Red Bull", 125, new FixedDiscount(10)));
        c6.addItem(new Item("Monster Energy", 110, new PercentageDiscount(5)));
        c6.addItem(new Item("Tropicana Juice", 120, new NoDiscount()));
        c6.addItem(new Item("Real Juice", 110, new NoDiscount()));
        c6.addItem(new Item("Paper Boat Drinks", 35, new NoDiscount()));
        c6.addItem(new Item("Gatorade", 50, new NoDiscount()));
        c6.addItem(new Item("Bisleri Water", 20, new NoDiscount()));
        c6.addItem(new Item("Evian Water", 150, new PercentageDiscount(10)));
        c6.addItem(new Item("Starbucks Cold Coffee", 250, new FixedDiscount(30)));
        c6.addItem(new Item("Nescafe Ready-to-Drink", 70, new NoDiscount()));
        catalog.add(c6);

        // 7. Instant & Packaged Foods
        Category c7 = new Category("Instant & Packaged Foods");
        c7.addItem(new Item("Maggi", 14, new NoDiscount()));
        c7.addItem(new Item("Top Ramen", 15, new NoDiscount()));
        c7.addItem(new Item("Knorr Soups", 60, new PercentageDiscount(10)));
        c7.addItem(new Item("Yippee Noodles", 14, new NoDiscount()));
        c7.addItem(new Item("MTR Meals", 120, new FixedDiscount(15)));
        c7.addItem(new Item("Haldiram's Ready Meals", 150, new PercentageDiscount(5)));
        c7.addItem(new Item("Pasta (Barilla)", 200, new PercentageDiscount(10)));
        c7.addItem(new Item("Instant Poha", 50, new NoDiscount()));
        c7.addItem(new Item("Cup Noodles", 50, new NoDiscount()));
        c7.addItem(new Item("Frozen Momos", 180, new FixedDiscount(20)));
        c7.addItem(new Item("Frozen Fries", 160, new NoDiscount()));
        c7.addItem(new Item("Oats", 150, new PercentageDiscount(5)));
        catalog.add(c7);

        // 8. Staples & Groceries
        Category c8 = new Category("Staples & Groceries");
        c8.addItem(new Item("Aashirvaad Atta", 220, new FixedDiscount(10)));
        c8.addItem(new Item("India Gate Rice", 190, new PercentageDiscount(5)));
        c8.addItem(new Item("Fortune Oil", 140, new NoDiscount()));
        c8.addItem(new Item("Tata Salt", 25, new NoDiscount()));
        c8.addItem(new Item("Toor Dal", 160, new NoDiscount()));
        c8.addItem(new Item("Rajma", 140, new PercentageDiscount(5)));
        c8.addItem(new Item("Chana Dal", 110, new NoDiscount()));
        c8.addItem(new Item("Sugar", 45, new NoDiscount()));
        c8.addItem(new Item("Jaggery", 60, new NoDiscount()));
        c8.addItem(new Item("Besan", 90, new NoDiscount()));
        c8.addItem(new Item("Vermicelli", 30, new NoDiscount()));
        c8.addItem(new Item("Corn Flour", 40, new NoDiscount()));
        catalog.add(c8);

        // 9. Meat, Fish & Eggs
        Category c9 = new Category("Meat, Fish & Eggs");
        c9.addItem(new Item("Chicken Breast", 350, new FixedDiscount(30)));
        c9.addItem(new Item("Mutton", 800, new PercentageDiscount(10)));
        c9.addItem(new Item("Eggs", 80, new NoDiscount()));
        c9.addItem(new Item("Fish Fillet", 450, new FixedDiscount(40)));
        c9.addItem(new Item("Prawns", 600, new PercentageDiscount(15)));
        c9.addItem(new Item("Chicken Sausages", 220, new NoDiscount()));
        c9.addItem(new Item("Salami", 250, new NoDiscount()));
        c9.addItem(new Item("Bacon", 350, new PercentageDiscount(5)));
        c9.addItem(new Item("Turkey Ham", 400, new FixedDiscount(50)));
        c9.addItem(new Item("Nuggets", 180, new PercentageDiscount(10)));
        c9.addItem(new Item("Kebabs", 250, new FixedDiscount(20)));
        c9.addItem(new Item("Fish Fingers", 300, new NoDiscount()));
        catalog.add(c9);

        // 10. Personal Care
        Category c10 = new Category("Personal Care");
        c10.addItem(new Item("Dove Soap", 50, new NoDiscount()));
        c10.addItem(new Item("Lux Body Wash", 150, new PercentageDiscount(10)));
        c10.addItem(new Item("Nivea Lotion", 250, new FixedDiscount(20)));
        c10.addItem(new Item("Vaseline", 100, new NoDiscount()));
        c10.addItem(new Item("Himalaya Face Wash", 130, new PercentageDiscount(5)));
        c10.addItem(new Item("Garnier Cream", 140, new NoDiscount()));
        c10.addItem(new Item("Lakme Sunscreen", 350, new FixedDiscount(40)));
        c10.addItem(new Item("Mamaearth Shampoo", 400, new PercentageDiscount(15)));
        c10.addItem(new Item("Head & Shoulders", 200, new NoDiscount()));
        c10.addItem(new Item("Colgate", 60, new NoDiscount()));
        c10.addItem(new Item("Oral-B Toothbrush", 40, new NoDiscount()));
        c10.addItem(new Item("Gillette Razor", 250, new PercentageDiscount(10)));
        c10.addItem(new Item("Whisper Sanitary Pads", 90, new NoDiscount()));
        c10.addItem(new Item("Dexter Tampons", 120, new NoDiscount()));
        c10.addItem(new Item("Menstrual Cup", 250, new PercentageDiscount(5)));
        catalog.add(c10);

        // 11. Household & Cleaning
        Category c11 = new Category("Household & Cleaning");
        c11.addItem(new Item("Surf Excel", 350, new FixedDiscount(30)));
        c11.addItem(new Item("Ariel Liquid", 220, new PercentageDiscount(10)));
        c11.addItem(new Item("Lizol", 100, new NoDiscount()));
        c11.addItem(new Item("Harpic", 90, new NoDiscount()));
        c11.addItem(new Item("Vim Liquid", 60, new NoDiscount()));
        c11.addItem(new Item("Scotch-Brite", 30, new NoDiscount()));
        c11.addItem(new Item("Colin Cleaner", 90, new PercentageDiscount(5)));
        c11.addItem(new Item("Garbage Bags", 120, new NoDiscount()));
        c11.addItem(new Item("Odonil", 50, new NoDiscount()));
        c11.addItem(new Item("Dettol", 150, new FixedDiscount(15)));
        c11.addItem(new Item("Mop Set", 500, new PercentageDiscount(20)));
        c11.addItem(new Item("Paper Towels", 180, new NoDiscount()));
        catalog.add(c11);

        // 12. Health & Wellness
        Category c12 = new Category("Health & Wellness");
        c12.addItem(new Item("Dabur Honey", 200, new FixedDiscount(20)));
        c12.addItem(new Item("Chyawanprash", 350, new PercentageDiscount(10)));
        c12.addItem(new Item("Protein Powder", 1500, new FixedDiscount(150)));
        c12.addItem(new Item("Multivitamins", 600, new PercentageDiscount(15)));
        c12.addItem(new Item("Omega-3", 800, new PercentageDiscount(10)));
        c12.addItem(new Item("ORS", 20, new NoDiscount()));
        c12.addItem(new Item("Herbal Tea", 150, new NoDiscount()));
        c12.addItem(new Item("Green Tea", 250, new FixedDiscount(30)));
        c12.addItem(new Item("Ashwagandha", 300, new PercentageDiscount(5)));
        c12.addItem(new Item("Electrolytes", 60, new NoDiscount()));
        c12.addItem(new Item("Whey Protein", 2500, new PercentageDiscount(20)));
        c12.addItem(new Item("Energy Bars", 100, new NoDiscount()));
        catalog.add(c12);

        // 13. Pet Supplies
        Category c13 = new Category("Pet Supplies");
        c13.addItem(new Item("Pedigree", 500, new FixedDiscount(50)));
        c13.addItem(new Item("Royal Canin", 1200, new PercentageDiscount(10)));
        c13.addItem(new Item("Whiskas", 450, new NoDiscount()));
        c13.addItem(new Item("Dog Leash", 350, new PercentageDiscount(5)));
        c13.addItem(new Item("Cat Litter", 600, new FixedDiscount(60)));
        c13.addItem(new Item("Pet Shampoo", 250, new NoDiscount()));
        c13.addItem(new Item("Dog Toys", 150, new NoDiscount()));
        c13.addItem(new Item("Bird Feed", 100, new NoDiscount()));
        c13.addItem(new Item("Fish Food", 80, new NoDiscount()));
        c13.addItem(new Item("Pet Bed", 800, new PercentageDiscount(15)));
        c13.addItem(new Item("Treats", 200, new FixedDiscount(20)));
        c13.addItem(new Item("Grooming Kit", 400, new PercentageDiscount(10)));
        catalog.add(c13);

        return catalog;
    }
}

import java.util.*;

class Product {
    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("  %-5d %-20s ₹%.2f", id, name, price);
    }
}

class ShoppingCart {
    private Map<Product, Integer> cart;

    public ShoppingCart() {
        this.cart = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        System.out.println("\u2714 " + quantity + " x " + product.name + " added to the cart!");
    }

    public void removeProduct(Product product, int quantity) {
        if (cart.containsKey(product)) {
            int currentQuantity = cart.get(product);
            if (quantity > currentQuantity) {
                System.out.println("\u26A0 Error: You cannot remove more items than exist in the cart!");
            } else if (quantity == currentQuantity) {
                cart.remove(product);
                System.out.println("\u2714 " + product.name + " completely removed from the cart.");
            } else {
                cart.put(product, currentQuantity - quantity);
                System.out.println("\u2714 Removed " + quantity + " x " + product.name + ". Remaining: " + (currentQuantity - quantity));
            }
        } else {
            System.out.println("\u26A0 Product not found in the cart!");
        }
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("\uD83D\uDED2 Your cart is empty! Start shopping!");
        } else {
            System.out.println("\n\uD83D\uDED2 Your Cart:");
            System.out.println("---------------------------------------");
            System.out.printf("  %-5s %-20s %-10s\n", "ID", "Product", "Quantity");
            System.out.println("---------------------------------------");
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.printf("  %-5d %-20s %-10d\n", product.id, product.name, quantity);
            }
            System.out.println("---------------------------------------");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            total += entry.getKey().price * entry.getValue();
        }
        return total;
    }
}

public class ShoppingCartSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample product catalog in Indian Rupees
        List<Product> products = Arrays.asList(
                new Product(1, "Laptop", 65000.0),
                new Product(2, "Smartphone", 30000.0),
                new Product(3, "Headphones", 2000.0),
                new Product(4, "Keyboard", 1200.0)
        );

        ShoppingCart cart = new ShoppingCart();
        boolean exit = false;
        System.out.println("\n---------------------------------------");
        System.out.println("🛒 Welcome to the Grab Go Cart !");
        System.out.println("---------------------------------------");
        while (!exit) {
            System.out.println("What do you want to do?");
            System.out.println("---------------------------------------");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.println("---------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // View Products
                    System.out.println("\n📦 Available Products:");
                    System.out.println("---------------------------------------");
                    System.out.printf("  %-5s %-20s %-10s\n", "ID", "Product", "Price (₹)");
                    System.out.println("---------------------------------------");
                    for (Product product : products) {
                        System.out.println(product);
                    }
                    System.out.println("---------------------------------------");
                    break;

                case 2: // Add Product to Cart
                    System.out.print("\nEnter Product ID to add to cart: ");
                    int addId = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    Product addProduct = findProductById(products, addId);
                    if (addProduct != null) {
                        cart.addProduct(addProduct, quantity);
                    } else {
                        System.out.println("\u26A0 Product not found!");
                    }
                    break;

                case 3: // Remove Product from Cart
                    System.out.print("\nEnter Product ID to remove from cart: ");
                    int removeId = scanner.nextInt();
                    Product removeProduct = findProductById(products, removeId);
                    if (removeProduct != null) {
                        System.out.print("Enter quantity to remove: ");
                        int removeQuantity = scanner.nextInt();
                        cart.removeProduct(removeProduct, removeQuantity);
                    } else {
                        System.out.println("\u26A0 Product not found!");
                    }
                    break;

                case 4: // View Cart
                    cart.viewCart();
                    break;

                case 5: // Checkout
                    System.out.println("\n🛍 Checkout:");
                    System.out.printf("Your total is: ₹%.2f\n", cart.calculateTotal());
                    System.out.println("🎉 Thank you for shopping with us!");
                    cart = new ShoppingCart(); // Reset cart for next session
                    break;

                case 6: // Exit
                    exit = true;
                    System.out.println("👋 Exiting... Have a great day!");
                    break;

                default:
                    System.out.println("\u26A0 Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static Product findProductById(List<Product> products, int id) {
        for (Product product : products) {
            if (product.id == id) {
                return product;
            }
        }
        return null;
    }
}
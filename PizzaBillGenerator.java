
import java.util.*;

abstract class Pizza {
    protected int basePrice;
    protected boolean extraCheese;
    protected boolean extraToppings;
    protected static final int CHEESE_PRICE = 100;
    protected static final int TOPPINGS_PRICE = 150;
    protected static final int TAKEAWAY_PRICE = 20;

    public Pizza(int basePrice) {
        this.basePrice = basePrice;
    }

    public void addExtraCheese() {
        this.extraCheese = true;
    }

    public void addExtraToppings() {
        this.extraToppings = true;
    }

    public int calculateTotalPrice(boolean takeAway) {
        int total = basePrice;
        if (extraCheese) total += CHEESE_PRICE;
        if (extraToppings) total += TOPPINGS_PRICE;
        if (takeAway) total += TAKEAWAY_PRICE;
        return total;
    }

    public void displayCostBreakdown(boolean takeAway) {
        System.out.println("Base Price: Rs." + basePrice);
        if (extraCheese) {
            System.out.println("Extra Cheese: Rs." + CHEESE_PRICE);
        }
        if (extraToppings) {
            System.out.println("Extra Toppings: Rs." + TOPPINGS_PRICE);
        }
        if (takeAway) {
            System.out.println("Takeaway Packaging: Rs." + TAKEAWAY_PRICE);
        }
        System.out.println("Total: Rs." + calculateTotalPrice(takeAway));
        System.out.println();
    }

    public abstract void display();
}

class VegPizza extends Pizza {
    public VegPizza() {
        super(300);
    }

    public void display() {
        System.out.println("Veg Pizza");
    }
}

class NonVegPizza extends Pizza {
    public NonVegPizza() {
        super(400);
    }

    public void display() {
        System.out.println("Non-Veg Pizza");
    }
}

class DeluxVegPizza extends Pizza {
    public DeluxVegPizza() {
        super(550);
        this.extraCheese = true;
        this.extraToppings = true;
    }

    public void display() {
        System.out.println("Delux Veg Pizza (Includes extra cheese and toppings)");
    }
}

class DeluxNonVegPizza extends Pizza {
    public DeluxNonVegPizza() {
        super(650);
        this.extraCheese = true;
        this.extraToppings = true;
    }

    public void display() {
        System.out.println("Delux Non-Veg Pizza (Includes extra cheese and toppings)");
    }
}

public class PizzaBillGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Pizza> order = new ArrayList<>();
        boolean continueOrdering = true;

        while (continueOrdering) {
            Pizza[] pizzas = {
                new VegPizza(),
                new NonVegPizza(),
                new DeluxVegPizza(),
                new DeluxNonVegPizza()
            };

            System.out.println("---------Welcome to Pizzamania!---------");
            System.out.println("Please select a type of pizza:");
            System.out.println("1. Veg Pizza            - Rs. 300/-");
            System.out.println("2. Non-Veg Pizza        - Rs. 400/-");
            System.out.println("3. Delux Veg Pizza      - Rs. 550/-");
            System.out.println("4. Delux Non-Veg Pizza  - Rs. 650/-");
            System.out.print("Your Choice: ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > pizzas.length) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            Pizza selectedPizza = pizzas[choice - 1];

            if (selectedPizza instanceof VegPizza || selectedPizza instanceof NonVegPizza) {
                System.out.println("Would you like extra cheese? (yes/no)");
                String cheeseChoice = scanner.next();
                if (cheeseChoice.equalsIgnoreCase("yes")) {
                    selectedPizza.addExtraCheese();
                }

                System.out.println("Would you like extra toppings? (yes/no)");
                String toppingsChoice = scanner.next();
                if (toppingsChoice.equalsIgnoreCase("yes")) {
                    selectedPizza.addExtraToppings();
                }
            }

            order.add(selectedPizza);

            System.out.println("Would you like to order another pizza? (yes/no)");
            String anotherPizza = scanner.next();
            if (!anotherPizza.equalsIgnoreCase("yes")) {
                continueOrdering = false;
            }
        }

        boolean reviewOrder = true;

        while (reviewOrder) {
            System.out.println("\nYour current order:");
            for (int i = 0; i < order.size(); i++) {
                System.out.print((i + 1) + ". ");
                order.get(i).display();
            }

            System.out.println("Would you like to remove any item? (yes/no)");
            String removeChoice = scanner.next();
            if (removeChoice.equalsIgnoreCase("yes")) {
                System.out.println("Enter the item number you want to remove:");
                int removeIndex = scanner.nextInt() - 1;
                if (removeIndex >= 0 && removeIndex < order.size()) {
                    order.remove(removeIndex);
                    System.out.println("Item removed.");
                } else {
                    System.out.println("Invalid item number.");
                }
            } else {
                reviewOrder = false;
            }
        }

        System.out.println("Would you like to opt for takeaway packaging? (yes/no)");
        String takeAwayChoice = scanner.next();
        boolean takeAway = takeAwayChoice.equalsIgnoreCase("yes");

        System.out.println("\nGenerating your bill...\n");

        int totalBill = 0;
        for (Pizza pizza : order) {
            pizza.display();
            pizza.displayCostBreakdown(takeAway);
            totalBill += pizza.calculateTotalPrice(takeAway);
        }

        System.out.println("Total Bill: Rs. " + totalBill);

        System.out.println("---------ThankYou!Visit Again---------");
    }
}

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Air Mineral", 3000, 0));
        menuItems.add(new MenuItem("Cocal Cola", 5000, 0));
        menuItems.add(new MenuItem("Sprite", 5000, 0));
        menuItems.add(new MenuItem("Cappucino", 15000, 0));
        menuItems.add(new MenuItem("Kue", 10000, 0));
        menuItems.add(new MenuItem("Keripik", 8000, 0));
        menuItems.add(new MenuItem("Mie Goreng", 8000, 0));
        menuItems.add(new MenuItem("Telur", 8000, 0));
    }

    public void displayMenu() {
        int index = 1;
        for (MenuItem item : menuItems) {
            System.out.print(index + ". " + item.getName() + " - Rp. " + item.getPrice());
            if (item.getDiscount() > 0) {
                System.out.print(" (Diskon " + item.getDiscount() + "%)");
            }
            System.out.println();
            index++;
        }
    }

    public MenuItem getMenuItem(int choice) {
        if (choice >= 1 && choice <= menuItems.size()) {
            return menuItems.get(choice - 1);
        }
        return null;
    }
}

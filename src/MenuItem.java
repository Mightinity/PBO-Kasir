public class MenuItem {
    private String name;
    private int price;
    private int discount;

    public MenuItem(String name, int price, int discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public int getDiscountedPrice() {
        return price - (price * discount / 100);
    }
}

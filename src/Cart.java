import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<MenuItem, Integer> items;
    private int total;

    public Cart() {
        items = new HashMap<>();
        total = 0;
    }

    public void addToCart(MenuItem item) {
        int itemCount = items.getOrDefault(item, 0);
        items.put(item, itemCount + 1);
        total += item.getDiscountedPrice();
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }

    public void displayCart() {
        System.out.println("----- Cart -----");
        int index = 1;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int itemCount = entry.getValue();
            System.out.println(index + ". " + item.getName() + " - Rp. " + item.getPrice() + " ( " + itemCount + " Items )");
            index++;
        }
        System.out.println();
    }

    public JsonObject toJson() {
        JsonObject cartJson = new JsonObject();
        JsonArray itemsJson = new JsonArray();
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int itemCount = entry.getValue();
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("name", item.getName());
            itemJson.addProperty("price", item.getPrice());
            itemJson.addProperty("count", itemCount);
            itemsJson.add(itemJson);
        }
        cartJson.add("items", itemsJson);
        cartJson.addProperty("total", total);
        return cartJson;
    }
}

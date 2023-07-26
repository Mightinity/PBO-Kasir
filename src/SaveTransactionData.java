// File: saveTransactionData.java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class SaveTransactionData {
    public static void saveTransactionData(Payment payment, Cart cart, String fileName) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject transactionJson = new JsonObject();
            transactionJson.addProperty("kode", fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf(".")));
            transactionJson.addProperty("time", getTimestamp());

            JsonObject customerJson = new JsonObject();
            customerJson.addProperty("nama", payment.getName());
            customerJson.addProperty("email", payment.getEmail());
            customerJson.addProperty("nohp", payment.getPhoneNumber());
            transactionJson.add("customer", customerJson);

            JsonArray itemsJson = new JsonArray();
            for (MenuItem item : cart.getItems().keySet()) {
                JsonObject itemJson = new JsonObject();
                itemJson.addProperty("name", item.getName());
                itemJson.addProperty("price", item.getPrice());
                itemJson.addProperty("count", cart.getItems().get(item));
                itemsJson.add(itemJson);
            }
            transactionJson.add("items", itemsJson);

            String json = gson.toJson(transactionJson);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(0);
        return formatter.format(date);
    }
}

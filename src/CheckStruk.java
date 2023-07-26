// File: checkStruk.java
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CheckStruk {
    public static void checkStruk(String transactionFolder) {
        // final String TRANSACTION_FOLDER = "allTransaksi";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan kode transaksi: ");
        String transactionCode = scanner.nextLine().trim();
        String fileName = transactionFolder + File.separator + transactionCode + ".json";
        File transactionFile = new File(fileName);
        if (transactionFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
                JsonObject transactionJson = new Gson().fromJson(reader, JsonObject.class);
                reader.close();

                String name = transactionJson.getAsJsonObject("customer").get("nama").getAsString();
                String email = transactionJson.getAsJsonObject("customer").get("email").getAsString();
                String phoneNumber = transactionJson.getAsJsonObject("customer").get("nohp").getAsString();
                JsonArray itemsJson = transactionJson.getAsJsonArray("items");
    
                System.out.println("\n====== Struk ======");
                System.out.println("Kode: " + transactionCode);
                System.out.println("Time: " + transactionJson.get("time").getAsString());
                System.out.println("----- Data Customer -----");
                System.out.println("Nama: " + name);
                System.out.println("Email: " + email);
                System.out.println("No. Hp: " + phoneNumber);
                System.out.println("----- Item -----");
                for (int i = 0; i < itemsJson.size(); i++) {
                    JsonObject itemJson = itemsJson.get(i).getAsJsonObject();
                    String itemName = itemJson.get("name").getAsString();
                    int itemPrice = itemJson.get("price").getAsInt();
                    int itemCount = itemJson.get("count").getAsInt();
                    System.out.println(itemName + " - Rp. " + itemPrice + " ( " + itemCount + " Items )");
                }
                System.out.println("===================");
                checkStruk(transactionFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nTransaksi dengan kode " + transactionCode + " tidak ditemukan.");
        }
        scanner.close();
    }
}

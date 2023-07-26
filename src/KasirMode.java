// File: KasirMode.java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class KasirMode {
    public static void handlePayment(Cart cart, String customerFolder, String transactionFolder) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Menu Pembayaran =====");
        cart.displayCart();
        System.out.println("Enter untuk melanjutkan...");
        scanner.nextLine();
        int total = cart.getTotal();
        System.out.println("Total belanja: Rp. " + total);
        System.out.print("Masukkan nama: ");
        String name = scanner.nextLine().trim();
        String fileName = customerFolder + File.separator + name.replace(" ", "_") + ".json";
        File customerFile = new File(fileName);
        boolean isExistingCustomer = customerFile.exists();

        String email = "";
        String phoneNumber = "";
        int transactionCount = 0;

        if (isExistingCustomer) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(customerFile));
                JsonObject customerJson = new Gson().fromJson(reader, JsonObject.class);
                email = customerJson.get("email").getAsString();
                phoneNumber = customerJson.get("phoneNumber").getAsString();
                transactionCount = customerJson.get("transactionCount").getAsInt();
                reader.close();

                System.out.println("Email: " + email);
                System.out.println("Nomor HP: " + phoneNumber);
                System.out.println("Total transaksi sebelumnya: " + transactionCount);

                if (transactionCount >= 5) {
                    total -= (total * 0.5); // Apply 50% discount
                    System.out.println("Diskon 50% telah diterapkan! Harga terbaru adalah " + total);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("Masukkan email: ");
            email = scanner.nextLine();
            System.out.print("Masukkan Nomor hp: ");
            phoneNumber = scanner.nextLine();
        }

        System.out.print("Masukkan uang tunai: ");
        int cash = scanner.nextInt();

        if (cash < total) {
            System.out.println("Maaf, uang tunai yang Anda masukkan kurang dari total belanja.");
        } else {
            Payment payment = new Payment(name, email, phoneNumber, cash, transactionCount);
            String transactionCode = generateTransactionCode();
            payment.printReceipt(cart.getItems(), transactionCode);

            String transactionFileName = transactionFolder + File.separator + transactionCode + ".json";
            saveTransactionData(payment, cart, transactionFileName);

            transactionCount++;
            JsonObject customerJson = new JsonObject();
            customerJson.addProperty("name", name);
            customerJson.addProperty("email", email);
            customerJson.addProperty("phoneNumber", phoneNumber);
            customerJson.addProperty("transactionCount", transactionCount);
            saveCustomerData(customerJson, fileName);
        }
        scanner.close();
    }

    public static void saveCustomerData(JsonObject customerJson, String fileName) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(customerJson);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static String generateTransactionCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 12;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            char randomChar = characters.charAt(randomIndex);
            code.append(randomChar);
        }
        return code.toString();
    }

    public static String getTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}

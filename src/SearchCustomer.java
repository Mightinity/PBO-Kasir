import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SearchCustomer {
    public void searchCustomer() {
        final String CUSTOMER_FOLDER = "allCustomer";
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nFitur Search Customer");
        System.out.print("Masukkan nama pelanggan: ");
        String name = scanner.nextLine().trim();
        String fileName = CUSTOMER_FOLDER + File.separator + name + ".json";
        File customerFile = new File(fileName);
        if (customerFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(customerFile));
                JsonObject customerJson = new Gson().fromJson(reader, JsonObject.class);
                String email = customerJson.get("email").getAsString();
                String phoneNumber = customerJson.get("phoneNumber").getAsString();
                int transactionCount = customerJson.get("transactionCount").getAsInt();
                reader.close();

                System.out.println("\nInformasi Pelanggan:");
                System.out.println("Nama: " + name);
                System.out.println("Email: " + email);
                System.out.println("Nomor HP: " + phoneNumber);
                System.out.println("Total Transaksi: " + transactionCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\nPelanggan dengan nama " + name + " tidak ditemukan.");
        }
        scanner.close();
    }
}

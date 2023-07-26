// File: Payment.java (tidak ada perubahan)
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Payment {
    private String name;
    private String email;
    private String phoneNumber;
    private int cash;

    public Payment(String name, String email, String phoneNumber, int cash, int transactionCount) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cash = cash;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void printReceipt(Map<MenuItem, Integer> items, String transactionCode) {
        System.out.println("\n===== Struk Pembayaran =====");
        System.out.println("Kode: " + transactionCode);
        System.out.println("Time: " + getTimestamp());
        System.out.println();
        System.out.println("Nama: " + name);
        System.out.println("Email: " + email);
        System.out.println("No. Hp: " + phoneNumber);
        System.out.println("Uang tunai: " + cash);

        System.out.println("\nDetail Pembelian:");
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int count = entry.getValue();
            System.out.println(item.getName() + " - Rp. " + item.getPrice() + " ( " + count + " Items )");
        }

        System.out.println("---------------------------");
        System.out.println("Total: " + getTotal(items));
        System.out.println("Kembalian: " + (cash - getTotal(items)));
        System.out.println("===========================");
    }

    private int getTotal(Map<MenuItem, Integer> items) {
        int total = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int count = entry.getValue();
            total += item.getPrice() * count;
        }
        return total;
    }
    public static String getTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}


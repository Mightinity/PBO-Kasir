import java.io.File;
import java.util.Scanner;

public class Main {
    private static final String TRANSACTION_FOLDER = "allTransaksi";
    private static final String CUSTOMER_FOLDER = "allCustomer";

    public static void main(String[] args) {
        createDirectoryIfNotExists(TRANSACTION_FOLDER);
        createDirectoryIfNotExists(CUSTOMER_FOLDER);
        Cart cart = new Cart();
        SearchCustomer searchCustomer = new SearchCustomer();
        Menu menu = new Menu();

        Scanner scanner = new Scanner(System.in);

        int choice;
        boolean isKasirMode = false;
        do {
            if (!isKasirMode) {
                System.out.println("Pilih menu:");
                System.out.println("1. Mode Kasir");
                System.out.println("2. Check Struk");
                System.out.println("3. Check Customer");
                System.out.println("0. Selesai");
            } else {
                System.out.println("Pilih makanan:");
                menu.displayMenu();
                System.out.println("");
                System.out.println("99. Check Cart");
                System.out.println("98. Menu Pembayaran");
            }
            
            System.out.print("Masukkan nomor: ");
            choice = scanner.nextInt();

            if (isKasirMode) {
                if (choice == 99) {
                    cart.displayCart();
                    continue;
                }

                MenuItem item = menu.getMenuItem(choice);
                if (item != null) {
                    cart.addToCart(item);
                    System.out.println(item.getName() + " berhasil dimasukkan ke keranjang");
                } else if (choice == 98) {
                    continue;
                } else {
                    System.out.println("Menu tidak valid");
                }
            } else {
                switch (choice) {
                    case 1:
                        isKasirMode = true;
                        break;
                    case 2:
                        CheckStruk.checkStruk(TRANSACTION_FOLDER);
                        break;
                    case 3:
                        searchCustomer.searchCustomer();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Pilihan tidak valid");
                        break;
                }
            }
        } while (choice != 98);

        if (isKasirMode) {
            KasirMode.handlePayment(cart, CUSTOMER_FOLDER, TRANSACTION_FOLDER);
        }
        scanner.close();
    }

    public static void createDirectoryIfNotExists(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (created) {
                System.out.println("Directory created: " + directoryName);
            } else {
                System.out.println("Failed to create directory: " + directoryName);
            }
        }
    }
}

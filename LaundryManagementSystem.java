import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class LaundryItem {
    int id;
    String customerName;
    String itemType;
    double weight;
    double price;
    String status;

    LaundryItem(int id, String customerName, String itemType, double weight, double price, String status) {
        this.id = id;
        this.customerName = customerName;
        this.itemType = itemType;
        this.weight = weight;
        this.price = price;
        this.status = status;
    }
}

class LaundrySystem {
    private ArrayList<User> users;
    private ArrayList<LaundryItem> laundryItems;
    private User currentUser;
    private Scanner scanner;
    private int nextId;

    LaundrySystem() {
        users = new ArrayList<>();
        laundryItems = new ArrayList<>();
        scanner = new Scanner(System.in);
        nextId = 1;

        // Tambahkan user default
        users.add(new User("admin", "admin123"));
    }

    public void run() {
        while (true) {
            if (currentUser == null) {
                login();
            } else {
                showMenu();
            }
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                currentUser = user;
                System.out.println("Login berhasil!");
                return;
            }
        }
        System.out.println("Username atau password salah.");
    }

    private void logout() {
        currentUser = null;
        System.out.println("Logout berhasil.");
    }

    private void showMenu() {
        System.out.println("\n=== SISTEM KASIR LAUNDRY ===");
        System.out.println("1. Tambah Item Laundry");
        System.out.println("2. Lihat Semua Item");
        System.out.println("3. Update Status Item");
        System.out.println("4. Hapus Item");
        System.out.println("5. Logout");
        System.out.print("Pilih menu (1-5): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // konsumsi newline

        switch (choice) {
            case 1:
                addLaundryItem();
                break;
            case 2:
                viewLaundryItems();
                break;
            case 3:
                updateItemStatus();
                break;
            case 4:
                deleteItem();
                break;
            case 5:
                logout();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private void addLaundryItem() {
        System.out.print("Nama Pelanggan: ");
        String customerName = scanner.nextLine();

        System.out.println("Pilih jenis layanan: ");
        System.out.println("1. Cuci saja (Rp 6000 per kg)");
        System.out.println("2. Cuci kering (Rp 10000 per kg)");
        System.out.println("3. Cuci setrika (Rp 9000 per kg)");
        System.out.println("4. Setrika saja (Rp 4000 per kg)");
        System.out.print("Pilihan (1-4): ");
        int serviceChoice = scanner.nextInt();
        scanner.nextLine(); // konsumsi newline

        String itemType;
        double pricePerKg;
        switch (serviceChoice) {
            case 1:
                itemType = "Cuci saja";
                pricePerKg = 6000;
                break;
            case 2:
                itemType = "Cuci kering";
                pricePerKg = 10000;
                break;
            case 3:
                itemType = "Cuci setrika";
                pricePerKg = 9000;
                break;
            case 4:
                itemType = "Setrika saja";
                pricePerKg = 4000;
                break;
            default:
                System.out.println("Pilihan tidak valid. Gunakan 'Cuci saja' sebagai default.");
                itemType = "Cuci saja";
                pricePerKg = 6000;
        }

        System.out.print("Berat (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // konsumsi newline

        double totalPrice = weight * pricePerKg;
        LaundryItem item = new LaundryItem(nextId++, customerName, itemType, weight, totalPrice, "Proses");
        laundryItems.add(item);

        System.out.println("Item laundry berhasil ditambahkan.");
        System.out.printf("Total harga: Rp %.2f\n", totalPrice);

        // Tambahan interaktif
        while (true) {
            System.out.println("\nApa yang ingin Anda lakukan selanjutnya?");
            System.out.println("1. Ubah status item ini");
            System.out.println("2. Lihat semua item laundry");
            System.out.println("3. Kembali ke menu utama");
            System.out.print("Pilihan (1-3): ");
            int followUp = scanner.nextInt();
            scanner.nextLine();

            if (followUp == 1) {
                System.out.print("Masukkan status baru (Proses/Selesai): ");
                String newStatus = scanner.nextLine();
                item.status = newStatus;
                System.out.println("Status item berhasil diupdate.");
            } else if (followUp == 2) {
                viewLaundryItems();
            } else if (followUp == 3) {
                return;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void viewLaundryItems() {
        if (laundryItems.isEmpty()) {
            System.out.println("Tidak ada item laundry.");
        } else {
            System.out.println("\n=== Daftar Item Laundry ===");
            for (LaundryItem item : laundryItems) {
                System.out.printf("ID: %d, Pelanggan: %s, Layanan: %s, Berat: %.2f kg, Harga: Rp%.2f, Status: %s\n",
                        item.id, item.customerName, item.itemType, item.weight, item.price, item.status);
            }
        }
    }

    private void updateItemStatus() {
        if (laundryItems.isEmpty()) {
            System.out.println("Belum ada item laundry.");
            return;
        }

        System.out.print("Masukkan ID item yang ingin diubah statusnya: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // konsumsi newline

        for (LaundryItem item : laundryItems) {
            if (item.id == id) {
                System.out.print("Status baru (Proses/Selesai): ");
                String newStatus = scanner.nextLine();
                item.status = newStatus;
                System.out.println("Status item berhasil diupdate.");
                return;
            }
        }

        System.out.print("Item dengan ID tersebut tidak ditemukan. ID tersedia: ");
        for (LaundryItem i : laundryItems) {
            System.out.print(i.id + " ");
        }
        System.out.println();
    }

    private void deleteItem() {
        if (laundryItems.isEmpty()) {
            System.out.println("Belum ada item laundry.");
            return;
        }

        System.out.print("Masukkan ID item yang akan dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // konsumsi newline

        for (Iterator<LaundryItem> iterator = laundryItems.iterator(); iterator.hasNext();) {
            LaundryItem item = iterator.next();
            if (item.id == id) {
                iterator.remove();
                System.out.println("Item berhasil dihapus.");
                return;
            }
        }

        System.out.print("Item dengan ID tersebut tidak ditemukan. ID tersedia: ");
        for (LaundryItem i : laundryItems) {
            System.out.print(i.id + " ");
        }
        System.out.println();
    }
}

public class LaundryManagementSystem {
    public static void main(String[] args) {
        LaundrySystem system = new LaundrySystem();
        system.run();
    }
}


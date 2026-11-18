package shop.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import shop.StudentInfo;
import shop.exception.CatalogLoadException;
import shop.exception.InsufficientBalanceException;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.io.CatalogReader;
import shop.model.Product;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.RegularShipping;
import shop.shipping.ShippingService;

public class ShopApp {

    public static void main(String[] args) {
        List<Product> catalog;
        try {
            catalog = new CatalogReader().load(Path.of("katalog.csv"));
        } catch (CatalogLoadException e) {
            System.out.println("Toko tidak bisa dibuka");
            return;
        }

        System.out.println("== Toko Online UMKM ==");
        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i).getInfo());
        }

        List<Product> cart = new ArrayList<>();
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Nomor produk (0 = selesai): ");
                int choice;
                try {
                    choice = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException _) {
                    System.out.println(" Input harus berupa angka.");
                    continue;
                }

                if (choice == 0) {
                    break;
                }
                if (choice < 1 || choice > catalog.size()) {
                    System.out.println(" Nomor produk tidak ada.");
                    continue;
                }
                Product product = catalog.get(choice - 1);

                System.out.print("Jumlah: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException _) {
                    System.out.println(" Input harus berupa angka.");
                    continue;
                }
                if (quantity <= 0) {
                    System.out.println(" Jumlah harus lebih dari 0.");
                    continue;
                }
                for (int i = 0; i < quantity; i++) {
                    cart.add(product);
                }
                System.out.println("  + " + quantity + " x " + product.getName());
            }
        }

        CheckoutService checkout = new CheckoutService();
        ShippingService regular = new RegularShipping();
        EWalletPayment wallet = new EWalletPayment("DompetKu", StudentInfo.walletBalance());

        if (cart.isEmpty()) {
            System.out.println("\nKeranjang: 0 barang, total Reguler Rp0");
            System.out.println("Pembayaran berhasil? false");
            System.out.println("Sisa saldo: Rp" + wallet.getBalance());
            return;
        }

        System.out.println("\nKeranjang: " + cart.size() + " barang, total Reguler Rp"
                + checkout.total(cart, regular));
        try {
            long paidAmount = checkout.checkout(cart, regular, wallet);
            System.out.println("Pembayaran berhasil? " + (paidAmount > 0));
            System.out.println("Sisa saldo: Rp" + wallet.getBalance());
        } catch (OutOfStockException e) {
            System.out.println(" Stok tidak cukup: " + e.getSku());
        } catch (InsufficientBalanceException e) {
            System.out.println(" Saldo tidak cukup: kurang Rp" + e.getShortfall());
        } catch (PaymentFailedException e) {
            System.out.println(" Pembayaran gagal: " + e.getMessage());
        }
    }

    public static String buy(CheckoutService service, List<Product> cart, ShippingService shipping,
            PaymentMethod payment) {
        try {
            long total = service.checkout(cart, shipping, payment);
            return "[OK] Pembayaran berhasil. Total Rp" + total;
        } catch (OutOfStockException e) {
            return "[STOK] Stok " + e.getSku() + " kurang; tersedia " + e.getAvailable()
                    + ", diminta " + e.getRequested();
        } catch (InsufficientBalanceException e) {
            return "[SALDO] Saldo kurang Rp" + e.getShortfall();
        } catch (PaymentFailedException e) {
            return "[BAYAR] " + e.getMessage();
        }
    }
}

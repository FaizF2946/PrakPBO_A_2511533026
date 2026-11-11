package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.Address;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.*;
import shop.payment.*;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.RegularShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {
        Product coffee3026 = new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300);
        Product rendang3026 = new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400);
        Product ebook3026 = new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000, "https://toko-umkm.example/unduh/ebk-003");
        List<Product> cart3026 = List.of(coffee3026, rendang3026, ebook3026);

        CheckoutService checkout3026 = new CheckoutService();
        ShippingService express3026 = new ExpressShipping();

        PaymentMethod wallet3026 = new EWalletPayment("Dompetku", 300_000);
        PaymentMethod transfer3026 = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card3026 = new CreditCardPayment("Siti Rahma", 1_000_000);

        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");
        System.out.println("== T1: Total dengan biaya admin (Ekspres) ==");
        System.out.println(wallet3026.getName() + " Rp" + checkout3026.grandTotal(cart3026, express3026, wallet3026) + " (admin Rp" + wallet3026.adminFee(checkout3026.total(cart3026, express3026)) + ")");
        System.out.println(transfer3026.getName() + " Rp" + checkout3026.grandTotal(cart3026, express3026, transfer3026) + " (admin Rp" + transfer3026.adminFee(checkout3026.total(cart3026, express3026)) + ")");
        System.out.println(card3026.getName() + ": Rp" + checkout3026.grandTotal(cart3026, express3026, card3026) + " (admin Rp" + card3026.adminFee(checkout3026.total(cart3026, express3026)) + ")");

        System.out.println("Kebijakan kartu kredit\n" + card3026.policy());
        System.out.println("Kebijakan e-wallet : " + ((RefundablePayment) wallet3026).policy());

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        NotificationService service3026 = new NotificationService();
        service3026.register(new EmailChannel());
        service3026.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        service3026.register(new WhatsAppChannel());
        
        service3026.register(new NotificationChannel() {
            @Override
            public String channelName() {
                return "LOG";
            }

            @Override
            public boolean send(CustomerContact contact3026, String message3026) {
                System.out.println("[" + channelName() + "] " + contact3026.getName() + ": " + message3026);
                return true;
            }
        });

        CustomerContact siti3026 = new CustomerContact("Siti Rahma", "siti@example.com", "081234567890");
        CustomerContact budi3026 = new CustomerContact("Budi", "", "+6281298765432");

        String longMsg3026 = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. Nomor resi akan muncul di aplikasi dalam 1x24 jam. Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami!";

        int sentSiti3026 = service3026.broadcast(siti3026, longMsg3026);
        System.out.println("Terkirim ke Siti lewat " + sentSiti3026 + " kanal");

        int sentBudi3026 = service3026.broadcast(budi3026, longMsg3026);
        System.out.println("Terkirim ke Budi lewat " + sentBudi3026 + " kanal");
    }
}

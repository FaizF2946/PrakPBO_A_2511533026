package review;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import shop.exception.CatalogLoadException;
import shop.exception.PaymentFailedException;
import shop.payment.PaymentMethod;

public final class FragileShop {
    private FragileShop() {}

    public static void payOrder(PaymentMethod payment, long amount) throws PaymentFailedException {
        if (payment == null) {
            throw new IllegalArgumentException("Payment method must not be null");
        }
        payment.pay(amount);
    }

    public static String readCatalogHeader(Path path) throws CatalogLoadException {
        try {
            return Files.readAllLines(path).getFirst();
        } catch (IOException e) {
            throw new CatalogLoadException("Gagal membaca header katalog " + path, e);
        }
    }
}

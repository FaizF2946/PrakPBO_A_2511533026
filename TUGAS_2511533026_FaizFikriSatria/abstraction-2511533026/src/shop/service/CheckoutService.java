package shop.service;

import java.util.List;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {
    public long subtotal(List<Product> items3026) {
        long total3026 = 0;
        for (Product product3026 : items3026) {
            total3026 += product3026.getFinalPrice();
        }
        return total3026;
    }

    public int totalWeight(List<Product> items3026) {
        int grams3026 = 0;
        for (Product product3026 : items3026) {
            if (product3026 instanceof Shippable shippable3026) {
                grams3026 += shippable3026.getWeightGrams();
            }
        }
        return grams3026;
    }

    public long total(List<Product> items3026, ShippingService shipping3026) {
        return subtotal(items3026) + shipping3026.calculateCost(totalWeight(items3026));
    }

    public long grandTotal(List<Product> items3026, ShippingService shipping3026, PaymentMethod payment3026) {
        long baseTotal3026 = total(items3026, shipping3026);
        return baseTotal3026 + payment3026.adminFee(baseTotal3026);
    }

    public boolean checkout(List<Product> items3026, ShippingService shipping3026, PaymentMethod payment3026) {
        if (items3026 == null || items3026.isEmpty()) {
            return false;
        }
        long finalCost3026 = grandTotal(items3026, shipping3026, payment3026);
        return payment3026.pay(finalCost3026);
    }

    public boolean refund(RefundablePayment payment3026, long amount3026) {
        if (payment3026 == null) {
            return false;
        }
        return payment3026.refund(amount3026);
    }
}

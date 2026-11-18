package shop.service;

import java.util.List;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {
    private final Inventory inventory;

    public CheckoutService() {
        this(new Inventory());
    }

    public CheckoutService(Inventory inventory) {
        this.inventory = inventory == null ? new Inventory() : inventory;
    }

    public long subtotal(List<Product> items) {
        long total = 0;
        for (Product product : items) {
            total += product.getFinalPrice();
        }
        return total;
    }

    public int totalWeight(List<Product> items) {
        int grams = 0;
        for (Product product : items) {
            if (product instanceof Shippable shippable) { // a capability, not a class
                grams += shippable.getWeightGrams();
            }
        }
        return grams;
    }

    public long total(List<Product> items, ShippingService shipping) {
        return subtotal(items) + shipping.calculateCost(totalWeight(items));
    }

    /** @return false when the cart is empty or the payment fails (but why?) */
    public long checkout(List<Product> items, ShippingService shipping, PaymentMethod payment)
            throws OutOfStockException, PaymentFailedException {
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        java.util.Map<String, Integer> reserved = new java.util.HashMap<>();
        try {
            for (Product product : items) {
                inventory.reserve(product.getSku(), 1);
                reserved.merge(product.getSku(), 1, Integer::sum);
            }

            long amount = total(items, shipping);
            payment.pay(amount);
            return amount;
        } catch (OutOfStockException e) {
            for (var entry : reserved.entrySet()) {
                inventory.release(entry.getKey(), entry.getValue());
            }
            throw e;
        } catch (PaymentFailedException e) {
            for (var entry : reserved.entrySet()) {
                inventory.release(entry.getKey(), entry.getValue());
            }
            throw e;
        }
    }

    public boolean refund(RefundablePayment payment, long amount) {
        return payment.refund(amount);
    }
}

package shop.service;

import shop.model.Order;
import shop.model.Shippable;
import shop.discount.DiscountStrategy;

/** Menghitung subtotal, diskon, ongkos kirim, dan total pesanan. */
public class OrderCalculator {
    private final long ratePerKg;
    public OrderCalculator(long ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    public Bill calculate(Order order, DiscountStrategy discountStrategy) {
        long subtotal = 0;
        // [R1] Menjumlahkan nilai seluruh barang untuk mendapatkan subtotal.
        for (Order.Line line : order.lines()) {
            subtotal += line.total();
        }

        // [R2] Meminta strategi diskon menghitung potongan dari subtotal.
        long discount = discountStrategy.discount(subtotal);

        int grams = 0;
        // [R3] Menghitung berat pesanan, ongkos kirim, dan total tagihan.
        for (Order.Line line : order.lines()) {
            if (line.product() instanceof Shippable shippable) {
                grams += shippable.getWeightGram() * line.qty();
            }
        }
        long shipping = grams == 0 ? 0 : ((grams + 999) / 1000) * ratePerKg;
        long total = subtotal - discount + shipping;

        return new Bill(subtotal, discount, shipping, total);
    }
}
package shop.discount;

/** Strategi tanpa potongan harga. */
public class NoDiscount implements DiscountStrategy {
    @Override
    public long discount(long subtotal) {
        return 0;
    }
}
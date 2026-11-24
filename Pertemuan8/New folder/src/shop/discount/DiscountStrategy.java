package shop.discount;

/** Strategi untuk menghitung potongan harga dari subtotal. */
public interface DiscountStrategy {
    long discount(long subtotal);
}
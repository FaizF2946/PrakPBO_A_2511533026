package shop.discount;

/** Capability for products that receive a percentage discount. */
public interface Discountable {
    default long applyDiscount(long amount) {
        return amount - percentOf(amount, 10);
    }

    static long percentOf(long amount, int percent) {
        validatePercent(percent);
        return amount * percent / 100;
    }

    private static void validatePercent(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percent must be between 0 and 100");
        }
    }
}
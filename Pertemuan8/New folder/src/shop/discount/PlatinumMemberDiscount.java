package shop.discount;

/** Potongan tetap 20 persen untuk member PLATINUM. */
public class PlatinumMemberDiscount implements DiscountStrategy {
    @Override
    public long discount(long subtotal) {
        return subtotal * 20 / 100;
    }
}
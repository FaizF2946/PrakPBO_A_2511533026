package shop.discount;

/** Potongan persentase untuk member GOLD. */
public class GoldMemberDiscount implements DiscountStrategy {
    private final int percent;

    public GoldMemberDiscount(int percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long subtotal) {
        return subtotal * percent / 100;
    }
}
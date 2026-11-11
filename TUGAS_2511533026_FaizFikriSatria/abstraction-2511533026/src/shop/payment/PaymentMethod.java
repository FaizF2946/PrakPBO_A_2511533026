package shop.payment;

public interface PaymentMethod {
    String getName();
    boolean pay(long amount3026);
    
    default long adminFee(long amount3026) {
        return 0;
    }
}

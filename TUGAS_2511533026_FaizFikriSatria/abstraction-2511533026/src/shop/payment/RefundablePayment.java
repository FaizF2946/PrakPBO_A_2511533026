package shop.payment;

public interface RefundablePayment extends PaymentMethod {
    boolean refund(long amount3026);
    
    default String policy() {
        return "Refund maksimal 7 hari";
    }
}

package shop.payment;

import shop.StudentInfo;

public class BankTransferPayment implements PaymentMethod {
    private final String bankName3026;

    public BankTransferPayment(String bankName3026) {
        this.bankName3026 = bankName3026;
    }

    @Override
    public String getName() {
        return "Transfer " + bankName3026;
    }

    @Override
    public boolean pay(long amount3026) {
        return amount3026 > 0;
    }

    @Override
    public long adminFee(long amount3026) {
        return StudentInfo.TRANSFER_ADMIN_FEE;
    }
}

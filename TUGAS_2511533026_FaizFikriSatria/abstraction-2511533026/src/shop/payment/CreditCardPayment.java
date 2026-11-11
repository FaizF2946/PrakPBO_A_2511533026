package shop.payment;

import shop.StudentInfo;

public class CreditCardPayment implements RefundablePayment, InstallmentPayment {
    private final String cardHolder3026;
    private final long creditLimit3026;
    private long usedCredit3026;

    public CreditCardPayment(String cardHolder3026, long creditLimit3026) {
        this.cardHolder3026 = cardHolder3026;
        this.creditLimit3026 = creditLimit3026;
        this.usedCredit3026 = 0;
    }

    @Override
    public String getName() {
        return "Kartu Kredit (" + cardHolder3026 + ")";
    }

    @Override
    public boolean pay(long amount3026) {
        if (amount3026 <= 0 || usedCredit3026 + amount3026 > creditLimit3026) {
            return false;
        }
        usedCredit3026 += amount3026;
        return true;
    }

    @Override
    public boolean refund(long amount3026) {
        if (amount3026 <= 0 || amount3026 > usedCredit3026) {
            return false;
        }
        usedCredit3026 -= amount3026;
        return true;
    }

    @Override
    public int maxInstallmentMonths() {
        return 12;
    }

    public long getUsedCredit() {
        return usedCredit3026;
    }

    @Override
    public long adminFee(long amount3026) {
        return amount3026 * StudentInfo.CARD_ADMIN_PERCENT / 100;
    }

    @Override
    public String policy() {
        return RefundablePayment.super.policy() + "; " + InstallmentPayment.super.policy();
    }
}

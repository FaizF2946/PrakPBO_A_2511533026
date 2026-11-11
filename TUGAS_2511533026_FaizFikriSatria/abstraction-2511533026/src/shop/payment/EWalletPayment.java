package shop.payment;

public class EWalletPayment implements RefundablePayment {
    private final String provider3026;
    private long balance3026;

    public EWalletPayment(String provider3026, long balance3026) {
        if (provider3026 == null || provider3026.isBlank() || balance3026 < 0) {
            throw new IllegalArgumentException("Invalid provider or balance");
        }
        this.provider3026 = provider3026;
        this.balance3026 = balance3026;
    }

    @Override
    public String getName() {
        return "E-Wallet " + provider3026;
    }

    public long getBalance() {
        return balance3026;
    }

    @Override
    public boolean pay(long amount3026) {
        if (amount3026 <= 0 || amount3026 > balance3026) {
            return false;
        }
        balance3026 -= amount3026;
        return true;
    }

    @Override
    public boolean refund(long amount3026) {
        if (amount3026 <= 0) {
            return false;
        }
        balance3026 += amount3026;
        return true;
    }
}

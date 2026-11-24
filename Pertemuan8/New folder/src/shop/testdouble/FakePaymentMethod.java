package shop.testdouble;

import shop.payment.PaymentMethod;

/** Payment test double yang menyimpan jumlah pembayaran terakhir. */
public class FakePaymentMethod implements PaymentMethod {
    private long lastAmount;

    @Override
    public String name() {
        return "Fake Payment";
    }

    @Override
    public void pay(long amount) {
        lastAmount = amount;
    }

    public long getLastAmount() {
        return lastAmount;
    }
}
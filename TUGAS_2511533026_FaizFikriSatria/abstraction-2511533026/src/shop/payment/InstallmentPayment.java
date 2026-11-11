package shop.payment;

import shop.StudentInfo;

public interface InstallmentPayment extends PaymentMethod {
    int maxInstallmentMonths();

    default long monthlyInstallment(long amount3026, int months3026) {
        if (months3026 < 1 || months3026 > maxInstallmentMonths()) {
            throw new IllegalArgumentException("Months must be between 1 and " + maxInstallmentMonths());
        }
        return (amount3026 + months3026 - 1) / months3026;
    }

    default String policy() {
        return "Cicilan maksimal " + maxInstallmentMonths() + " bulan";
    }
}

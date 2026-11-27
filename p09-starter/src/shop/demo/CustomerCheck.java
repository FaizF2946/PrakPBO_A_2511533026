package shop.demo;

import shop.model.Customer;

/** Demo validasi Builder Customer. */
public class CustomerCheck {
    public static void main(String[] args) {
        Customer valid = new Customer.Builder("Rina", "081234567890")
                .email("rina@mail.com")
                .address("Padang")
                .build();
        System.out.println(valid.getName() + " | " + valid.getMemberLevel());

        try {
            new Customer.Builder("", "081234567890").build();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            new Customer.Builder("Rina", "12345").build();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

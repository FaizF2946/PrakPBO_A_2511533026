package shop.notification;

public final class CustomerContact {
    private final String name3026;
    private final String email3026;
    private final String phone3026;

    public CustomerContact(String name3026, String email3026, String phone3026) {
        if (name3026 == null || name3026.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name3026 = name3026;
        this.email3026 = (email3026 == null) ? "" : email3026;
        this.phone3026 = (phone3026 == null) ? "" : phone3026;
    }

    public String getName() {
        return name3026;
    }

    public String getEmail() {
        return email3026;
    }

    public String getPhone() {
        return phone3026;
    }
}

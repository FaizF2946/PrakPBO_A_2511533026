package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount3026 = 0;
    private String lastDelivered3026 = "";

    protected abstract String resolveRecipient(CustomerContact contact3026);
    protected abstract boolean doSend(String recipient3026, String preparedMessage3026);

    protected String prepare(String message3026) {
        return message3026;
    }

    @Override
    public final boolean send(CustomerContact contact3026, String message3026) {
        if (message3026 == null || message3026.isBlank()) {
            return false;
        }
        String recipient3026 = resolveRecipient(contact3026);
        if (recipient3026 == null || recipient3026.isEmpty()) {
            return false;
        }
        String prepared3026 = prepare(message3026);
        boolean success3026 = doSend(recipient3026, prepared3026);
        if (success3026) {
            sentCount3026++;
            lastDelivered3026 = prepared3026;
        }
        return success3026;
    }

    public int getSentCount() {
        return sentCount3026;
    }

    public String getLastDelivered() {
        return lastDelivered3026;
    }
}

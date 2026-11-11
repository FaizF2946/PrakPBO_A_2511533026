package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "WA";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact3026) {
        String phone3026 = contact3026.getPhone();
        if (phone3026 != null && (phone3026.startsWith("08") || phone3026.startsWith("+62")) && phone3026.length() >= 10) {
            return phone3026;
        }
        return "";
    }

    @Override
    protected String prepare(String message3026) {
        return "[Toko UMKM] " + message3026;
    }

    @Override
    protected boolean doSend(String recipient3026, String preparedMessage3026) {
        System.out.println("[WA to " + recipient3026 + "] " + preparedMessage3026);
        return true;
    }
}

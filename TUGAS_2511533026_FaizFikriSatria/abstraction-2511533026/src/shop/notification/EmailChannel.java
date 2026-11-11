package shop.notification;

public class EmailChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "EMAIL";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact3026) {
        String email3026 = contact3026.getEmail();
        if (email3026 != null && email3026.contains("@")) {
            return email3026;
        }
        return "";
    }

    @Override
    protected boolean doSend(String recipient3026, String preparedMessage3026) {
        System.out.println("[EMAIL to " + recipient3026 + "] " + preparedMessage3026);
        return true;
    }
}

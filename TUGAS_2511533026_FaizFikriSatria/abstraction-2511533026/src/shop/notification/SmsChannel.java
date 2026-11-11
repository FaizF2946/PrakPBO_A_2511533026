package shop.notification;

import shop.StudentInfo;

public class SmsChannel extends AbstractChannel {
    private final int maxLength3026;

    public SmsChannel(int maxLength3026) {
        this.maxLength3026 = maxLength3026;
    }

    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact3026) {
        String phone3026 = contact3026.getPhone();
        if (phone3026 != null && phone3026.startsWith("08")) {
            return phone3026;
        }
        return "";
    }

    @Override
    protected String prepare(String message3026) {
        if (message3026.length() > maxLength3026) {
            return message3026.substring(0, maxLength3026 - 3) + "...";
        }
        return message3026;
    }

    @Override
    protected boolean doSend(String recipient3026, String preparedMessage3026) {
        System.out.println("[SMS to " + recipient3026 + "] " + preparedMessage3026);
        return true;
    }
}

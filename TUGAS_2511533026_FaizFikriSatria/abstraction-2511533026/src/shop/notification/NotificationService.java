package shop.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationChannel> channels3026 = new ArrayList<>();

    public void register(NotificationChannel channel3026) {
        if (channel3026 != null) {
            channels3026.add(channel3026);
        }
    }

    public int broadcast(CustomerContact contact3026, String message3026) {
        int count3026 = 0;
        for (NotificationChannel channel3026 : channels3026) {
            if (channel3026.send(contact3026, message3026)) {
                count3026++;
            }
        }
        return count3026;
    }
}

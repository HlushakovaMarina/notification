package by.hlushakova.notification.service;

import org.springframework.stereotype.Service;

@Service("pushNotificationService")
public class PushNotificationService implements NotificationServiceInterface{

    @Override
    public void sendNotification(String message) {
        System.out.println("Sending push notification: " + message);
    }

    @Override
    public String getServiceType() {
        return "PUSH";
    }
}

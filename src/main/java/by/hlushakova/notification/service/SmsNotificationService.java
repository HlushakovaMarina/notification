package by.hlushakova.notification.service;

import org.springframework.stereotype.Service;

@Service("smsNotificationService")
public class SmsNotificationService implements NotificationServiceInterface {
    @Override
    public void sendNotification(String message) {
        System.out.println("Sending sms notification: "+ message);
    }

    @Override
    public String getServiceType() {
        return "SMS";
    }
}

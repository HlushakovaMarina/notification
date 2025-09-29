package by.hlushakova.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationManagerService {

    private final NotificationServiceInterface emailService;
    private final NotificationServiceInterface smsService;
    private final NotificationServiceInterface pushService;
    private final List<NotificationServiceInterface> allNotificationService;
    private final Map<String, NotificationServiceInterface> notificationServiceMap;
    //private final FarmApiClient farmApiClient;


    @Autowired
    public NotificationManagerService(@Qualifier("emailNotificationService") NotificationServiceInterface emailService,
                                      @Qualifier("smsNotificationService") NotificationServiceInterface smsService,
                                      @Qualifier("pushNotificationService") NotificationServiceInterface pushService,
                                      List<NotificationServiceInterface> allNotificationService,
                                      Map<String, NotificationServiceInterface> notificationServiceMap) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushService = pushService;
        this.allNotificationService = allNotificationService;
        this.notificationServiceMap = notificationServiceMap;
    }

    public void sendEmailNotification(String message){
        emailService.sendNotification(message);
    }
    public void sendSmsNotification(String message){
        smsService.sendNotification(message);
    }
    public void sendPushNotification(String message){
        pushService.sendNotification(message);
    }

    public void sendNotificationToAll(String message) {
        System.out.println("Отправили сообщение на все сервисы");
allNotificationService.forEach(service -> {
    System.out.println("Service type: " + service.getServiceType());
    service.sendNotification(message);
});
    }

    public void sendNotificationByType(String serviceType, String message) {
        NotificationServiceInterface service = notificationServiceMap.get(serviceType);
        if (service != null) {
            service.sendNotification(message);
        } else {
            System.out.println("Service type '" + serviceType + "'not found");
        }
    }
}

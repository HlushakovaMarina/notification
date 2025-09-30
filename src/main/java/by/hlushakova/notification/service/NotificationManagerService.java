package by.hlushakova.notification.service;

import by.hlushakova.notification.client.FarmApiClient;
import by.hlushakova.notification.dto.FarmRequest;
import by.hlushakova.notification.dto.FarmResponse;
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
    private final FarmApiClient farmApiClient;
    private NotificationManagerService notificationService;


    @Autowired
    public NotificationManagerService(@Qualifier("emailNotificationService") NotificationServiceInterface emailService,
                                      @Qualifier("smsNotificationService") NotificationServiceInterface smsService,
                                      @Qualifier("pushNotificationService") NotificationServiceInterface pushService,
                                      List<NotificationServiceInterface> allNotificationService,
                                      Map<String, NotificationServiceInterface> notificationServiceMap,
                                      FarmApiClient farmApiClient) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushService = pushService;
        this.allNotificationService = allNotificationService;
        this.notificationServiceMap = notificationServiceMap;
        this.farmApiClient = farmApiClient;
    }

    public void sendEmailNotification(String message) {
        emailService.sendNotification(message);
    }

    public void sendSmsNotification(String message) {
        smsService.sendNotification(message);
    }

    public void sendPushNotification(String message) {
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
    public FarmResponse createFarmAndNotify(String farmName, String location, String notificationMessage) {
        try {
            FarmRequest farmRequest = new FarmRequest(farmName, location);
            FarmResponse farmResponse = farmApiClient.createFarm(farmRequest);
            notificationService.sendNotificationToAll(notificationMessage);
            return farmResponse;

        } catch (Exception e) {
            System.err.println("Ошибка при создании фермы или отправке уведомления: " + e.getMessage());
            throw new RuntimeException("Не удалось создать ферму и уведомлять.", e);
        }
    }
}

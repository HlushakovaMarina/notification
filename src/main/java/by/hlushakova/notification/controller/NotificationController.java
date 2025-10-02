package by.hlushakova.notification.controller;

import by.hlushakova.notification.dto.FarmResponse;
import by.hlushakova.notification.service.NotificationManagerService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationManagerService notificationManagerService;

    @Autowired
    public NotificationController(NotificationManagerService notificationManagerService) {
        this.notificationManagerService = notificationManagerService;
    }

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> sendEmailNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendEmailNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "email");
        response.put("message", "Email notification sent");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sms")
    public ResponseEntity<Map<String, String>> sendSmsNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendSmsNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "sms");
        response.put("message", "SMS notification sent");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/push")
    public ResponseEntity<Map<String, String>> sendPushNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendPushNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "push");
        response.put("message", "Push notification sent");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/all")
    public ResponseEntity<Map<String, String>> sendNotificationToAll(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendNotificationToAll(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "sms");
        response.put("message", "All notification sent");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/by-type/{serviceType}")
    public ResponseEntity<Map<String, String>> sendNotificationByType(
            @PathVariable String serviceType,
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerService.sendNotificationByType(serviceType, message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", serviceType);
        response.put("message", "Notification sent to " + serviceType);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/create-farm")
    public ResponseEntity<FarmResponse> createFarm(@RequestBody Map<String, String> request) {
        try {
            String farmName = request.get("farmName");
            String location = request.get("location");
            String notificationMessage = request.get("notificationMessage");

            if (farmName == null || location == null || notificationMessage == null) {
                System.err.println("Ошибка: Не все необходимые параметры переданы.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            FarmResponse farmResponse = notificationManagerService.createFarmAndNotify(farmName,
                    location, notificationMessage);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Farm created and notification sent");
            response.put("farm", farmResponse);
            return new ResponseEntity<>(farmResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println("Ошибка при создании фермы и отправке уведомления: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

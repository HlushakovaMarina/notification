package by.hlushakova.notification.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> sendEmailNotification(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerServise.sendEmailNotification(message);

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
        notificationManagerServise.sendEmailNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", "sms");
        response.put("message", "SMS notification sent");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/all")
    public ResponseEntity<Map<String, String>> sendNotificationToAll(
            @RequestBody Map<String, String> request){
        String message = request.get("message");
        notificationManagerServise.sendEmailNotification(message);

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
        notificationManagerServise.sendEmailNotification(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("type", serviceType);
        response.put("message", "Notification sent to " + serviceType);

        return ResponseEntity.ok(response);
    }
}

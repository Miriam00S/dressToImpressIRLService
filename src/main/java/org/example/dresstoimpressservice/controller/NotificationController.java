package org.example.dresstoimpressservice.controller;

import org.example.dresstoimpressservice.dto.CreateNotificationDto;
import org.example.dresstoimpressservice.model.Notification;
import org.example.dresstoimpressservice.model.Show;
import org.example.dresstoimpressservice.model.User;
import org.example.dresstoimpressservice.repository.NotificationRepository;
import org.example.dresstoimpressservice.repository.ShowRepository;
import org.example.dresstoimpressservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private  final ShowRepository showRepository;

    public NotificationController(NotificationRepository notificationRepository, UserRepository userRepository, ShowRepository showRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody CreateNotificationDto createNotificationDto) {
        // Znajdź użytkownika po ID
        User user = userRepository.findById(createNotificationDto.getUserId()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Show show = showRepository.findById(createNotificationDto.getShowId()).orElse(null);

        if (show == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Tworzymy nowy obiekt Notification
        Notification notification = new Notification();
        notification.setRead(createNotificationDto.getRead());
        notification.setMessage(createNotificationDto.getMessage());
        notification.setUser(user);
        notification.setShow(show);

        // Zapisujemy nowy obiekt Notification w bazie danych
        Notification savedNotification = notificationRepository.save(notification);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody CreateNotificationDto createNotificationDto) {
        // Znajdujemy powiadomienie po ID
        Notification notification = notificationRepository.findById(id).orElse(null);

        if (notification == null) {
            // Jeśli powiadomienie nie istnieje, zwracamy kod 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Zaktualizuj dane powiadomienia
        notification.setRead(createNotificationDto.getRead());
        notification.setMessage(createNotificationDto.getMessage());

        // Zapisz zaktualizowane powiadomienie
        Notification updatedNotification = notificationRepository.save(notification);

        // Zwróć zaktualizowane powiadomienie
        return ResponseEntity.ok(updatedNotification);
    }
}

package org.example.dresstoimpressservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Plik jest pusty");
        }

        try {
            // Tworzymy katalog, jeśli nie istnieje
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generujemy unikalną nazwę pliku na podstawie znacznika czasu
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Ścieżka do zapisu pliku z nową unikalną nazwą
            Path filePath = Paths.get(uploadDir, uniqueFileName);

            // Zapisujemy plik
            Files.write(filePath, file.getBytes());

            // Zwracamy nazwę pliku, a nie pełną ścieżkę
            return ResponseEntity.ok(String.valueOf(filePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Błąd zapisu pliku: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testUploadDir() {
        System.out.println("testUploadDir method called");
        return ResponseEntity.ok("Katalog uploadów: " + uploadDir);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Tworzymy pełną ścieżkę do pliku
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

            // Sprawdzamy, czy plik istnieje
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            // Ładujemy plik jako zasób
            Resource resource = new UrlResource(filePath.toUri());

            // Sprawdzamy, czy zasób jest dostępny
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(500).body(null); // Błąd odczytu pliku
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Ogólny błąd I/O
        }
    }
}

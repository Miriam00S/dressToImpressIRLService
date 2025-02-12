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
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // create a directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // generate a unique file name based on the timestamp
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path filePath = Paths.get(uploadDir, uniqueFileName);

            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok(String.valueOf(filePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File save error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testUploadDir() {
        System.out.println("testUploadDir method called");
        return ResponseEntity.ok("Uploads catalog: " + uploadDir);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // create the full path to the file
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            // We load the file as a resource
            Resource resource = new UrlResource(filePath.toUri());

            // check if the resource is available
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(500).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}

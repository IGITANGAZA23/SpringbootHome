package com.igitan.springboothome.controller;

import com.igitan.springboothome.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/files")
public class FileController {

  @Autowired
  private FileStorageService fileStorageService;

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    try {
      Path filePath = fileStorageService.loadFileAsResource(fileName);
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists()) {
        String contentType = "application/octet-stream";
        // Try to determine file's content type
        try {
          contentType = java.nio.file.Files.probeContentType(filePath);
        } catch (java.io.IOException ex) {
          // Fallback to default
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (MalformedURLException ex) {
      return ResponseEntity.badRequest().build();
    }
  }
}

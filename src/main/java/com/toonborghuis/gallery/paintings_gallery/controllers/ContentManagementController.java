package com.toonborghuis.gallery.paintings_gallery.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toonborghuis.gallery.paintings_gallery.entities.ContentManagement;
import com.toonborghuis.gallery.paintings_gallery.entities.Image;
import com.toonborghuis.gallery.paintings_gallery.services.ContentManagementService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/content_management")
@AllArgsConstructor
public class ContentManagementController {

    private final ContentManagementService contentManagementService;

    @GetMapping()
    public ResponseEntity<ContentManagement> getContentManagement() {
        ContentManagement contentManagement = contentManagementService.getContentManagement();
        return ResponseEntity.ok(contentManagement);
    }
    
    @PostMapping("homepage/image/upload")
    public ResponseEntity<Image> uploadHomepagePainting(@RequestPart MultipartFile file) throws IOException {
        Image image = contentManagementService.uploadHomepagePainting(file);
        return ResponseEntity.ok(image);
    }

}

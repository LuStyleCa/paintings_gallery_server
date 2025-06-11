package com.toonborghuis.gallery.paintings_gallery.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toonborghuis.gallery.paintings_gallery.entities.AboutPage;
import com.toonborghuis.gallery.paintings_gallery.services.AboutPageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/about_page")
@AllArgsConstructor
public class AboutPageController {

    private final AboutPageService aboutPageService;

    @PutMapping
    public ResponseEntity<AboutPage> saveAboutPage(@RequestPart MultipartFile file, @RequestPart String aboutPageData) {
        try {
            return new ResponseEntity<>(aboutPageService.saveAboutPage(file, aboutPageData), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

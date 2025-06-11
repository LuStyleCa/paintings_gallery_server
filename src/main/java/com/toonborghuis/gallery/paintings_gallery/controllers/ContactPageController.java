package com.toonborghuis.gallery.paintings_gallery.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toonborghuis.gallery.paintings_gallery.dtos.ContactPageDto;
import com.toonborghuis.gallery.paintings_gallery.services.ContactPageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/contact_page")
@AllArgsConstructor
public class ContactPageController {
    
    private final ContactPageService contactPageService;

    @PutMapping()
    public ResponseEntity<ContactPageDto> saveContactPage(@RequestBody ContactPageDto contactPageDto) {
        return new ResponseEntity<>(contactPageService.saveContactPage(contactPageDto), HttpStatus.OK);
    }
}

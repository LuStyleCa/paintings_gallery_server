package com.toonborghuis.gallery.paintings_gallery.services;

import org.springframework.stereotype.Service;

import com.toonborghuis.gallery.paintings_gallery.dtos.ContactPageDto;
import com.toonborghuis.gallery.paintings_gallery.entities.ContactPage;
import com.toonborghuis.gallery.paintings_gallery.entities.ContentManagement;
import com.toonborghuis.gallery.paintings_gallery.repositories.ContactPageRepository;
import com.toonborghuis.gallery.paintings_gallery.repositories.ContentManagementRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactPageService {

    private final ContactPageRepository contactPageRepository;
    private final ContentManagementRepository contentManagementRepository;

    public ContactPageDto saveContactPage(ContactPageDto contactPageDto) {
        ContentManagement contentManagement = contentManagementRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ContentManagement with ID 1 not found"));
        ContactPage contactPage = contactPageRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ContactPage with ID 1 not found"));

        if (contactPageDto.getEmail() != null && !contactPageDto.getEmail().isBlank()) {
            contactPage.setEmail(contactPageDto.getEmail());
        }

        if (contactPageDto.getPhoneNumber() != null && !contactPageDto.getPhoneNumber().isBlank()) {
            contactPage.setPhoneNumber(contactPageDto.getPhoneNumber());
        }

        if (contactPageDto.getLocation() != null && !contactPageDto.getLocation().isBlank()) {
            contactPage.setLocation(contactPageDto.getLocation());
        }

        contentManagement.setContactPage(contactPage);
        contactPage.setContentManagement(contentManagement);
        contactPageRepository.save(contactPage);

        return toDto(contactPage);
    }

    public ContactPageDto toDto(ContactPage contactPage) {
        ContactPageDto dto = new ContactPageDto();
        dto.setEmail(contactPage.getEmail());
        dto.setPhoneNumber(contactPage.getPhoneNumber());
        dto.setLocation(contactPage.getLocation());
        return dto;
    }

}

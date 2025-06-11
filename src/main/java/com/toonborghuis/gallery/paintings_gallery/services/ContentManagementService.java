package com.toonborghuis.gallery.paintings_gallery.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toonborghuis.gallery.paintings_gallery.entities.ContentManagement;
import com.toonborghuis.gallery.paintings_gallery.entities.Image;
import com.toonborghuis.gallery.paintings_gallery.repositories.ContentManagementRepository;
import com.toonborghuis.gallery.paintings_gallery.repositories.ImageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContentManagementService {

    private final ContentManagementRepository contentManagementRepository;
    private final ImageRepository imageRepository;
    private final AwsS3Service awsS3Service;

    public ContentManagement getContentManagement() {
        return contentManagementRepository.getReferenceById(1L);
    }

    public Image uploadHomepagePainting(MultipartFile file) throws IOException {
        ContentManagement contentManagement = contentManagementRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ContentManagement not found"));

        if(contentManagement.getHomepagePainting() != null) {
            Image updateImage = contentManagement.getHomepagePainting();
            updateImage.setFileUrl(awsS3Service.uploadFileToS3(file));
            contentManagement.setHomepagePainting(updateImage);
            return imageRepository.save(updateImage);
        }

        Image image = new Image();
        image.setFileUrl(awsS3Service.uploadFileToS3(file));

        contentManagement.setHomepagePainting(image);
        contentManagementRepository.save(contentManagement);
        return imageRepository.save(image);
    }
}

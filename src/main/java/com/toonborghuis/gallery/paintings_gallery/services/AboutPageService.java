package com.toonborghuis.gallery.paintings_gallery.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.toonborghuis.gallery.paintings_gallery.entities.AboutPage;
import com.toonborghuis.gallery.paintings_gallery.entities.ContentManagement;
import com.toonborghuis.gallery.paintings_gallery.entities.Image;
import com.toonborghuis.gallery.paintings_gallery.repositories.AboutPageRepository;
import com.toonborghuis.gallery.paintings_gallery.repositories.ContentManagementRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AboutPageService {

    private final AboutPageRepository aboutPageRepository;
    private final ContentManagementRepository contentManagementRepository;
    private final AwsS3Service awsS3Service;

    public AboutPage saveAboutPage(MultipartFile file, String aboutPageData) throws IOException {
        ContentManagement contentManagement = contentManagementRepository.getReferenceById(1L);
        AboutPage aboutPage = aboutPageRepository.getReferenceById(1L);
        if (aboutPage.getAboutPageImage() == null) {
            aboutPage.setAboutPageImage(new Image());
        }
        aboutPage.getAboutPageImage().setFileUrl(awsS3Service.uploadFileToS3(file));
        aboutPage.setText(aboutPageData);
        aboutPage.setContentManagement(contentManagement);
        contentManagement.setAboutPage(aboutPage);
        return aboutPageRepository.save(aboutPage);
    }

}

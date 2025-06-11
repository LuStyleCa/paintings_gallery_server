package com.toonborghuis.gallery.paintings_gallery.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import com.toonborghuis.gallery.paintings_gallery.dtos.GalleryDto;
import com.toonborghuis.gallery.paintings_gallery.entities.ContentManagement;
import com.toonborghuis.gallery.paintings_gallery.entities.Gallery;
import com.toonborghuis.gallery.paintings_gallery.entities.Painting;
import com.toonborghuis.gallery.paintings_gallery.repositories.ContentManagementRepository;
import com.toonborghuis.gallery.paintings_gallery.repositories.GalleryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final ContentManagementRepository contentManagementRepository;

    public List<Gallery> getAllGalleries() {
        return galleryRepository.findAll();
    }

    public void deleteGalleryById(Long galleryId) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("Gallery not found"));

        // Delete associated painting files from uploads/
        for (Painting painting : gallery.getPaintings()) {
            String filePath = "uploads/" + painting.getFileUrl().replace("http://localhost:8080/uploads/", "");
            ;
            try {
                Files.deleteIfExists(Paths.get(filePath)); // Deletes file if it exists
            } catch (IOException e) {
                e.printStackTrace(); // Proper logging in a real application
            }
        }

        // Delete gallery (which will also remove paintings from the database)
        galleryRepository.delete(gallery);
    }

    public Gallery createGallery(GalleryDto galleryDto) {
        ContentManagement contentManagement = contentManagementRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ContentManagement with ID 1 not found"));
        Gallery gallery = new Gallery();
        gallery.setTitle(galleryDto.getTitle());
        gallery.setShowTitle(galleryDto.isShowTitle());
        gallery.setContentManagement(contentManagement);
        contentManagement.getGalleries().add(gallery);
        return galleryRepository.save(gallery);
    }

    public Gallery updateGallery(Long galleryId, GalleryDto galleryRequest) {
        Gallery galleryToUpdate = getGalleryById(galleryId);

        galleryToUpdate.setTitle(galleryRequest.getTitle());
        galleryToUpdate.setShowTitle(galleryRequest.isShowTitle());

        return galleryRepository.save(galleryToUpdate);
    }

    public Gallery getGalleryById(Long galleryId) {
        return galleryRepository.findById(galleryId)
                .orElseThrow(() -> new RuntimeException("Gallery not found with id: " + galleryId));
    }
}

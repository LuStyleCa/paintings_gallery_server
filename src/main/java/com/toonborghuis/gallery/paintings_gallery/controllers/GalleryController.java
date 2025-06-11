package com.toonborghuis.gallery.paintings_gallery.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toonborghuis.gallery.paintings_gallery.dtos.GalleryDto;
import com.toonborghuis.gallery.paintings_gallery.entities.Gallery;
import com.toonborghuis.gallery.paintings_gallery.services.GalleryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/galleries")
@AllArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;
    private static final Logger logger = LoggerFactory.getLogger(PaintingController.class);

    @GetMapping()
    public ResponseEntity<List<Gallery>> getAllGalleries() {
        List<Gallery> galleries = galleryService.getAllGalleries();
        return ResponseEntity.ok(galleries);
    }

    @PostMapping("/create")
    public ResponseEntity<Gallery> createGallery(@RequestBody GalleryDto galleryRequest) {
        logger.info("showTitle: " + galleryRequest.isShowTitle());
        Gallery gallery = galleryService.createGallery(galleryRequest);
        return ResponseEntity.ok(gallery);
    }

    @DeleteMapping("/{galleryId}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long galleryId) {
        galleryService.deleteGalleryById(galleryId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{galleryId}")
    public ResponseEntity<Gallery> updateGallery(@PathVariable Long galleryId, @RequestBody GalleryDto gallery) {
        Gallery updatedGallery = galleryService.updateGallery(galleryId, gallery);
        return ResponseEntity.ok(updatedGallery);
    }

}

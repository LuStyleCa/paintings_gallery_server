package com.toonborghuis.gallery.paintings_gallery.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.toonborghuis.gallery.paintings_gallery.dtos.PaintingDto;
import com.toonborghuis.gallery.paintings_gallery.entities.Gallery;
import com.toonborghuis.gallery.paintings_gallery.entities.Painting;
import com.toonborghuis.gallery.paintings_gallery.repositories.PaintingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaintingService {

    private final GalleryService galleryService;
    private final PaintingRepository paintingRepository;
    private final AwsS3Service awsS3Service;

    public Painting getPaintingById(Long paintingId) {
        return paintingRepository.findById(paintingId)
                .orElseThrow(() -> new RuntimeException("Painting with ID " + paintingId + " not found"));
    }

    public Painting addPainting(Long galleryId, PaintingDto dto, MultipartFile file) throws IOException {
        Gallery gallery = galleryService.getGalleryById(galleryId);
        dto.setGallery(gallery);

        dto.setFileUrl(awsS3Service.uploadFileToS3(file));

        Painting painting = new Painting();
        painting.setFileUrl(dto.getFileUrl());
        painting.setTitle(dto.getTitle());
        painting.setDescription(dto.getDescription());
        painting.setPrice(dto.getPrice());
        painting.setGallery(dto.getGallery());
        return paintingRepository.save(painting);

    }

    @Transactional
    public void deletePainting(Long galleryId, Long paintingId) {

        Gallery gallery = galleryService.getGalleryById(galleryId);

        Painting painting = gallery.getPaintings().stream()
                .filter(p -> p.getId().equals(paintingId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Painting with id: " + paintingId + " not found in gallery"));

        gallery.getPaintings().remove(painting);

        String paintingFileUrl = painting.getFileUrl();

        if (!paintingFileUrl.isEmpty()) {
            awsS3Service.deleteFileFromS3(paintingFileUrl);
        }
    }

    public Painting updatePainting(Long galleryId, Long paintingId, MultipartFile file, PaintingDto paintingDto)
            throws IOException {
        Gallery gallery = galleryService.getGalleryById(galleryId);
        paintingDto.setGallery(gallery);

        Painting paintingToUpdate = gallery.getPaintings().stream()
                .filter(p -> p.getId().equals(paintingId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Painting not found"));

        awsS3Service.deleteFileFromS3(paintingToUpdate.getFileUrl());

        paintingDto.setFileUrl(awsS3Service.uploadFileToS3(file));

        paintingToUpdate.setFileUrl(paintingDto.getFileUrl());
        paintingToUpdate.setTitle(paintingDto.getTitle());
        paintingToUpdate.setDescription(paintingDto.getDescription());
        paintingToUpdate.setPrice(paintingDto.getPrice());
        paintingToUpdate.setGallery(paintingDto.getGallery());
        return paintingRepository.save(paintingToUpdate);

    }
}

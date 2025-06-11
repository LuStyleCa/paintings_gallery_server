package com.toonborghuis.gallery.paintings_gallery.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toonborghuis.gallery.paintings_gallery.dtos.PaintingDto;
import com.toonborghuis.gallery.paintings_gallery.entities.Painting;
import com.toonborghuis.gallery.paintings_gallery.services.PaintingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/paintings")
@AllArgsConstructor
public class PaintingController {

    private final PaintingService paintingService;
    private static final Logger logger = LoggerFactory.getLogger(PaintingController.class);

    @GetMapping("{paintingId}")
    public ResponseEntity<Painting> getPaintingById(@PathVariable Long paintingId) {
        Painting painting = paintingService.getPaintingById(paintingId);
        return ResponseEntity.ok(painting);
    }

    @PostMapping("/upload/{galleryId}")
    public ResponseEntity<Painting> uploadPainting(
            @PathVariable Long galleryId,
            @RequestPart MultipartFile file,
            @RequestPart String paintingDto) {

        try {
            PaintingDto dto = convertToPaintingDto(paintingDto);
            return new ResponseEntity<>(paintingService.addPainting(galleryId, dto, file), HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private PaintingDto convertToPaintingDto(String paintingDto) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(paintingDto, PaintingDto.class);
    }

    @DeleteMapping("/{galleryId}/{paintingId}")
    public ResponseEntity<Void> deletePainting(@PathVariable Long paintingId,
            @PathVariable Long galleryId) {
        try {
            paintingService.deletePainting(galleryId, paintingId);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting painting", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/{galleryId}/{paintingId}")
    public ResponseEntity<Painting> updatePainting(@PathVariable Long galleryId,
            @PathVariable Long paintingId, @RequestPart MultipartFile file, @RequestPart String paintingDto)
            throws IOException {
        PaintingDto dto = convertToPaintingDto(paintingDto);
        Painting painting = paintingService.updatePainting(galleryId, paintingId,
                file, dto);
        return ResponseEntity.ok(painting);
    }

}

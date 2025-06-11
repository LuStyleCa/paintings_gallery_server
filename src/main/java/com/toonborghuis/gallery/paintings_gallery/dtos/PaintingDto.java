package com.toonborghuis.gallery.paintings_gallery.dtos;

import com.toonborghuis.gallery.paintings_gallery.entities.Gallery;

import lombok.Data;

@Data
public class PaintingDto {
    private String title;
    private String description;
    private String price;
    private String fileUrl;
    private Gallery gallery;
}

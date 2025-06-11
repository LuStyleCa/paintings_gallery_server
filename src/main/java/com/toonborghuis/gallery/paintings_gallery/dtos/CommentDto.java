package com.toonborghuis.gallery.paintings_gallery.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentDto {
    private String message;
    private String sender;
    private Long paintingId;
    private Long userId;
}

package com.toonborghuis.gallery.paintings_gallery.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.toonborghuis.gallery.paintings_gallery.auth.entities.User;
import com.toonborghuis.gallery.paintings_gallery.auth.repositories.UserRepository;
import com.toonborghuis.gallery.paintings_gallery.dtos.CommentDto;
import com.toonborghuis.gallery.paintings_gallery.entities.Comment;
import com.toonborghuis.gallery.paintings_gallery.entities.Painting;
import com.toonborghuis.gallery.paintings_gallery.repositories.CommentRepository;
import com.toonborghuis.gallery.paintings_gallery.repositories.PaintingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PaintingRepository paintingRepository;

    public Comment savePaintingComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(
                () -> new RuntimeException("User with ID " + commentDto.getUserId() + " not found."));
        Painting painting = paintingRepository.findById(commentDto.getPaintingId()).orElseThrow(
            () -> new RuntimeException("Painting with ID " + commentDto.getPaintingId() + " not found."));
        
        Comment newComment = new Comment();
        newComment.setMessage(commentDto.getMessage());
        newComment.setSender(commentDto.getSender());
        newComment.setTimestamp(Instant.now());
        newComment.setPainting(painting);
        newComment.setUser(user);
        return commentRepository.save(newComment);
    }

}

package com.toonborghuis.gallery.paintings_gallery.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.toonborghuis.gallery.paintings_gallery.dtos.CommentDto;
import com.toonborghuis.gallery.paintings_gallery.entities.Comment;
import com.toonborghuis.gallery.paintings_gallery.services.CommentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @MessageMapping("/comment")
    @SendTo("/topic/comments")
    public Comment send(CommentDto commentDto) {
        return commentService.savePaintingComment(commentDto);
    }
    
}

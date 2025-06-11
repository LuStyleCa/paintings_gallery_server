package com.toonborghuis.gallery.paintings_gallery.auth.utils;

import lombok.Builder;

import lombok.Getter;

@Getter
@Builder
public class MailBody {
    private String to;
    private String subject;
    private String text;
}

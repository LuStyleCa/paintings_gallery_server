package com.toonborghuis.gallery.paintings_gallery.auth.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
}

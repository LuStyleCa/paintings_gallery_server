package com.toonborghuis.gallery.paintings_gallery.auth.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String email;
    private String password;
}

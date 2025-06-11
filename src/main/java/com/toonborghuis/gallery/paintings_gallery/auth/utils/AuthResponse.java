package com.toonborghuis.gallery.paintings_gallery.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private Long id;
    private String accessToken;
    private String refreshToken;
    private String username;
    private String email;
    private String role;
}

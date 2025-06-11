package com.toonborghuis.gallery.paintings_gallery.auth.entities;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Please enter refresh token value!")
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    @JsonIgnore
    private User user;

}

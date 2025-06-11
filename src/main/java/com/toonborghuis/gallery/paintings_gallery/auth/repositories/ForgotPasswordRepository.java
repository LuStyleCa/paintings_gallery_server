package com.toonborghuis.gallery.paintings_gallery.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.toonborghuis.gallery.paintings_gallery.auth.entities.ForgotPassword;
import com.toonborghuis.gallery.paintings_gallery.auth.entities.User;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    Optional<ForgotPassword> findByUser(User user);
}

package com.toonborghuis.gallery.paintings_gallery.auth.controllers;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toonborghuis.gallery.paintings_gallery.auth.entities.ForgotPassword;
import com.toonborghuis.gallery.paintings_gallery.auth.entities.User;
import com.toonborghuis.gallery.paintings_gallery.auth.repositories.ForgotPasswordRepository;
import com.toonborghuis.gallery.paintings_gallery.auth.repositories.UserRepository;
import com.toonborghuis.gallery.paintings_gallery.auth.services.AuthEmailService;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.ChangePassword;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.EmailRequest;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.MailBody;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.VerifyOtp;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/forgotPassword")
@AllArgsConstructor
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final AuthEmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final PasswordEncoder passwordEncoder;

    // send mail for email verification
    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailRequest emailRequest) {
        System.out.println("verifyEmail method called with email: " + emailRequest.email());
        User user = userRepository.findByEmail(emailRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!" + emailRequest.email()));
        System.out.println("user has been found and we continue");

        Optional<ForgotPassword> optionalForgotPassword = forgotPasswordRepository.findByUser(user);

        if (optionalForgotPassword.isPresent()) {
            ForgotPassword forgotPassword = optionalForgotPassword.get();
            Date expireTime = forgotPassword.getExpirationTime();

            if (expireTime.after(new Date())) {

                return ResponseEntity
                        .badRequest()
                        .body("An OTP has already been sent. Please wait until it expires before requesting a new one.");

            } else {
                forgotPasswordRepository.delete(forgotPassword);

            }
        }

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(emailRequest.email())
                .text("This is the OTP for your Forgot Password request : " + otp)
                .subject("OTP for Forgot Password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtp verifyOtp) {
        User user = userRepository.findByEmail(verifyOtp.email())
                .orElseThrow(() -> new UsernameNotFoundException("Please provide an valid email!"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(verifyOtp.otp(), user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email: " + verifyOtp.email()));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified!");
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword
            ) {
        if (!Objects.equals(changePassword.password(), changePassword.confirmPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(changePassword.email(), encodedPassword);

        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}

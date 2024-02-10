package com.XAUS.Services.Auth;


import com.XAUS.DTOS.Auth.GenerateTokenDTO;
import com.XAUS.DTOS.Auth.PasswordResetDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Models.Auth.PasswordResetToken;
import com.XAUS.Models.User.User;
import com.XAUS.Repositories.Auth.PasswordResetRepository;
import com.XAUS.Services.EmailSender.EmailService;
import com.XAUS.Services.EmailSender.HtmlEmailGenerator;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private HtmlEmailGenerator htmlEmailGenerator;

    public void createPasswordResetToken(User user, String passwordToken) {

        PasswordResetToken token = new PasswordResetToken(passwordToken, user);
        passwordResetRepository.save(token);

    }

    public void validatePasswords(PasswordResetDTO passwordResetDTO) {
        if (!passwordResetDTO.newPassword().equals(passwordResetDTO.confirmPassword())) {
            throw new CustomException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
    }

    public void resetUserPassword(String token, PasswordResetDTO passwordResetDTO) {

        validatePasswordResetToken(token);
        validatePasswords(passwordResetDTO);
        Optional<User> user = findUserByPasswordToken(token);

        user.ifPresent(value -> userService.resetUserPassword(value, passwordResetDTO));


    }

    public void generateAndSendToken(GenerateTokenDTO generateTokenDTO) throws MessagingException {

        User user = userService.findUserByEmail(generateTokenDTO.email());
        if (user != null) {
            String passwordToken = UUID.randomUUID().toString();
            createPasswordResetToken(user, passwordToken);
            sendPasswordResetEmail(user, passwordToken);
        } else {
            throw new CustomException("There's no generated token for user with email " + generateTokenDTO.email(), HttpStatus.NOT_FOUND);
        }

    }

    private void sendPasswordResetEmail(User user, String passwordToken) throws MessagingException {

        //TODO: CHANGE IT TO THE PUBLISHED URL
        String url = "http://localhost:5173/password-recover/" + passwordToken;


        String text = htmlEmailGenerator.generateRecoverPasswordHtml(user.getName(), url);
//        emailService.sendSimpleMessage(user.getEmail(), );
        emailService.sendRecoverPasswordMessage(user.getEmail(), "Recuperação de senha - Xaus system", text);

    }


    public Boolean validatePasswordResetToken(String passwordResetToken) {

        PasswordResetToken passwordToken = passwordResetRepository.findByToken(passwordResetToken);
        if (passwordToken == null) {
            throw new CustomException("There is no matching generated tokens to: " + passwordResetToken, HttpStatus.BAD_REQUEST);
        }
        Calendar calendar = Calendar.getInstance();

        if ((passwordToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            throw new CustomException("Link already expired, resend link", HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetRepository.findByToken(passwordResetToken).getUser());
    }

    public PasswordResetToken findPasswordResetToken(String token) {
        return passwordResetRepository.findByToken(token);
    }

}

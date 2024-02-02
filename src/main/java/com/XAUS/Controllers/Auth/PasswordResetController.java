package com.XAUS.Controllers.Auth;


import com.XAUS.DTOS.Auth.GenerateTokenDTO;
import com.XAUS.DTOS.Auth.PasswordResetDTO;
import com.XAUS.Services.Auth.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    PasswordResetService passwordResetService;

    @PostMapping("/generateToken")
    public void generateToken (@RequestBody GenerateTokenDTO generateTokenDTO){
        passwordResetService.generateAndSendToken(generateTokenDTO);

    }

    @GetMapping("/validate-token")
    public Boolean validateToken(@RequestParam String token){
            return passwordResetService.validatePasswordResetToken(token);
    }

    @PostMapping("/reset-password")
    public void resetUserPassword(@RequestParam String token, @RequestBody PasswordResetDTO passwordResetDTO){
        passwordResetService.resetUserPassword(token,passwordResetDTO);
    }


}

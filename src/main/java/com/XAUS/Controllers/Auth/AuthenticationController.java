package com.XAUS.Controllers.Auth;

import com.XAUS.DTOS.Auth.LoginRequestDTO;
import com.XAUS.DTOS.Auth.LoginResponseDTO;
import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.DTOS.Auth.ValidateTokenDTO;
import com.XAUS.Exceptions.XausException;
import com.XAUS.Models.User.User;
import com.XAUS.Services.Auth.AuthorizationService;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;



    //(POST) localhost:8080/auth/login

    //=-===-=-= Usuário de teste (ADMIN)-=-=--==-

//
//    {
//        "email": "testeXAUS@gmail.com",
//            "password": "Teste2@"
//    }

    //=-==-=-=-Usuário de teste (SALES) -=-=-=-

//    {
//        "email": "testeXAUSALES@gmail.com",
//            "password": "123456"
//    }

    //=-==-=-=-Usuário de teste (PACKAGER) -=-=-=-

//    {
//        "email": "testeXAUSPackager@gmail.com",
//            "password": "123456"
//    }


    @PostMapping("/login")

    public ResponseEntity login(@RequestBody LoginRequestDTO data){

        var emailAndPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(emailAndPassword);
        List<String> userRoles = auth.getAuthorities().stream().map(role -> role.getAuthority().replace("ROLE_", "")).toList();
        var token = authorizationService.generateToken((User) auth.getPrincipal() );

        return ResponseEntity.ok(new LoginResponseDTO(token, userRoles));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRequestDTO userData) {

        userService.registerUser(userData);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
        public ResponseEntity validateToken (@RequestBody String token){
        User user = authorizationService.validateToken(token);

            if(user == null){
                throw new XausException("Invalid token!",HttpStatus.BAD_REQUEST);
            }
            else{
                List<String> userRoles = user.getAuthorities().stream().map(role -> role.getAuthority().replace("ROLE_", "")).toList();
                return ResponseEntity.ok(new ValidateTokenDTO(userRoles, user.getName(), user.getId()  ) );
            }
        }



    }

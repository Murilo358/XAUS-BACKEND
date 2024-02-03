package com.XAUS.Controllers.Auth;

import com.XAUS.DTOS.Auth.LoginRequestDTO;
import com.XAUS.DTOS.Auth.LoginResponseDTO;
import com.XAUS.DTOS.Auth.ValidateTokenDTO;
import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.Models.User.User;
import com.XAUS.Services.Auth.AuthorizationService;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//            "password": "123456"
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
        var userRole = auth.getAuthorities();


        //Gera o token pro usuário e retorna
        //Get principal() pega o objeto principal instanciado, ou seja o usuário e da um cast pra user (User) , pois o generate token espera um usuário como parametro
        var token = authorizationService.generateToken((User) auth.getPrincipal() );

        //Retorna uma response com ok, e o token utilizando o LoginResponseDto
        return ResponseEntity.ok(new LoginResponseDTO(token, userRole));

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
                return ResponseEntity.badRequest().build();
            }
            else{
                return ResponseEntity.ok(new ValidateTokenDTO(user.getAuthorities(), user.getName(), user.getId()  ) );
            }
        }



    }

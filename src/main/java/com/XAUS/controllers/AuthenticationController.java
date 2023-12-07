package com.XAUS.controllers;

import com.XAUS.DTOS.LoginRequestDTO;
import com.XAUS.DTOS.LoginResponseDTO;
import com.XAUS.DTOS.UserRequestDTO;
import com.XAUS.DTOS.ValidateTokenDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Models.User;
import com.XAUS.Models.UserRole;
import com.XAUS.Repositories.UserRepository;
import com.XAUS.SecurityConfig.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController

@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;


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
//        "email": "testeXAUSPACKAGER@gmail.com",
//            "password": "123456"
//    }


    @PostMapping("/login")

    public ResponseEntity login(@RequestBody LoginRequestDTO data){

        var emailAndPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(emailAndPassword);
        var userRole = auth.getAuthorities();


        //Gera o token pro usuário e retorna
        //Get principal() pega o objeto principal instanciado, ou seja o usuário e da um cast pra user (User) , pois o generate token espera um usuário como parametro
        var token = tokenService.generateToken((User) auth.getPrincipal() );

        //Retorna uma response com ok, e o token utilizando o LoginResponseDto
        return ResponseEntity.ok(new LoginResponseDTO(token, userRole));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRequestDTO data) {

        User alreadyAddedEmail = userRepository.findByEmail(data.email());
        User alreadyAddedCPF = userRepository.findByCPF(data.cpf());

        if(alreadyAddedEmail != null){
            throw new CustomException("Email ja cadastrado", HttpStatus.BAD_REQUEST);
        }

        if(alreadyAddedCPF != null){
            throw new CustomException("CPF ja cadastrado", HttpStatus.BAD_REQUEST);
        }
        if(!Pattern.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}", data.cpf())){
            throw new CustomException("CPF invalido! formato aceito: xxx.xxx.xxx-xx", HttpStatus.BAD_REQUEST);
        }
        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", data.email())){
            throw new CustomException("Email invalido!", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = passwordEncoder.encode(data.password());

        User userData = new User(data);
        userData.setPassword(hashedPassword);

        this.userRepository.save(userData);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity changeUserRole(@PathVariable Long id, @RequestBody ClientsRequestDTO newData){
//        return this.clientsService.updateClient(id, newData);
//    }
    @PostMapping("/validate")
        public ResponseEntity validateToken (@RequestBody String token){
            String validatedToken = tokenService.validateToken(token);
            User user = userRepository.findByEmail(validatedToken);

            if(validatedToken.isBlank()){
                return ResponseEntity.badRequest().build();
            }
            else{
                return ResponseEntity.ok(new ValidateTokenDTO(user.getAuthorities(), user.getName(), user.getId()  ) );
            }
        }

    @GetMapping("/allRoles")
    public UserRole[] getAllAvailableRoles(){
       return UserRole.values();


    }

    }

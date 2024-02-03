package com.XAUS.Services.User;

import com.XAUS.DTOS.Auth.PasswordResetDTO;
import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.Exceptions.CustomException;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Models.User.User;
import com.XAUS.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserRole getUserRole(String userEmail){

        User foundedUser = userRepository.findByEmail(userEmail);

        return foundedUser.getRole();

    }

    public void resetUserPassword(User user, PasswordResetDTO passwordResetDTO){
        validatePassword(passwordResetDTO.newPassword());
        String hashedPassword = passwordEncoder.encode(passwordResetDTO.newPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

    }

    public User findUserByEmail(String userEmail){
        return userRepository.findByEmail(userEmail);
    }

    public void checkExistentEmail(String email){

        User alreadyAddedEmail = userRepository.findByEmail(email);
        if(alreadyAddedEmail != null){
            throw new CustomException("Email ja cadastrado", HttpStatus.BAD_REQUEST);
        }


    }
    public void checkExistentCpf(String cpf){
        User alreadyAddedCPF = userRepository.findByCPF(cpf);

        if(alreadyAddedCPF != null){
            throw new CustomException("CPF ja cadastrado", HttpStatus.BAD_REQUEST);
        }
    }

    public void verifyEmailAndCPF(String cpf, String userEmail){

        if(!Pattern.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}", cpf)){
            throw new CustomException("CPF invalido! formato aceito: xxx.xxx.xxx-xx", HttpStatus.BAD_REQUEST);
        }
        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", userEmail)){
            throw new CustomException("Email inválido!", HttpStatus.BAD_REQUEST);
        }

    }

//    public static void main(String[] args) {
//        validatePassword("Teste2@");
//    }

    public static void validatePassword(String password){

        if(!Pattern.matches( "^(?=.*[0-9])"+
                 "(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,20}$", password)){
            throw new CustomException("A senha precisa conter no mínimo 6 caracteres, um caractere maiúsculo, e um caractere especial ", HttpStatus.BAD_REQUEST);
        }


    }

    public void registerUser(UserRequestDTO userData){

        checkExistentEmail(userData.email());
        checkExistentCpf(userData.cpf());
        verifyEmailAndCPF(userData.cpf(), userData.email());
        validatePassword(userData.password());
        String hashedPassword = passwordEncoder.encode(userData.password());

        User user = new User(userData);
        user.setPassword(hashedPassword);

        this.userRepository.save(user);
    }


}

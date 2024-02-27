package com.XAUS.Services.User;

import com.XAUS.DTOS.Auth.PasswordResetDTO;
import com.XAUS.DTOS.Users.UpdateUserDTO;
import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.Exceptions.XausException;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Models.User.User;
import com.XAUS.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
            throw new XausException("Email já cadastrado", HttpStatus.BAD_REQUEST);
        }


    }
    public void checkExistentCpf(String cpf){
        User alreadyAddedCPF = userRepository.findByCPF(cpf);

        if(alreadyAddedCPF != null){
            throw new XausException("CPF já cadastrado", HttpStatus.BAD_REQUEST);
        }
    }

    public void verifyEmailAndCPF(String cpf, String userEmail){

        if(!Pattern.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}", cpf)){
            throw new XausException("CPF inválido! formato aceito: xxx.xxx.xxx-xx", HttpStatus.BAD_REQUEST);
        }
        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", userEmail)){
            throw new XausException("Email inválido!", HttpStatus.BAD_REQUEST);
        }

    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public static void validatePassword(String password){

        if(!Pattern.matches( """
                ^(?=.*[0-9])\
                (?=.*[A-Z])\
                (?=.*[@#$%^&+=])\
                (?=\\S+$).{6,20}$\
                """, password)){
            throw new XausException("A senha precisa conter no mínimo 6 caracteres, um caractere maiúsculo, e um caractere especial ", HttpStatus.BAD_REQUEST);
        }


    }

    public void registerUser(UserRequestDTO userData){

        checkExistentEmail(userData.email());
        checkExistentCpf(userData.cpf());
        verifyEmailAndCPF(userData.cpf(), userData.email());
        validatePassword(userData.password());
        String hashedPassword = passwordEncoder.encode(userData.password());

        User user = new User(userData, true);
        user.setPassword(hashedPassword);
        this.userRepository.save(user);
    }

    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(u -> u.setPassword(null));
        return allUsers;
    }


    public void updateUser(Long userId, UpdateUserDTO userData){

        User user = userRepository.findById(userId).orElseThrow(()->  new XausException("Usuário não encontrado", HttpStatus.NOT_FOUND));
        user.setName(userData.name());
        user.setCpf(userData.cpf());
        user.setEmail(userData.email());
        user.setRole(UserRole.valueOf(userData.role()));
        user.setEnabled(userData.enabled());
        userRepository.save(user);

    }

    public void verifyIfItsTheLastAdmin(){
        int count = userRepository.countByRole(String.valueOf(UserRole.ADMIN.ordinal()));
        if (count <= 1){
            throw new XausException("Its needed at least one admin user!", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteUser(Long userId){
        verifyIfItsTheLastAdmin();
        userRepository.deleteById(userId);
    }
    public List<String> formatUserRoles(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream().map(role -> role.getAuthority().replace("ROLE_", "")).toList();
    }

}

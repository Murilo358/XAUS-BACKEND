package com.XAUS.Controllers.User;

import com.XAUS.DTOS.Users.UpdateUserDTO;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Models.User.User;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/allRoles")
    public UserRole[] getAllAvailableRoles(){
        return UserRole.values();
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO userData){
        userService.updateUser(id, userData);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}

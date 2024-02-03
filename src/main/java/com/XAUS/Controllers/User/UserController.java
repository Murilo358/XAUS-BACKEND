package com.XAUS.Controllers.User;

import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/allRoles")
    public UserRole[] getAllAvailableRoles(){
        return UserRole.values();


    }

    public UserRole getUserRoleByEmail(String userEmail){
        return userService.getUserRole(userEmail);
    }


}

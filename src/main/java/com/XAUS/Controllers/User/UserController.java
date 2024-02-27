package com.XAUS.Controllers.User;

import com.XAUS.DTOS.Users.UpdateUserDTO;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Models.User.User;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/allRoles")
    public ResponseEntity<UserRole[]> getAllAvailableRoles(){
        return ResponseEntity.ok(UserRole.values());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO userData){
        userService.updateUser(id, userData);
        return ResponseEntity.ok("User with id: " + id + " updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id: " + id + " deleted successfully!");
    }
}

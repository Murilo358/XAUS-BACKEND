package com.XAUS.Services.WebSocket;

import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WebSocketService {

    @Autowired
    SimpUserRegistry simpUserRegistry;

    @Autowired
    UserService userService;

    public List<SimpUser> getConnectionByRole(UserRole[] userRoleToCheck){
        List<SimpUser> foundUsers = new ArrayList<SimpUser>();

        for (SimpUser simpUser : simpUserRegistry.getUsers()) {

            UserRole userRole = userService.getUserRole(simpUser.getName());

            if(Arrays.stream(userRoleToCheck).anyMatch(s-> userRole == s)){
                foundUsers.add(simpUser);
            }

        }

        return foundUsers;
    }

    public SimpUser getSimpUserByNameAndRole(String name, UserRole[] userRoleToCheck){

        UserRole userRole = userService.getUserRole(name);

        if(Arrays.stream(userRoleToCheck).anyMatch(s-> userRole == s)){
            return simpUserRegistry.getUser(name);
        }

        return null;

    }


}

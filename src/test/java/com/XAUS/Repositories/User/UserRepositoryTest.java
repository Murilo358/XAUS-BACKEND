package com.XAUS.Repositories.User;

import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Models.User.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should get count by a role user passed in")
    void countByRoleSuccess() {
        UserRole userRole = UserRole.ADMIN;
        UserRequestDTO data = new UserRequestDTO("Teste", "458.388.978-05",
        "murilobarbosa358@,gmail.com", new Date(), "123456", userRole);
        this.createUser(data);

        int countUsers =  this.userRepository.countByRole(String.valueOf(userRole.ordinal()));

        assertThat(countUsers).isNotZero();
    }

    public User createUser(UserRequestDTO data){
        User newUser = new User(data, true);
        this.entityManager.persist(newUser);
        return newUser;
    }
}
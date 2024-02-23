package com.XAUS.Repositories.User;

import com.XAUS.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    public User findByEmail (@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE cpf = :cpf", nativeQuery = true)
    public User findByCPF (@Param("cpf") String cpf);

    @Query(value = "SELECT  COUNT(*) FROM users WHERE role = :role", nativeQuery = true)
    public int countByRole(@Param("role") String role);
}

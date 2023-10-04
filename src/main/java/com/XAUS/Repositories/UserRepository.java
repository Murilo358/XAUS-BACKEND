package com.XAUS.Repositories;

import com.XAUS.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

//JpaRepository já trás todos os métodos de acesso ao db sem ter que implementar nada, alem do tipo do repository que ela retorna
//E o tipo do id

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    public User findByEmail (@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE cpf = :cpf", nativeQuery = true)
    public User findByCPF (@Param("cpf") String cpf);
}

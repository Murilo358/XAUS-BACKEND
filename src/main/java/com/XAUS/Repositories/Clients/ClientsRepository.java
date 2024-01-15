package com.XAUS.Repositories.Clients;


import com.XAUS.Models.Clients.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository  extends JpaRepository<Clients,Long> {

    @Query(value="SELECT * FROM clients WHERE CPF = :cpf", nativeQuery = true)
    public Clients findbyCPF (@Param("cpf") String cpf);

    @Query(value="SELECT * FROM clients WHERE email = :email", nativeQuery = true)
    public Clients findbyEmail (@Param("email") String email);


}

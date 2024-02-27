package com.XAUS.Models.Clients;

import com.XAUS.DTOS.Clients.ClientsRequestDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.util.Date;

@Table(name = "clients")
@Entity(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "email")
    private String email;

    @Column(name= "name")
    private String name;

    @Column(name = "cpf")
    private  String cpf;


    public Clients(ClientsRequestDTO data) {
        this.email = data.email();
        this.cpf = data.cpf();
        this.name = data.name();

    }

}

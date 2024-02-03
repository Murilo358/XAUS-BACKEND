package com.XAUS.Models.User;


import com.XAUS.DTOS.Users.UserRequestDTO;
import com.XAUS.Models.User.Enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Table(name = "Users")
@Entity(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "password")
    private String password;

    @Column(name= "role")
    private UserRole role;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public User(UserRequestDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.birthDate = data.birthDate();
        this.cpf = data.cpf();
        this.password = data.password();
        this.role = data.role();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch(this.role){

        case ADMIN:
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_PACKAGER"),new SimpleGrantedAuthority("ROLE_SALES") );

            case SALES:
            return  List.of(new SimpleGrantedAuthority("ROLE_SALES"));

            default: return List.of(new SimpleGrantedAuthority("ROLE_PACKAGER"));
        }

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

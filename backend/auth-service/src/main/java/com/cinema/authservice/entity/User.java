package com.cinema.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_cinema")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String password;
    private String email;
    private String address;
    private String contact;
    @Enumerated(EnumType.STRING)
    private  Role role;

    @OneToMany(mappedBy = "user" )
    private List<Token> tokens;

    @OneToMany(mappedBy = "user")
    private List<ForgetPasswordToken> ForgetPasswordTokens;

    @OneToMany(mappedBy = "user")
    private List<MovieTime> movieTimes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
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

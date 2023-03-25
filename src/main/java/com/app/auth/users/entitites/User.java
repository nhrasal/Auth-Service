package com.app.auth.users.entitites;

import com.app.auth.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(length = 50, nullable = true)
    private String firstName;

    @Column(length = 50, nullable = true)
    private String lastName;

    @Column(length = 100, nullable = true)
    private String email;

    @JsonIgnore
    @Column(length = 100)
    private String password;

    @Column(length = 20, nullable = true)
    private String phone;

    @Column(length = 100, nullable = true)
    private String image;

    @Column()
    private Boolean isVerified=false;

    @OneToMany(mappedBy = "user")
    private List<UserRole> roles;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority('super-admin'));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
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

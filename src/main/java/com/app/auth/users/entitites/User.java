package com.app.auth.users.entitites;

import com.app.auth.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

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
}

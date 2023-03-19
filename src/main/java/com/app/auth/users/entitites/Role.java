package com.app.auth.users.entitites;

import com.app.auth.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(length = 50)
    private String title;

    @Column(length = 50,unique = true)
    private String slug;
    @Column(length = 50)
    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

}

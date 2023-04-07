package com.app.auth.users.entitites;

import com.app.auth.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_permissions")
public class RolePermission extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Permission permission;

}

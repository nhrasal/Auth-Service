package com.app.auth.users.entitites;

import com.app.auth.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_permissions")
public class UserPermission extends BaseEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Permission permission;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean hasPermitted = true;
}

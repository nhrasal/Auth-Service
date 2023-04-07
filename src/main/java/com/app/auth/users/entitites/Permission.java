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
@Table(name = "permissions")
public class Permission extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name="parent_id",nullable = true)
    private Permission parent;
    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 50, unique = true)
    private String slug;
    @Column(length = 50)
    private String description;
}

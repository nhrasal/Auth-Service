package com.app.auth.users.entitites;

import com.app.auth.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_verifications")
public class Verification extends BaseEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable =true )
    String code;

    @Column(nullable = true)
    String type;

    @Column(nullable = true)
    String message;

    @Column()
    Date expireAt;

    @Column()
    String token;

    @Column()
    Boolean isVerified = false;
}

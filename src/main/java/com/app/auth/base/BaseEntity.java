package com.app.auth.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;




@Component
@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;


    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean isActive = true;

    @Column(nullable = true, updatable = false)
    private UUID createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private Date createdOn;

    @Column()
    private UUID updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @UpdateTimestamp
    @JsonIgnore
    private Date updatedOn;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean isDeleted = false;
    
}

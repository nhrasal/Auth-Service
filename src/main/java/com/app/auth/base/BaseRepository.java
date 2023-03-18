package com.app.auth.base;

import com.app.auth.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, UUID>, PagingAndSortingRepository<E, UUID>, JpaSpecificationExecutor<E> {

    Optional<E> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    List<E> findByIdInAndIsDeleted(Set<UUID> ids, boolean isDeleted);

    Page<E> findByIsDeleted(boolean isDeleted, Pageable pageable);

    Page<E> findByIdInAndIsDeleted(Set<UUID> ids, boolean isDeleted, Pageable pageable);
}
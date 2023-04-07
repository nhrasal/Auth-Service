package com.app.auth.base;

import com.app.auth.base.dtos.IdHolderRequestBodyDTO;
import com.app.auth.exceptions.ServiceExceptionHolder;
import com.app.auth.response.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
@Data
public abstract class BaseService<E extends BaseEntity, D extends IdHolderRequestBodyDTO> {


    @Autowired
    ObjectMapper objectMapper;

    private final BaseRepository<E> repository;

    private final ModelMapper modelMapper;


    @SuppressWarnings("unchecked")
    public AppResponse<List<D>> getAll() {
        List<E> results = repository.findAll();
        return AppResponse.build(HttpStatus.OK).body(convertForRead(results));
    }

    public AppResponse<D> findSingle(UUID id) {
        E results = getEntityById(id);
        return AppResponse.build(HttpStatus.OK).body(convertForRead(results));
    }


    protected D convertForRead(E e) {
        return modelMapper.map(e, getDtoClass());
    }

    protected List<D> convertForRead(List<E> e) {
        return e.stream().map(this::convertForRead).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Class<D> getDtoClass() {
        return (Class<D>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    public Optional<E> getOptionalEntity(@NonNull UUID id) {
        return repository.findByIdAndIsDeleted(id, false);
    }

    protected E getEntityById(@NonNull UUID id) {
        return getOptionalEntity(id).orElseThrow(() -> new RuntimeException("Nothing found with id=" + id));
    }


//    Create or store start

    public D store(D dto) {
        return convertForRead(createEntity(dto));
    }

    public E createEntity(D dto) {
        if (!isValidWhileCreate(dto))
            throw new ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException(
                    "Sorry, can't create this time.");
        E entity = convertForCreate(dto);
        E createdEntity = repository.save(entity);
        return createdEntity;
    }


    protected E convertForCreate(D d) {
        E e = null;
        try {
            e = getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        BeanUtils.copyProperties(d, e, getNullPropertyNames(d));
//        copyNonNullProperties(d, e);
        return e;
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        return (Class<E>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected boolean isValidWhileCreate(D dto) {
        return true;
    }

    //Create or store end

    protected E putBaseEntityDetailsForUpdate(E entity) {
//        entity.setUpdatedBy(getLoggedInUserId());
        entity.setUpdatedOn(new Date());
        entity.setIsDeleted(false);
        return entity;
    }

    public D update(D dto) {
        if (!isValidWhileUpdate(dto))
            throw new ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException("Sorry, nothing found to be updated.");
        ;
        E entity = putBaseEntityDetailsForUpdate(convertForUpdate(dto, getEntityById(dto.getId())));
        E updatedEntity = repository.save(entity);
        postUpdate(dto, updatedEntity);
        return convertForRead(updatedEntity);
    }

    public List<D> update(List<D> dtos) {
        List<E> updatedEntities = new ArrayList<>();
        E e;
        for (D dto : dtos) {
            if (!isValidWhileUpdate(dto)) continue;
            e = putBaseEntityDetailsForUpdate(convertForUpdate(dto, getEntityById(dto.getId())));
            updatedEntities.add(e);
        }
        updatedEntities = repository.saveAll(updatedEntities);
        postUpdateAll(dtos, updatedEntities);
        return updatedEntities.stream().map(o -> convertForRead(o)).collect(Collectors.toList());
    }

    public E updateEntity(E entity) {
        return repository.save(putBaseEntityDetailsForUpdate(entity));
    }

    public List<E> updateEntities(List<E> entities) {
        return repository.saveAll(entities.stream().map(e -> putBaseEntityDetailsForUpdate(e)).collect(Collectors.toList()));
    }

    protected boolean isValidWhileUpdate(D dto) {
        if (dto.getId() == null) return false;
        return true;
    }

    protected E convertForUpdate(D d, E e) {
        copyNonNullProperties(d, e);
        return e;
    }

    protected void postUpdateAll(List<D> dtos, List<E> entities) {
    }

    protected void postUpdate(D dto, E entity) {
    }


    //end update

    //    start delete


    public D delete(E entity) {
        return convertForRead(repository.save(putBaseEntityDetailsForDelete(entity)));
    }

    public D delete(UUID uuid) {
        return delete(getEntityById(uuid));
    }

    public List<D> delete(List<E> entities) {
        entities = entities.stream().map(e -> putBaseEntityDetailsForDelete(e)).collect(Collectors.toList());
        return convertForRead(repository.saveAll(entities));
    }

    public List<D> delete(Set<UUID> uuidSet) {
        return delete(getListOfEntitiesByIdSet(uuidSet));
    }

    public E deleteEntity(E entity) {
        return repository.save(putBaseEntityDetailsForDelete(entity));
    }

    public List<E> deleteEntities(List<E> entities) {
        entities = entities.stream().map(e -> putBaseEntityDetailsForDelete(e)).collect(Collectors.toList());
        return repository.saveAll(entities);
    }

    private E putBaseEntityDetailsForDelete(E entity) {
//        entity.setUpdatedBy(getLoggedInUserId());
        entity.setUpdatedOn(new Date());
        entity.setIsDeleted(true);
        return entity;
    }

    /**
     * Copies properties from one object to another
     *
     * @param source
     * @return
     * @destination
     */
    public void copyNonNullProperties(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    /**
     * Returns an array of null properties of an object
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    protected List<E> getListOfEntitiesByIdSet(Set<UUID> uuidSet) {
        return repository.findAllById(uuidSet)
                .stream()
                .filter(e -> !e.getIsDeleted())
                .collect(Collectors.toList());
    }


}

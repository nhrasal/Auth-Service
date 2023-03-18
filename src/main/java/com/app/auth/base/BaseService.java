package com.app.auth.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.app.auth.base.dtos.IdHolderRequestBodyDTO;
import com.app.auth.exceptions.ServiceExceptionHolder;
import com.app.auth.response.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



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
        BeanUtils.copyProperties(d, e);
        return e;
    }
    
      @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        return (Class<E>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    
    protected boolean isValidWhileCreate(D dto) {
        return true;
    }



    

}

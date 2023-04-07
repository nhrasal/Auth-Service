package com.app.auth.base;

import com.app.auth.base.dtos.IdHolderRequestBodyDTO;
import com.app.auth.response.AppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
public class BaseController<E extends BaseEntity, D extends IdHolderRequestBodyDTO> {

    public final BaseService<E, D> service;


    @GetMapping
    public AppResponse getAll() {
        try {
            return service.getAll();
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @GetMapping("{id}")
    public AppResponse getSingle(@PathVariable("id") UUID id) {
        try {
            return service.findSingle(id);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PostMapping()
    public AppResponse createRole(@Valid @RequestBody D requestData) {
        try {
//            requestData.setIsActive(true);
            D responseData = service.store(requestData);
            return AppResponse.build(HttpStatus.CREATED).message("Create successfully!.").body(responseData);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public AppResponse updateRole(@PathVariable("id") UUID id, @Valid @RequestBody D requestData) {
        try {
            requestData.setId(id);
            D responseData = service.update(requestData);
            return AppResponse.build(HttpStatus.OK).message("Updated successfully!.").body(responseData);
        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public AppResponse delete(@PathVariable("id") UUID id) {
        try {
            D responseData= service.delete(id);
            return AppResponse.build(HttpStatus.OK).message("Removeds successfully!.");

        } catch (Exception ex) {
            return AppResponse.build(HttpStatus.INTERNAL_SERVER_ERROR).message(ex.getMessage());
        }
    }


}

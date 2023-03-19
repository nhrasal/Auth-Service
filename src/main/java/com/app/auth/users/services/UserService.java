package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.response.AppResponse;
import com.app.auth.users.dto.UserDTO;
import com.app.auth.users.entitites.Role;
import com.app.auth.users.entitites.User;
import com.app.auth.users.entitites.UserRole;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.repositories.UserRoleRepository;
import com.app.auth.users.request.SignupRequest;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@SuppressWarnings("unused")
@Slf4j
@Service
public class UserService extends BaseService<User, UserDTO> {

    @Autowired
    private UserRepository repository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleRepository userRoleRepository;

    /**
     * @param repository
     * @param modelMapper
     */
    public UserService(UserRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
        // super(repository, modelMapper);
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public AppResponse<List<User>> getAllUser() {
        List<User> users = repository.findAll();
        return AppResponse.build(HttpStatus.OK).body(users);
    }

    @Transactional
    public AppResponse signup(SignupRequest requestData) {

        if (requestData.getEmail().trim().isEmpty()) {
            return AppResponse.build(HttpStatus.NOT_ACCEPTABLE).message("First name is required!");
        }

        Optional<User> findUser = repository.findByEmail(requestData.getEmail());
        if (findUser.isPresent())
            return AppResponse.build(HttpStatus.CONFLICT).message("This email address already exist!");
        User user = new User();
        user.setFirstName(requestData.getFirstName().trim());
        user.setLastName(requestData.getLastName().trim());
        user.setPhone(requestData.getPhone().trim());
        user.setEmail(requestData.getEmail().trim());
        user.setPassword(requestData.getPassword().trim());
        user.setIsActive(true);
        user.setIsDeleted(false);
        User storeUser = repository.save(user);

        if (storeUser.getId() != null) {
            Optional<Role> role = roleService.findBySlug("super-admin");
            if (role.isPresent()) {
                UserRole userRole = new UserRole();
                userRole.setUser(storeUser);
                userRole.setRole(role.get());
                UserRole usrRole=userRoleRepository.save(userRole);
                return AppResponse.build(HttpStatus.OK).body(storeUser).message("Registration has been completed successfully");
            }
        }
        return AppResponse.build(HttpStatus.BAD_REQUEST).message("Registration failed!");

    }
}

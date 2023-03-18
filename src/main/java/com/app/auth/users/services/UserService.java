package com.app.auth.users.services;

import com.app.auth.base.BaseService;
import com.app.auth.response.AppResponse;
import com.app.auth.users.dto.UserDTO;
import com.app.auth.users.entitites.User;
import com.app.auth.users.repositories.UserRepository;
import com.app.auth.users.request.SignupRequest;
import java.util.List;
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
   
    
    public User signup(SignupRequest requestData) {
        // return requestData;
        User user = new User();
        user.setFirstName(requestData.getFirstName());
        user.setLastName(requestData.getLastName());
        user.setPhone(requestData.getPhone());
        user.setEmail(requestData.getEmail());
        user.setPassword(requestData.getPassword());
        repository.save(user);
        return user;
        // user.setUser
    }
}

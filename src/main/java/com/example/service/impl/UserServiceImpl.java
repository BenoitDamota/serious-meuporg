package com.example.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ConstanteGameMaster;
import com.example.dto.UserDTO;
import com.example.exception.GameMasterException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.transformers.Transformers;

/**
 * Created by Morgane TROYSI on 16/05/17.
 */

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private Transformers transformers;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> loadUsers(String query) {
        List<User> users = userRepository.findByFirstNameContainingOrLastNameContaining(query, query);
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = (UserDTO) transformers.convertEntityToDto(user, UserDTO.class);
            userDTO.setFullName(user.getFirstName() + ' ' + user.getLastName());
            result.add(userDTO);
        }
        return result;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO){
        User user = (User)transformers.convertDtoToEntity(userDTO, User.class);
        User userTest = userRepository.findByEmail(user.getEmail());
        if (userTest != null){
        	throw new GameMasterException(ConstanteGameMaster.SIGNUP_ERROR);
        }else{
            // TO DO : chiffrer le password
        	user.setId(null);
            user.setDateCreation(Calendar.getInstance().getTime());
            userRepository.save(user);
        }
        return (UserDTO)transformers.convertEntityToDto(user, UserDTO.class);
    }
}

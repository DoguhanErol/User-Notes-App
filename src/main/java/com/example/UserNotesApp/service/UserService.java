package com.example.UserNotesApp.service;

import com.example.UserNotesApp.dto.UserDto;
import com.example.UserNotesApp.model.User;
import com.example.UserNotesApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    //Get All Users | Admin
    public List<UserDto> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        List<UserDto> allUsersDto = allUsers.stream()
                .map(user -> modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
        return  allUsersDto;
    }
    
    //Create User | Unknown
    public UserDto postUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
    //Delete User By Id | Admin
    //Bir ara degistir optional yap yok ise
    public Boolean delUser(Long id){
        try {
            userRepository.deleteById(id);
        }catch (Exception ex){
            return false;
        }
        return true;
    }
    // Update User Information | User
    public UserDto updateUser(Long id, UserDto updatedUserDto){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            existingUser.setUserName(updatedUserDto.getUserName());
            existingUser.setPassword(updatedUserDto.getPassword());
            User updatedUser = userRepository.save(existingUser);
            return modelMapper.map(updatedUser, UserDto.class);
        }else {
            throw new ObjectNotFoundException(optionalUser,"User not found!!!");
        }
    }

}

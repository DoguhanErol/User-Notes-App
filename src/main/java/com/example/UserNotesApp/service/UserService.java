package com.example.UserNotesApp.service;

import com.example.UserNotesApp.dto.UserDto;
import com.example.UserNotesApp.exceptions.BadRequestException;
import com.example.UserNotesApp.exceptions.SystemException;
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
            try {
                List<User> allUsers = userRepository.findAll();
                List<UserDto> allUsersDto = allUsers.stream()
                        .map(user -> modelMapper.map(user, UserDto.class))
                        .collect(Collectors.toList());
                return allUsersDto;
            }catch (Exception ex){
                throw new SystemException("System error occurred while fetching all users");
            }
    }
    //Get user by id
    public UserDto getUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        try{
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                return modelMapper.map(existingUser, UserDto.class);
            }else {
                throw new BadRequestException("There is no user with this id number");
            }
        }catch (BadRequestException ex) {
            throw ex;
        } catch (Exception ex){
            throw new SystemException("An unexpected error occurred while retrieving the user");
        }
    }
    //Create User | Unknown
    public UserDto postUser(UserDto userDto){
        try{
            if (userDto.getUserName() == null || userDto.getUserName().isEmpty() ||userDto.getPassword() == null || userDto.getPassword().isEmpty() ) {
                throw new BadRequestException("Username and Password can`t be empty.");
            }
            User user = modelMapper.map(userDto, User.class);
            User savedUser = userRepository.save(user);
            return modelMapper.map(savedUser, UserDto.class);
        }catch (BadRequestException ex) {
            throw ex;
        }
        catch (Exception ex){
            throw new SystemException("An unexpected error occurred while creating the user");
        }
    }
    //Delete User By Id | Admin
    //Bir ara degistir optional yap yok ise
    public Boolean delUser(Long id){
        try {
            if(userRepository.existsById(id)){
                userRepository.deleteById(id);
            }else{
                throw new BadRequestException("There is no user with this id number");
            }
        }catch (BadRequestException ex){
            throw ex;
        }
        catch (Exception ex){
            throw new SystemException("An unexpected error occurred while deleting the user");
        }
        return true;
    }
    // Update User Information | User
    public UserDto updateUser(Long id, UserDto updatedUserDto){
        if (updatedUserDto.getUserName() == null || updatedUserDto.getUserName().isEmpty() ||updatedUserDto.getPassword() == null || updatedUserDto.getPassword().isEmpty() ) {
            throw new BadRequestException("Username and Password can`t be empty.");
        }
        try{
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()){
                User existingUser = optionalUser.get();
                existingUser.setUserName(updatedUserDto.getUserName());
                existingUser.setPassword(updatedUserDto.getPassword());
                User updatedUser = userRepository.save(existingUser);
                return modelMapper.map(updatedUser, UserDto.class);
            }else {
                throw new BadRequestException("There is no user with this id number");
            }
        }catch (BadRequestException ex){
            throw ex;
        }catch (Exception ex){
            throw new SystemException("An unexpected error occurred while updating the user");
        }
    }

}

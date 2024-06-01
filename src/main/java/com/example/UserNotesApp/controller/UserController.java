package com.example.UserNotesApp.controller;

import com.example.UserNotesApp.dto.UserDto;
import com.example.UserNotesApp.model.User;
import com.example.UserNotesApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {


    private final UserService userService;
    public UserController (UserService userService){
        this.userService = userService;
    }

    //Get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //Create a user
    @PostMapping
    public  ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.postUser(userDto));
    }

    //Delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.delUser(id));
    }

    //Update user by id
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        UserDto updatedUserDto = userService.updateUser(id,userDto);
        if (updatedUserDto != null){
            return ResponseEntity.ok(updatedUserDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

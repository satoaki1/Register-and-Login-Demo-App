package com.example.randlandmanageuser.service;

import com.example.randlandmanageuser.entity.dto.UserDto;
import com.example.randlandmanageuser.entity.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}

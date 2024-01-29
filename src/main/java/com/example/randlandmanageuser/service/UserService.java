package com.example.randlandmanageuser.service;

import com.example.randlandmanageuser.dto.UserDto;
import com.example.randlandmanageuser.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}

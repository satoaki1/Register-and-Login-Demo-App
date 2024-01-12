package com.example.randlandmanageuser.service;


import com.example.randlandmanageuser.entity.dto.UserDto;
import com.example.randlandmanageuser.entity.model.Role;
import com.example.randlandmanageuser.entity.model.User;
import com.example.randlandmanageuser.entity.repository.RoleRepository;
import com.example.randlandmanageuser.entity.repository.UserRepository;
import com.example.randlandmanageuser.exception.DuplicateUserException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @CacheEvict(value = {"users", "userByEmail"}, allEntries = true)
    public void saveUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateUserException("There is already an account registered with the same email");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new DuplicateUserException("There is already an account registered with the same username");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }

        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "userByEmail", key = "#email")
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Cacheable("users")
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}


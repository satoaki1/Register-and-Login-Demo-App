package com.example.randlandmanageuser;

import com.example.randlandmanageuser.dto.UserDto;
import com.example.randlandmanageuser.entity.Role;
import com.example.randlandmanageuser.entity.User;
import com.example.randlandmanageuser.repository.RoleRepository;
import com.example.randlandmanageuser.repository.UserRepository;
import com.example.randlandmanageuser.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("noahsark");
        userDto.setEmail("noahsark@noahsark.com");
        userDto.setPassword("noahsark");

        Role mockRole = new Role();
        mockRole.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(mockRole);

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        userServiceImpl.saveUser(userDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

}
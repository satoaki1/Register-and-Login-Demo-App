package com.example.randlandmanageuser;

import com.example.randlandmanageuser.controller.MainController;
import com.example.randlandmanageuser.entity.repository.RoleRepository;
import com.example.randlandmanageuser.entity.repository.UserRepository;
import com.example.randlandmanageuser.service.CustomUserDetailsService;
import com.example.randlandmanageuser.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MainApplicationTests {

    @Autowired
    private MainController mainController;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void contextLoads() {
        assertThat(mainController).isNotNull();
        assertThat(userServiceImpl).isNotNull();
        assertThat(customUserDetailsService).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(roleRepository).isNotNull();
        assertThat(passwordEncoder).isNotNull();
    }
}

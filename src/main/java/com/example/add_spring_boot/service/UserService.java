package com.example.add_spring_boot.service;


import com.example.add_spring_boot.dto.UserStatus;
import com.example.add_spring_boot.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserStatus getUser(String id);
    List<UserDTO> getAllUsers();
    void saveUser(UserDTO dto);
    void updateUser(String ID, UserDTO dto);
    void deleteUser(String ID);
    UserDetailsService userDetailsService();
}

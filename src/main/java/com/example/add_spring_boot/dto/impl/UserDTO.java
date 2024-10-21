package com.example.add_spring_boot.dto.impl;

import com.example.add_spring_boot.dto.UserStatus;
import com.example.add_spring_boot.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserStatus {
    private String userID;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePic;
    private Role role;
    private List<NoteDTO> noteDTO;
}

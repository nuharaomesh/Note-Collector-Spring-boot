package com.example.add_spring_boot.service;

import com.example.add_spring_boot.dto.impl.UserDTO;
import com.example.add_spring_boot.secure.JWTAuthResponse;
import com.example.add_spring_boot.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDTO userDetails);
    JWTAuthResponse refreshToken(String accessToken);
}

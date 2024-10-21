package com.example.add_spring_boot.service.impl;

import com.example.add_spring_boot.dao.UserDAO;
import com.example.add_spring_boot.dto.impl.UserDTO;
import com.example.add_spring_boot.entity.impl.User;
import com.example.add_spring_boot.secure.JWTAuthResponse;
import com.example.add_spring_boot.secure.SignIn;
import com.example.add_spring_boot.service.AuthService;
import com.example.add_spring_boot.service.JWTService;
import com.example.add_spring_boot.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager manager;

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userDAO.findByEmail(signIn.getEmail()).
                orElseThrow(() -> new UsernameNotFoundException("User not Found!"));
        var generatedToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        //Save User
        User savedUser = userDAO.save(mapping.toUserEntity(userDTO));
        //Generate Token & return
        var generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        // Extract User name
        var userName = jwtService.extractUserName(accessToken);
        //Is User Exist
        var user = userDAO.findByEmail(userName).
                orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        var refreshToken = jwtService.refreshToken(user);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}

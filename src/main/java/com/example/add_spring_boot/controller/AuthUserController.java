package com.example.add_spring_boot.controller;

import com.example.add_spring_boot.dto.impl.UserDTO;
import com.example.add_spring_boot.entity.Role;
import com.example.add_spring_boot.exception.DataPersistException;
import com.example.add_spring_boot.secure.JWTAuthResponse;
import com.example.add_spring_boot.secure.SignIn;
import com.example.add_spring_boot.service.AuthService;
import com.example.add_spring_boot.service.UserService;
import com.example.add_spring_boot.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class AuthUserController {

    private final UserService userService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(
            @RequestPart("password") String password,
            @RequestPart ("lastName") String lastName,
            @RequestPart ("firstName") String firstName,
            @RequestPart ("email") String email,
            @RequestPart ("role") String role,
            @RequestPart ("profilePic") MultipartFile profilePic
    ) {

        try {
            byte[] byteProPic = profilePic.getBytes();
            String base64ProPic = AppUtil.profilePicToBase64(byteProPic);

            var buildUserDTO = new UserDTO();

            String userID = AppUtil.generateUserID();
            buildUserDTO.setUserID(userID);
            buildUserDTO.setPassword(passwordEncoder.encode(password));
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setRole(Role.valueOf(role));
            buildUserDTO.setEmail(email);
            buildUserDTO.setProfilePic(base64ProPic);
            buildUserDTO.setNoteDTO(null);
            return ResponseEntity.ok(authService.signUp(buildUserDTO));
        } catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn) {
        return ResponseEntity.ok(authService.signIn(signIn));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponse> refreshToken(@RequestParam("existingToken") String existingToken) {
        return ResponseEntity.ok(authService.refreshToken(existingToken));
    }
}

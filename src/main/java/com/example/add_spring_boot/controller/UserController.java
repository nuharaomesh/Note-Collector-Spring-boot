package com.example.add_spring_boot.controller;

import com.example.add_spring_boot.customStatusCode.SelectedUserErrorStatus;
import com.example.add_spring_boot.dto.UserStatus;
import com.example.add_spring_boot.dto.impl.UserDTO;
import com.example.add_spring_boot.exception.DataPersistException;
import com.example.add_spring_boot.exception.UserNotFoundException;
import com.example.add_spring_boot.service.UserService;
import com.example.add_spring_boot.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public UserStatus getUser(@PathVariable("id") String id) {
        if (id.isEmpty() || id == null)
            return new SelectedUserErrorStatus(1, "User ID is Not Valid!");
        return userService.getUser(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{userID}")
    public ResponseEntity<Void> updateUser(
            @RequestPart ("password") String password,
            @RequestPart ("lastName") String lastName,
            @RequestPart ("firstName") String firstName,
            @RequestPart ("email") String email,
            @RequestPart ("profilePic") MultipartFile profilePic,
            @PathVariable ("userID") String userID
    ) {

        try {
            byte[] byteProPic = profilePic.getBytes();
            String base64ProPic = AppUtil.profilePicToBase64(byteProPic);

            var buildUserDTO = new UserDTO();

            buildUserDTO.setUserID(userID);
            buildUserDTO.setPassword(password);
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setProfilePic(base64ProPic);
            buildUserDTO.setNoteDTO(null);
            userService.updateUser(userID, buildUserDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

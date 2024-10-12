package com.example.add_spring_boot.service.impl;

import com.example.add_spring_boot.customStatusCode.SelectedUserErrorStatus;
import com.example.add_spring_boot.dao.UserDAO;
import com.example.add_spring_boot.dto.UserStatus;
import com.example.add_spring_boot.dto.impl.UserDTO;
import com.example.add_spring_boot.entity.impl.User;
import com.example.add_spring_boot.exception.UserNotFoundException;
import com.example.add_spring_boot.service.UserService;
import com.example.add_spring_boot.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Mapping mapping;


    @Override
    public UserStatus getUser(String id) {
        if (userDAO.existsById(id)) {
            User selectedUser = userDAO.getReferenceById(id);
            return mapping.toUserDTO(selectedUser);
        } else {
            return new SelectedUserErrorStatus(2, "Selected User not found!");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userDAO.findAll();
        return mapping.getUserDTOList(userList);
    }

    @Override
    public void saveUser(UserDTO dto) {
        userDAO.save(mapping.toUserEntity(dto));
    }

    @Override
    public void updateUser(String ID, UserDTO dto) {
        Optional<User> tmpUser = userDAO.findById(ID);
        if (!tmpUser.isPresent())
            throw new UserNotFoundException("Note Not Found!");
        else
            tmpUser.get().setPassword(dto.getPassword());
            tmpUser.get().setFirstName(dto.getFirstName());
            tmpUser.get().setLastName(dto.getLastName());
            tmpUser.get().setEmail(dto.getEmail());
            tmpUser.get().setProfilePic(dto.getProfilePic());
    }

    @Override
    public void deleteUser(String ID) {
        Optional<User> tmpUser = userDAO.findById(ID);
        if (!tmpUser.isPresent()) {
            throw new UserNotFoundException("Note Not Found!");
        } else {
            userDAO.deleteById(ID);
        }
    }
}

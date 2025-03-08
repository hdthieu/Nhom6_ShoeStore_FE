package com.shoestore.client.service;

import com.shoestore.client.dto.request.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<UserDTO> getUserByName(String username);

    void save(UserDTO user);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll();

    // Phương thức để xác thực người dùng qua email và password
    ResponseEntity<UserDTO> authenticateUser(UserDTO userDTO);
}

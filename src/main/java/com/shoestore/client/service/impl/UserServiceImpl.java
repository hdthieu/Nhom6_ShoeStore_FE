package com.shoestore.client.service.impl;

import com.shoestore.client.dto.request.UserDTO;
import com.shoestore.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private static final String SERVER_BASE_URL = "http://localhost:8080/";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDTO> authenticateUser(UserDTO userDTO) {
        // Gửi yêu cầu POST đến server để xác thực người dùng
        String apiUrl = SERVER_BASE_URL + "auth/login?email=" + userDTO.getEmail() + "&password=" + userDTO.getPassword();
        return restTemplate.postForEntity(apiUrl, null, UserDTO.class);
    }

    public void save(UserDTO userDTO) {
        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
        String emailCheckUrl = SERVER_BASE_URL + "auth/findByEmail?email=" + userDTO.getEmail();
        ResponseEntity<UserDTO> response = restTemplate.exchange(emailCheckUrl, HttpMethod.GET, null, UserDTO.class);
        if (response.getBody() == null) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(encodedPassword);
            String apiUrl = SERVER_BASE_URL + "auth/register";
            restTemplate.postForEntity(apiUrl, userDTO, Void.class);
        } else {
            // Nếu email đã tồn tại, trả về thông báo lỗi
            throw new IllegalArgumentException("Email already exists");
        }
    }

    @Override
    public UserDTO findByEmail(String email) {
        String url = SERVER_BASE_URL + "auth/findByEmail?email=" + email;
        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
        return response.getBody();
    }

    @Override
    public List<UserDTO> findAll() {
        String url = SERVER_BASE_URL + "findAll";
        UserDTO[] userArray = restTemplate.getForObject(url, UserDTO[].class);
        return List.of(userArray);
    }

    @Override
    public List<UserDTO> getUserByName(String username) {
        String url = SERVER_BASE_URL + "getUserByName?username=" + username;
        UserDTO[] userArray = restTemplate.getForObject(url, UserDTO[].class);
        return List.of(userArray);
    }
}

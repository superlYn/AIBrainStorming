package com.example.brainstromai.controller;

import com.example.brainstromai.model.db.User;
import com.example.brainstromai.model.request.UserLoginSignUpRequest;
import com.example.brainstromai.model.response.ApiResponse;
import com.example.brainstromai.model.response.ApiResponseErrorCode;
import com.example.brainstromai.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserLoginSignUpRequest userLoginSignUpRequest) {
        Optional<User> userOptional = userRepository.findById(userLoginSignUpRequest.getUsername());
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }
        return ResponseEntity.ok(ApiResponse.fail("", ApiResponseErrorCode.LoginFail));
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody UserLoginSignUpRequest userLoginSignUpRequest) {
        Optional<User> userOptional = userRepository.findById(userLoginSignUpRequest.getUsername());
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(ApiResponse.fail(null, ApiResponseErrorCode.SignUpFail));
        }
        User user = new User(userLoginSignUpRequest.getUsername(), userLoginSignUpRequest.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success(null));

    }
}

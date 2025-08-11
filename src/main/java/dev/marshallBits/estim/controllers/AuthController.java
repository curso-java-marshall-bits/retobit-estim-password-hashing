package dev.marshallBits.estim.controllers;

import dev.marshallBits.estim.dto.CreateUserDTO;
import dev.marshallBits.estim.dto.SignupResponseDTO;
import dev.marshallBits.estim.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponseDTO signup(@Valid @RequestBody CreateUserDTO createUserDTO) {
        return userService.registerUser(createUserDTO);
    }
}

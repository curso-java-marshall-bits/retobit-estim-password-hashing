package dev.marshallBits.estim.services;

import dev.marshallBits.estim.dto.CreateUserDTO;
import dev.marshallBits.estim.models.User;

public interface UserService {

    User registerUser(CreateUserDTO createUserDTO);

}

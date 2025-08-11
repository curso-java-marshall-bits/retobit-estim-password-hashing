package dev.marshallBits.estim.services;

import dev.marshallBits.estim.dto.CreateUserDTO;
import dev.marshallBits.estim.models.User;
import dev.marshallBits.estim.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(CreateUserDTO createUserDTO) {
        // TODO: Agregar encriptación de contraseña antes de guardar
        // TODO: Verificar si el nombre de usuario o correo electrónico ya existen

        User user = User.builder()
                .username(createUserDTO.getUsername())
                .email(createUserDTO.getEmail())
                .password(createUserDTO.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        // TODO: Mandar la info del nuevo user pero sin la contraseña
        return savedUser;
    }
}

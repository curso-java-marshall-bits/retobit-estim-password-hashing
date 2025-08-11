package dev.marshallBits.estim;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.marshallBits.estim.dto.CreateUserDTO;
import dev.marshallBits.estim.models.User;
import dev.marshallBits.estim.repositories.UserRepository;
import dev.marshallBits.estim.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignupTasksTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    private CreateUserDTO validUserDTO;

    /**
     * Configuración inicial antes de cada prueba.
     * Limpia la base de datos y crea un DTO válido para el usuario.
     */
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        validUserDTO = new CreateUserDTO();
        validUserDTO.setUsername("testuser");
        validUserDTO.setEmail("test@example.com");
        validUserDTO.setPassword("Password123");
    }

    @Test
    @Order(1)
    @DisplayName("TAREA 2: El password debe ser encriptado usando PasswordEncoder")
    void testPasswordIsEncryptedWithPasswordEncoder() throws Exception {
        // Verificar que existe un PasswordEncoder bean
        assertNotNull(passwordEncoder,
                "❌ TAREA 2: Debes configurar un PasswordEncoder bean en tu configuración de seguridad");

        // Realizar el signup
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isCreated());

        // Verificar que el password fue encriptado en la base de datos
        User savedUser = userRepository.findByUsername("testuser").orElse(null);
        assertNotNull(savedUser,
                "❌ TAREA 2: El usuario no fue guardado correctamente");

        assertNotEquals("Password123", savedUser.getPassword(),
                "❌ TAREA 2: El password no está siendo encriptado. Debes usar PasswordEncoder.encode() antes de guardar el usuario");

        assertTrue(passwordEncoder.matches("Password123", savedUser.getPassword()),
                "❌ TAREA 2: El password encriptado no coincide con el password original. Verifica que estés usando correctamente PasswordEncoder.encode()");
    }

    @Test
    @Order(2)
    @DisplayName("TAREA 3: Debe existir el DTO SignupResponse")
    void testSignupResponseDTOExists() {
        try {
            Class.forName("dev.marshallBits.estim.dto.SignupResponseDTO");
        } catch (ClassNotFoundException e) {
            fail("❌ TAREA 3: Debes crear el DTO 'SignupResponseDTO' en el paquete dev.marshallBits.estim.dto");
        }
    }

    @Test
    @Order(3)
    @DisplayName("TAREA 3: El signup no debe devolver el password en la respuesta")
    void testSignupDoesNotReturnPassword() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        assertFalse(responseContent.contains("password"),
                "❌ TAREA 3: La respuesta del signup no debe contener el campo 'password'. Debes usar el DTO SignupResponse en lugar de devolver el User completo");

        assertFalse(responseContent.contains("Password123"),
                "❌ TAREA 3: La respuesta del signup no debe contener el password del usuario");

        // Verificar que contiene los campos esperados
        assertTrue(responseContent.contains("username"),
                "❌ TAREA 3: La respuesta debe contener el campo 'username'");

        assertTrue(responseContent.contains("email"),
                "❌ TAREA 3: La respuesta debe contener el campo 'email'");

        assertTrue(responseContent.contains("id"),
                "❌ TAREA 3: La respuesta debe contener el campo 'id'");
    }

    @Test
    @Order(4)
    @DisplayName("TAREA 3: SignupResponseDTO debe tener los campos correctos (id, username, email)")
    void testSignupResponseHasCorrectFields() throws Exception {
        try {
            Class<?> signupResponseClass = Class.forName("dev.marshallBits.estim.dto.SignupResponseDTO");

            // Verificar que tiene los campos requeridos
            try {
                signupResponseClass.getDeclaredField("id");
            } catch (NoSuchFieldException e) {
                fail("❌ TAREA 3: El SignupResponseDTO debe tener un campo 'id' de tipo Long");
            }

            try {
                signupResponseClass.getDeclaredField("username");
            } catch (NoSuchFieldException e) {
                fail("❌ TAREA 3: El SignupResponseDTO debe tener un campo 'username' de tipo String");
            }

            try {
                signupResponseClass.getDeclaredField("email");
            } catch (NoSuchFieldException e) {
                fail("❌ TAREA 3: El SignupResponseDTO debe tener un campo 'email' de tipo String");
            }

            // Verificar que NO tiene el campo password
            try {
                signupResponseClass.getDeclaredField("password");
                fail("❌ TAREA 3: El SignupResponseDTO NO debe tener un campo 'password'");
            } catch (NoSuchFieldException e) {
                // Esto es lo esperado - no debe tener el campo password
            }

        } catch (ClassNotFoundException e) {
            fail("❌ TAREA 3: Debes crear 'SignupResponseDTO' en el paquete dev.marshallBits.estim.dto");
        }
    }

    @Test
    @Order(5)
    @DisplayName("TAREA 2 y 3: El controller debe usar SignupResponse y el password debe estar encriptado")
    void testControllerUsesSignupResponseAndPasswordIsEncrypted() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        // Verificar que la respuesta no contiene password
        assertFalse(responseContent.contains("password"),
                "❌ TAREA 2 y 3: El controller debe devolver SignupResponseDTO (sin password) en lugar de User");

        // Verificar que el usuario fue guardado con password encriptado
        User savedUser = userRepository.findByUsername("testuser").orElse(null);
        assertNotNull(savedUser);

        if (passwordEncoder != null) {
            assertNotEquals("Password123", savedUser.getPassword(),
                    "❌ TAREA 2: El password debe estar encriptado en la base de datos");
        }

        // Verificar formato de respuesta JSON
        assertTrue(responseContent.contains("testuser"),
                "❌ TAREA 3: La respuesta debe contener el username");

        assertTrue(responseContent.contains("test@example.com"),
                "❌ TAREA 3: La respuesta debe contener el email");
    }
}

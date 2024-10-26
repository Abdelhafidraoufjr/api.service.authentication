package api.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import api.service.auth.entity.User;
import api.service.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceauthApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        userRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        mockMvc.perform(post("/api/auth/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        userRepository.save(user); // Save user to the database

        mockMvc.perform(get("/api/auth/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        User savedUser = userRepository.save(user);

        mockMvc.perform(get("/api/auth/users/{id}", savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        User savedUser = userRepository.save(user);

        savedUser.setName("Jane Doe");

        mockMvc.perform(put("/api/auth/users/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        User savedUser = userRepository.save(user);

        mockMvc.perform(delete("/api/auth/users/{id}", savedUser.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/auth/users/{id}", savedUser.getId()))
                .andExpect(status().isNotFound());
    }
}

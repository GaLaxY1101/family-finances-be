package com.mindspark.family_finances.controller;

import com.mindspark.family_finances.controllers.AuthenticationController;
import com.mindspark.family_finances.dto.AuthenticationRequest;
import com.mindspark.family_finances.dto.AuthenticationResponse;
import com.mindspark.family_finances.dto.RegisterRequest;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.UserRepository;
import com.mindspark.family_finances.security.JwtAuthenticationFilter;
import com.mindspark.family_finances.security.JwtService;
import com.mindspark.family_finances.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;


    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AuthenticationController authenticationController;


    @Test
    @WithMockUser
    void register_ShouldReturnJwtToken() throws Exception {
        // Arrange
        AuthenticationResponse authResponse = new AuthenticationResponse("jwt_token");

        // Stubbing the service register method
        when(authenticationService.register(any())).thenReturn(authResponse);
        Mockito.doAnswer(invocation -> {
            User argUser = invocation.getArgument(0);
            argUser.setId(1L);  // Simulate that save sets the user ID
            return null;  // Since save doesn't return anything
        }).when(userRepository).save(any(User.class));
        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "firstname": "John",
                                "lastname": "Doe",
                                "email": "john@example.com",
                                "password": "password"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt_token"));
    }

    @Test
    @WithMockUser
    void authenticate_ShouldReturnJwtToken() throws Exception {
        // Arrange
        AuthenticationResponse authResponse = new AuthenticationResponse("jwt_token");

        // Stubbing the service authenticate method
        when(authenticationService.authenticate(any())).thenReturn(authResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "john@example.com",
                                "password": "password"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt_token"));
    }
}

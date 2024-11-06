package com.intern.project1.service;

import com.intern.project1.entities.User;
import com.intern.project1.entities.dto.RegisterRequest;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.exceptions.ResourceAlreadyExistedException;
import com.intern.project1.repositories.UserRepository;
import com.intern.project1.services.authentication.AuthenticationServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthenticationServiceImp authService;
    @Mock
    private ModelMapper modelMapper;
    private RegisterRequest request;
    private User user = new User();

    @BeforeEach
    public void setup() {
        request = RegisterRequest.builder()
                .userName("test1")
                .email("test@mail.com")
                .phone("0000000001")
                .password("password")
                .role(Role.CUSTOMER)
                .build();
        lenient().when(modelMapper.map(request, User.class)).thenReturn(user);
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
    }

    @DisplayName("JUnit for register method")
    @Test
    public void givenRegisterRequestObject_whenRegister_thenGetRegister() {
        //given RegisterRequest Object

        given(userRepository.findByUserName(request.getUserName()))
                .willReturn(Optional.empty());
        lenient().when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        lenient().when(userRepository.findByPhone(request.getPhone()))
                .thenReturn(Optional.empty());
        given(userRepository.save(user)).willReturn(user);
        //when Register
//        User savedUser = authService.register(request);
//        System.out.println(savedUser);
        //then getRegister
//        Assertions.assertThat(savedUser).isNotNull();
//        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit for register method exist Email thows exception")
    @Test
    public void givenExistEmail_whenRegister_thenThrowsException() {
        //given RegisterRequest Object
        given(userRepository.findByEmail(request.getEmail()))
                .willReturn(Optional.of(user));

        //when Register
        assertThrows(ResourceAlreadyExistedException.class, () -> {
            authService.register(request);
        });
        //then throws Exception
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit for register method exist userId thows exception")
    @Test
    public void givenExistUserName_whenRegister_thenThrowsException() {
        //given RegisterRequest Object

        given(userRepository.findByUserName(request.getUserName()))
                .willReturn(Optional.of(user));
        //when Register
        assertThrows(ResourceAlreadyExistedException.class, () -> {
            authService.register(request);
        });
        //then throws Exception
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit for register method exist phone number thows exception")
    @Test
    public void givenExistPhone_whenRegister_thenThrowsException() {
        //given RegisterRequest Object
        given(userRepository.findByPhone(request.getPhone()))
                .willReturn(Optional.of(user));
        //when Register
        assertThrows(ResourceAlreadyExistedException.class, () -> {
            authService.register(request);
        });
        //then throws Exception
        verify(userRepository, never()).save(any(User.class));
    }
}

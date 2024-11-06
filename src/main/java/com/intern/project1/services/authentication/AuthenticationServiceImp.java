package com.intern.project1.services.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern.project1.configurations.securityConfiguration.JwtService;
import com.intern.project1.entities.User;
import com.intern.project1.entities.dto.AuthenticationRequest;
import com.intern.project1.entities.dto.AuthenticationResponse;
import com.intern.project1.entities.dto.RegisterRequest;
import com.intern.project1.constant.enums.AccountStatus;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.entities.token.Token;
import com.intern.project1.entities.token.TokenType;
import com.intern.project1.exceptions.ResourceAlreadyExistedException;
import com.intern.project1.exceptions.WrongInformationException;
import com.intern.project1.repositories.CartRepository;
import com.intern.project1.repositories.TokenRepository;
import com.intern.project1.repositories.UserRepository;
import com.intern.project1.services.CartServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartServices cartServices;
    private final CartRepository cartRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (request.getUserName().isBlank() || request.getUserName().isEmpty()) {
            throw new NullPointerException("UserName can not be null or blank");
        } else if (repository.findByUserName(request.getUserName()).isPresent()) {
            throw new ResourceAlreadyExistedException("This UserName has already been used, please use another");
        }
        if (request.getEmail().isEmpty()
                || request.getEmail().isBlank()
                || !request.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {

            throw new IllegalArgumentException("Email can not be null or blank and format must be followed by 'test@mail.com' ");
        } else if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistedException("This email has already been used, please use another");
        }
        if (request.getPhone().isEmpty()
                || request.getPhone().isBlank()
                || !request.getPhone().matches("0+[0-9]{9}")) {
            throw new IllegalArgumentException("Phone number can not be null or blank and format must be followed by '0xxxxxxxxx' ");
        } else if (repository.findByPhone(request.getPhone()).isPresent()) {
            throw new ResourceAlreadyExistedException("This phone number has already been used, please use another");
        }
        if (request.getPassword().isEmpty() || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password can not be null or blank, please re-enter your password");
        } else if (request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 character, please re-enter your password");
        }
        var user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .createdTime(Date.from(Instant.now()))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        cartServices.initCart(savedUser.getId());
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse register(@Nullable String userName, RegisterRequest request) {
        if (request.getUserName().isBlank() || request.getUserName().isEmpty()) {
            throw new NullPointerException("UserName can not be null or blank");
        } else if (repository.findByUserName(request.getUserName()).isPresent()) {
            throw new ResourceAlreadyExistedException("This UserName has already been used, please use another");
        }
        if (request.getEmail().isEmpty()
                || request.getEmail().isBlank()
                || !request.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {

            throw new IllegalArgumentException("Email can not be null or blank and format must be followed by 'test@mail.com' ");
        } else if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistedException("This email has already been used, please use another");
        }
        if (request.getPhone().isEmpty()
                || request.getPhone().isBlank()
                || !request.getPhone().matches("0+[0-9]{9}")) {
            throw new IllegalArgumentException("Phone number can not be null or blank and format must be followed by '0xxxxxxxxx' ");
        } else if (repository.findByPhone(request.getPhone()).isPresent()) {
            throw new ResourceAlreadyExistedException("This phone number has already been used, please use another");
        }
        if (request.getPassword().isEmpty() || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password can not be null or blank, please re-enter your password");
        } else if (request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 character, please re-enter your password");
        }
        var user = new User();
        if (userName == null || userName.isEmpty()) {
            user = User.builder()
                    .userName(request.getUserName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .accountStatus(AccountStatus.ACTIVATED)
                    .role(request.getRole())
                    .createdTime(Date.from(Instant.now()))
                    .build();

        } else if (repository.findByUserName(userName).get().getRole() == Role.GUEST) {
            user = repository.findByUserName(userName).get();
            user.setUserName(request.getUserName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setRole(request.getRole());
        }

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public boolean checkEmailExisted(RegisterRequest request) {
        return repository.findByEmail(request.getEmail()).isPresent();
    }

    @Override
    public boolean checkMobileExisted(RegisterRequest request) {
        return repository.findByPhone(request.getPhone()).isPresent();
    }

    @Override
    public boolean checkUsernameExisted(RegisterRequest request) {
        return repository.findByUserName(request.getUserName()).isPresent();
    }

    @Override
    public boolean isLoginInformationMathched(AuthenticationRequest authenticationRequest) {
        return repository.findByUserName(authenticationRequest.getUserName()).isPresent()
                && passwordEncoder.matches(
                authenticationRequest.getPassword(),
                repository.findByUserName(authenticationRequest.getUserName()).get().getPassword()
        );
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!isLoginInformationMathched(request)) {
            throw new WrongInformationException("Username or password wrong, please try again");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(),
                        request.getPassword()
                )
        );
        var user = repository.findByUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private UserRepository userRepository;
//    //    @Autowired
//    //    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private CartRepository cartRepository;
//    //    @Autowired
//    //    private AuthenticationManager authenticationManager;
//
//    public AuthenticationServiceImp(UserRepository userRepository) {
//        this.userRepository = userRepository;
//
//    }
//
//    @Override
//    public AuthenticationResponse register(RegisterRequest registerUser) throws RuntimeException {
//        User createUser = new User();
//        //Neu user role khong phai la guest thi kiem tra trung lap du lieu sau do cho tao user moi
//        if (!registerUser.getRole().equals(Role.GUEST)) {
//            if (registerUser.getUserId().isBlank() || registerUser.getUserId().isEmpty()) {
//                throw new NullPointerException("UserName can not be null or blank");
//            } else if (userRepository.findByUserName(registerUser.getUserId()).isPresent()) {
//                throw new ResourceAlreadyExistedException("This UserName has already been used, please use another");
//            }
//            if (registerUser.getEmail().isEmpty()
//                    || registerUser.getEmail().isBlank()
//                    || !registerUser.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//
//                throw new IllegalArgumentException("Email can not be null or blank and format must be followed by 'test@mail.com' ");
//            } else if (userRepository.findByEmail(registerUser.getEmail()).isPresent()) {
//                throw new ResourceAlreadyExistedException("This email has already been used, please use another");
//            }
//            if (registerUser.getPhone().isEmpty()
//                    || registerUser.getPhone().isBlank()
//                    || !registerUser.getPhone().matches("0+[0-9]{9}")) {
//                throw new IllegalArgumentException("Phone number can not be null or blank and format must be followed by '0xxxxxxxxx' ");
//            } else if (userRepository.findByPhone(registerUser.getPhone()).isPresent()) {
//                throw new ResourceAlreadyExistedException("This phone number has already been used, please use another");
//            }
//            if (registerUser.getPassword().isEmpty() || registerUser.getPassword().isBlank()) {
//                throw new IllegalArgumentException("Password can not be null or blank, please re-enter your password");
//            } else if (registerUser.getPassword().length() < 6) {
//                throw new IllegalArgumentException("Password must be at least 6 character, please re-enter your password");
//            }
//            createUser = modelMapper.map(registerUser, User.class);
//            createUser.setCreatedTime(Date.from(Instant.now()));
////            createUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
//            userRepository.save(createUser);
//            Cart initCart = Cart.builder()
//                    .user(createUser)
//                    .price(0.0)
//                    .createdTime(Date.from(Instant.now()))
//                    .build();
//            cartRepository.save(initCart);
//
//
//            //Tao Jwt cho nguoi dung
//
//            //Luu User vua tao vao database
//
//            //Tao Cart tuong ung cho User vua tao
////            cartServices.initCart(createUser.getId());
//
//        }
//        //Neu user Role la GUEST thi tao mot doi tuong user voi random userId va password null
//        return new AuthenticationResponse(jwtService.generateToken(createUser));
//    }
//
//    @Override
//    public AuthenticationResponse login(LoginRequest loginRequest) throws RuntimeException {
//
//        if (isLoginInformationMatched(loginRequest)) {
//            if (loginRequest.getPassword().matches(userRepository.findByUserName(loginRequest.getUserId()).get().getPassword())
//                    || loginRequest.getPassword().matches(userRepository.findByPhone(loginRequest.getPhone()).get().getPassword())
//                    || loginRequest.getPassword().matches(userRepository.findByEmail(loginRequest.getEmail()).get().getPassword())){
//                return AuthenticationResponse.builder().token(jwtService.generateToken(loggingIn)).build();
//
//            }
//
//        }
//        User loggingIn = new User();
//        if (userRepository.findByUserName(loginRequest.getUserId()).isEmpty() ||
//                loginRequest.getPassword()
//                        .matches(userRepository.findByUserName(loginRequest.getUserId()).get().getPassword())) {
//            throw new ResourceNotFoundException("UserName or password incorrect, please try again");
//        } else {
//
//            loggingIn = userRepository.findByUserName(loginRequest.getUserId()).get();
//        }
//        System.out.println(jwtService.generateToken(loggingIn));
//        return AuthenticationResponse.builder().token(jwtService.generateToken(loggingIn)).build();
//    }
//
//    private boolean isLoginInformationMatched(LoginRequest loginRequest) throws RuntimeException {
//        return userRepository.findByEmail(loginRequest.getEmail()).isPresent()
//                && userRepository.findByUserName(loginRequest.getUserId()).isPresent()
//                && userRepository.findByPhone(loginRequest.getPhone()).isPresent();
//
//    }
}

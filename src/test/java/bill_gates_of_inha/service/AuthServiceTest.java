package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입로직성공테스트() {
        final UserDto.Creation req = UserDto.Creation.builder()
                .name("1234")
                .password("1234")
                .userId("1234")
                .build();

        User user = User.builder()
                .password("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));
        given(passwordEncoder.encode(any())).willReturn("1234");
        given(userRepository.save(any())).willReturn(Optional.ofNullable(user));

        final UserDto.SignUpResult res = authService.signUp(req);

        Assertions.assertEquals(res.getResult(), true);
    }

    @Test
    void 회원가입중중복회원() {
        final UserDto.Creation req = UserDto.Creation.builder()
                .name("1234")
                .password("1234")
                .userId("1234")
                .build();

        User user = User.builder()
                .password("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));

        final UserException.Reduplication e = assertThrows(UserException.Reduplication.class, () -> authService.signUp(req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.CONFLICT);
    }

    @Test
    void 회원가입중에중복회원() {
        final UserDto.Creation req = UserDto.Creation.builder()
                .name("1234")
                .password("1234")
                .userId("1234")
                .build();

        User user = User.builder()
                .password("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));

        final UserException.Reduplication e = assertThrows(UserException.Reduplication.class, () -> authService.signUp(req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.CONFLICT);
    }

    @Test
    void 로그인성공테스트() {
        final String userId = "test";

        User user = User.builder()
                .userId("1234")
                .password("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));

        UserDetails res = authService.loadUserByUsername(userId);
        Assertions.assertEquals(user.getUserId(), res.getUsername());
    }

    @Test
    void 로그인중없는아이디() {
        final String userId = "test";

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));

        final UserException.NotFoundUser e = assertThrows(UserException.NotFoundUser.class, () -> authService.loadUserByUsername(userId));
        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void 로그인중비번틀림() {
        given(passwordEncoder.matches(any(),any())).willReturn(false);

        final UserException.WrongPassword e = assertThrows(UserException.WrongPassword.class, () -> authService.comparePassword("1234","123"));
        Assertions.assertEquals(e.getStatus(), HttpStatus.UNAUTHORIZED);
    }
}
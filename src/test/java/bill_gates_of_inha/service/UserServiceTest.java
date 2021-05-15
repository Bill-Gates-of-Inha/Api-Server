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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 유저프로필가져오깅() {
        User user = User.builder()
                .userId("hello")
                .password("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .name("hello")
                .build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));

        UserDto.User u = userService.getProfileByUserId("hello");

        Assertions.assertEquals(u.getName(), user.getName());
        Assertions.assertEquals(u.getUserId(), user.getUserId());
        Assertions.assertEquals(u.getAddress(), user.getAddress());
    }

    @Test
    void 유저프로필가져올떄없을때() {
        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));

        UserException.NotFoundUser e = assertThrows(UserException.NotFoundUser.class, ()-> userService.getProfileByUserId("test"));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void 업데이트로직() {
        User user = User.builder()
                .userId("hello")
                .password("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .name("hello")
                .build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(user));
    }

    @Test
    void 업데이트할때유저없을때() {
        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));

        UserDto.Update req = UserDto.Update.builder().address("1234").name("1234").build();
        UserException.NotFoundUser e = assertThrows(UserException.NotFoundUser.class, ()-> userService.updateProfile("test", req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }
}
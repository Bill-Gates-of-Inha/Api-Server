package bill_gates_of_inha.controller;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.TokenDto;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.repository.TokenRepository;
import bill_gates_of_inha.repository.UserRepository;
import bill_gates_of_inha.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext ctx;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    TokenRepository tokenRepository;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }
    @Test
    void 회원가입성공테스() throws Exception {
        final UserDto.Creation req = UserDto.Creation.builder()
                .userId("test")
                .password("test")
                .name("test")
                .build();

        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value("true"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("회원가입"));
    }

    @Test
    void 중복회원가입테스트() throws Exception {
        final String password = passwordEncoder.encode("test");
        final UserDto.Creation req = UserDto.Creation.builder()
                .userId("test")
                .password("test")
                .name("test")
                .build();
        final User user = User.builder()
                .userId("test")
                .password(password)
                .name("test")
                .build();
        userRepository.save(user);


        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect((res)-> assertTrue(res.getResolvedException().getClass().isAssignableFrom(UserException.Reduplication.class)))
                .andExpect(jsonPath("$.status").value("CONFLICT"))
                .andExpect(jsonPath("$.code").value("409"))
                .andExpect(jsonPath("$.message").value("존재하는 회원입니다."));
    }

    @Test
    void 로그인성공테스트() throws Exception {
        final String password = passwordEncoder.encode("test");

        final User user = User.builder()
                .userId("test")
                .password(password)
                .name("test")
                .build();

        final UserDto.SignIn req = UserDto.SignIn.builder()
                .userId("test")
                .password("test")
                .build();

        userRepository.save(user);

        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("로그인"));
    }

    @Test
    void 로그인중_아이디_못찾을때() throws Exception {
        final UserDto.SignIn req = UserDto.SignIn.builder()
                .userId("test")
                .password("test")
                .build();

        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((res)-> assertTrue(res.getResolvedException().getClass().isAssignableFrom(UserException.NotFoundUser.class)))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.code").value("404"));
    }

    @Test
    void 로그인_패스워드_틀() throws Exception {
        final String password = passwordEncoder.encode("test2");

        final User user = User.builder()
                .userId("test")
                .password(password)
                .name("test")
                .build();

        final UserDto.SignIn req = UserDto.SignIn.builder()
                .userId("test")
                .password("test")
                .build();

        userRepository.save(user);

        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect((res)-> assertTrue(res.getResolvedException().getClass().isAssignableFrom(UserException.WrongPassword.class)))
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value("401"));
    }

    @Test
    void 토큰재발급() throws Exception {
        final String password = passwordEncoder.encode("test");
        final User user = User.builder()
                .userId("test")
                .password(password)
                .name("test")
                .build();
        User u = userRepository.save(user).get();

        String act = jwtProvider.createToken(u.getUserId(), u.getRoles());
        String rft = jwtProvider.createRefreshToken();

        tokenRepository.set(act,rft);

        TokenDto.Token to = TokenDto.Token.builder().accessToken(act).refreshToken(rft).build();

        mvc.perform(post("/api/auth/re")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(to)))
                .andDo(print())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value("201"));

    }

    @Test
    void 토큰이_만료되었을() throws Exception {
        final String password = passwordEncoder.encode("test");
        final User user = User.builder()
                .userId("test")
                .password(password)
                .name("test")
                .build();
        User u = userRepository.save(user).get();

        Claims claims = Jwts.claims().setSubject(u.getUserId());
        claims.put("roles", u.getRoles());
        Date now = new Date();

        String act = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(0))
                .signWith(SignatureAlgorithm.HS256, "1234")
                .compact();

        mvc.perform(get("/api/foods/test").header("AUTHORIZATION_HEADER", "Bearer "+act))
                .andDo(print());
    }
}
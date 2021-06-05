package bill_gates_of_inha.controller;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext ctx;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }

    @Test
    void 유저프로필리스폰스() throws Exception{
        User u = User.builder().name("test").address("test").password("test").userId("test").build();
        u = userRepository.save(u).get();

        mvc.perform(get("/api/users/" + u.getUserId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(u.getUserId()))
                .andExpect(jsonPath("$.data.name").value(u.getName()))
                .andExpect(jsonPath("$.data.address").value(u.getAddress()))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    void 프로필업데이트() throws Exception {
        User u = User.builder().name("test").address("test").password("test").userId("test").build();
        u = userRepository.save(u).get();

        UserDto.Update req = UserDto.Update.builder().address("test2").build();

        mvc.perform(patch("/api/users/" + u.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(u.getUserId()))
                .andExpect(jsonPath("$.data.name").value(u.getName()))
                .andExpect(jsonPath("$.data.address").value("test2"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    void 스코어업데이트() throws Exception {
        User u = User.builder().name("test").address("test").password("test").userId("test").build();
        u = userRepository.save(u).get();

        mvc.perform(patch("/api/users/" + u.getUserId() +"/"+10d)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(u.getUserId()))
                .andExpect(jsonPath("$.data.name").value(u.getName()))
                .andExpect(jsonPath("$.data.address").value("test"))
                .andExpect(jsonPath("$.data.score").value(10d))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"));
    }
}
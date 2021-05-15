package bill_gates_of_inha.controller;

import bill_gates_of_inha.domain.Record;
import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.domain.Workout;
import bill_gates_of_inha.dto.RecordDto;
import bill_gates_of_inha.repository.RecordRepository;
import bill_gates_of_inha.repository.UserRepository;
import bill_gates_of_inha.repository.WorkoutRepository;
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

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
@AutoConfigureMockMvc
class RecordControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext ctx;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkoutRepository workoutRepository;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }

    @Test
    void 기록생성() throws Exception {
        User u = User.builder().name("test").userId("test").password("test").address("test").build();
        Workout w = Workout.builder().name("test").build();

        u = userRepository.save(u).get();
        w = workoutRepository.save(w).get();

        RecordDto.Creation req = RecordDto.Creation.builder().userId(u.getUserId()).workoutId(w.getId()).weight(10d).setNum(10L).count(10L).build();
        mvc.perform(post("/api/records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.weight").value(10d))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value("201"));
    }

    @Test
    void 기록리스트() throws Exception {
        User u = User.builder().name("test").userId("test").password("test").address("test").build();
        Workout w = Workout.builder().name("test").build();
        Record r1 = Record.builder().weight(10d).setNum(10L).count(10L).build();
        Record r2 = Record.builder().weight(10d).setNum(10L).count(10L).build();

        //recordRepository.save(r1,)
        //u = userRepository.save(u).get();
        //w = workoutRepository.save(w).get();
    }

    @Test
    void updateRecord() {
    }
}
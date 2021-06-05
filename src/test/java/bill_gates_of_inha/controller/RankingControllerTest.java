package bill_gates_of_inha.controller;

import bill_gates_of_inha.repository.RankingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@Transactional
@AutoConfigureMockMvc
class RankingControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext ctx;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    RankingRepository rankingRepository;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }

    @Test
    void getRankingListByAddress() throws Exception {
        rankingRepository.addUserNameAndScoreByAddress("asdf","1234",100d);
        rankingRepository.addUserNameAndScoreByAddress("asdf","1235",250d);
        rankingRepository.addUserNameAndScoreByAddress("asdf","1236",50d);

        mvc.perform(get("/api/rankings/asdf"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"));
    }
}
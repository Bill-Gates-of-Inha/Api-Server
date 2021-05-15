package bill_gates_of_inha.controller;

import bill_gates_of_inha.domain.Food;
import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.repository.FoodRepository;
import bill_gates_of_inha.repository.UserRepository;
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

import javax.persistence.EntityManager;
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
class FoodControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext ctx;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }

    @Test
    void 영양정보얻기기기기기기리리릭() throws Exception{
        Food f = Food.builder().sugars(10d).serving(10d).salts(10d).protein(10d).fat(10d).carbohydrate(10d).calorie(10d).name("test").build();
        f = foodRepository.save(f).get();

        mvc.perform(get("/api/foods/" + f.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(f.getName()))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.code").value("200"));
    }
}
package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class FoodRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    FoodRepository foodRepository;


    @Test
    void 이름제대로찾는지() {
        Food f = Food.builder().name("함지디").calorie(100.5).carbohydrate(10.5).fat(10.1).protein(10.1).salts(10.1).serving(10.1).sugars(10.1).build();
        foodRepository.save(f);

        Food ff = foodRepository.findByName("함지디").get();

        Assertions.assertEquals(ff.getName(), "함지디");
        Assertions.assertEquals(ff.getCarbohydrate(), 10.5);
        Assertions.assertEquals(ff.getCalorie(), 100.5);
        Assertions.assertEquals(ff.getFat(), 10.1);
        Assertions.assertEquals(ff.getSalts(), 10.1);
        Assertions.assertEquals(ff.getProtein(), 10.1);
        Assertions.assertEquals(ff.getSugars(), 10.1);
        Assertions.assertEquals(ff.getServing(), 10.1);

    }
}
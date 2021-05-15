package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.Food;
import bill_gates_of_inha.dto.FoodDto;
import bill_gates_of_inha.exception.FoodException;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.repository.FoodRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class FoodServiceTest {
    @Mock
    private FoodRepository foodRepository;
    @InjectMocks
    private FoodService foodService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 영양정보잘가져와랑() {
        Food f = Food.builder().sugars(10d).serving(10d).salts(10d).protein(10d).fat(10d).carbohydrate(10d).calorie(10d).name("test").build();

        given(foodRepository.findByName(any())).willReturn(Optional.ofNullable(f));

        FoodDto.Food dto = foodService.getFoodByName("test");

        Assertions.assertEquals(dto.getName(), f.getName());
        Assertions.assertEquals(dto.getCalorie(), f.getCalorie());
        Assertions.assertEquals(dto.getFat(), f.getFat());
    }

    @Test
    void 찾는음식이없엉() {
        given(foodRepository.findByName(any())).willReturn(Optional.ofNullable(null));

        FoodException.NotFoundFood e= assertThrows(FoodException.NotFoundFood.class, () -> foodService.getFoodByName("test"));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }
}
package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.Food;
import bill_gates_of_inha.dto.FoodDto;
import bill_gates_of_inha.exception.FoodException;
import bill_gates_of_inha.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Transactional
@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public FoodDto.Food getFoodByName(String name) {
        Food food = foodRepository.findByName(name).orElseThrow(FoodException.NotFoundFood::new);

        return FoodDto.Food.toDto(food);
    }

}

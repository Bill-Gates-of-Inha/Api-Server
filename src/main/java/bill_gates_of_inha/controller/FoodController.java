package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.FoodDto;
import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/api/foods/{name}")
    public ResponseDto.Ok getFoodByName(@PathVariable("name") String name) {
        FoodDto.Food foodDto = foodService.getFoodByName(name);

        return new ResponseDto.Ok(foodDto, "영양정보");
    }
}

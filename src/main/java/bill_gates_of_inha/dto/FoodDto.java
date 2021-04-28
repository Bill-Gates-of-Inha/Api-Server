package bill_gates_of_inha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class FoodDto {
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Food {
        private Long id;
        private String name;
        private Double serving;
        private Double calorie;
        private Double carbohydrate;
        private Double protein;
        private Double fat;
        private Double sugars;
        private Double salts;

        public static FoodDto.Food toDto(bill_gates_of_inha.domain.Food food) {

            return Food.builder()
                    .id(food.getId())
                    .name(food.getName())
                    .serving(food.getServing())
                    .calorie(food.getCalorie())
                    .carbohydrate(food.getCarbohydrate())
                    .protein(food.getProtein())
                    .fat(food.getFat())
                    .sugars(food.getSugars())
                    .salts(food.getSalts())
                    .build();
        }
    }
}

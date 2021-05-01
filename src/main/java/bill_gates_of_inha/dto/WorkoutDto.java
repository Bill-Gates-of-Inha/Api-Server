package bill_gates_of_inha.dto;

import bill_gates_of_inha.domain.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WorkoutDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Workout {
        private Long id;
        private String name;

        public static Workout toDto(bill_gates_of_inha.domain.Workout workout) {

            return Workout.builder()
                    .id(workout.getId())
                    .name(workout.getName())
                    .build();
        }
    }
}

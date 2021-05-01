package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.dto.WorkoutDto;
import bill_gates_of_inha.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutController {
    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/api/workouts")
    public ResponseDto.Ok getWorkoutAll() {
        List<WorkoutDto.Workout> workoutDtoList =  workoutService.getWorkoutAll();

        return new ResponseDto.Ok(workoutDtoList, "운동 종류 리스트");
    }
}

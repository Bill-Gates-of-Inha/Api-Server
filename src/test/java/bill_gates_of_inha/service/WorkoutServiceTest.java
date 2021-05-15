package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.Workout;
import bill_gates_of_inha.dto.WorkoutDto;
import bill_gates_of_inha.repository.WorkoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class WorkoutServiceTest {
    @Mock
    WorkoutRepository workoutRepository;

    @InjectMocks
    WorkoutService workoutService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getWorkoutAll() {
        List<Workout> li = new ArrayList<>();
        li.add(Workout.builder().build());
        li.add(Workout.builder().build());

        given(workoutRepository.findAll()).willReturn(li);

        List<WorkoutDto.Workout> list = workoutService.getWorkoutAll();

        Assertions.assertEquals(list.size(), 2);
    }
}
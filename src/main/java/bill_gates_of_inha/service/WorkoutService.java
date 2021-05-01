package bill_gates_of_inha.service;

import bill_gates_of_inha.dto.WorkoutDto;
import bill_gates_of_inha.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkoutService {
    private WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<WorkoutDto.Workout> getWorkoutAll() {

        return workoutRepository.findAll().stream().map(w -> WorkoutDto.Workout.toDto(w)).collect(Collectors.toList());
    }
}

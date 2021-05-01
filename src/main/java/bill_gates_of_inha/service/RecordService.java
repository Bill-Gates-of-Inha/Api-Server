package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.Record;
import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.domain.Workout;
import bill_gates_of_inha.dto.RecordDto;
import bill_gates_of_inha.exception.RecordException;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.exception.WorkoutException;
import bill_gates_of_inha.repository.RecordRepository;
import bill_gates_of_inha.repository.UserRepository;
import bill_gates_of_inha.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository, UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    public RecordDto.Record createRecord(RecordDto.Creation req) {
        Record record = RecordDto.Creation.toEntity(req);
        User user = userRepository.findByUserId(req.getUserId()).orElseThrow(UserException.NotFoundUser::new);
        Workout workout = workoutRepository.findById(req.getWorkoutId()).orElseThrow(WorkoutException.NotFound::new);

        Record savedRecord = recordRepository.save(record, user, workout).orElseThrow(RecordException.NotFound::new);

        return RecordDto.Record.toDto(savedRecord);
    }

    public RecordDto.Record getRecordById(String id) {
        Record record = recordRepository.findById(id).orElseThrow(RecordException.NotFound::new);

        return RecordDto.Record.toDto(record);
    }

    public void updateRecord(String id, RecordDto.Update req) {
        Record record = recordRepository.findById(id).orElseThrow(RecordException.NotFound::new);

        HashMap<String, Object> updateMap = new HashMap<>();
        if(req.getWeight() != null) {
            updateMap.put("weight", req.getWeight());
        }
        if(req.getCount() != null) {
            updateMap.put("count", req.getCount());
        }
        if(req.getSetNum() != null) {
            updateMap.put("setNum", req.getSetNum());

        }
        if(req.getWorkoutId() != null) {
            Workout workout = workoutRepository.findById(req.getWorkoutId()).orElseThrow(WorkoutException.NotFound::new);
            updateMap.put("workout", workout);
        }

        recordRepository.update(record, updateMap);
    }

    public List<RecordDto.Record> getRecordListByUserIdByDate(String userId, RecordDto.FilterForDate req) {
        LocalDateTime startDate = req.getStartDate() == null ? null :LocalDateTime.parse(req.getStartDate() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = req.getEndDate() == null ? null : LocalDateTime.parse(req.getEndDate() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Record> records = recordRepository.findByUserIdByDate(userId, startDate, endDate);

        return records.stream().map(r-> RecordDto.Record.toDto(r)).collect(Collectors.toList());
    }
}

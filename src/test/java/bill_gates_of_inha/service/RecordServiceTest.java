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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RecordServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RecordRepository recordRepository;
    @Mock
    WorkoutRepository workoutRepository;

    @InjectMocks
    RecordService recordService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 레코드생성() {
        User u = User.builder().build();
        Workout w = Workout.builder().build();
        Record r = Record.builder().workout(w).build();

        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(u));
        given(workoutRepository.findById(any())).willReturn(Optional.ofNullable(w));
        given(recordRepository.save(any(),any(),any())).willReturn(Optional.ofNullable(r));

        RecordDto.Creation req = RecordDto.Creation.builder().build();
        RecordDto.Record dto = recordService.createRecord(req);

        Assertions.assertEquals(r.getWeight(), dto.getWeight());
    }

    @Test
    void 레코드생성_유저없을때() {
        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(null));

        RecordDto.Creation req = RecordDto.Creation.builder().build();
        UserException.NotFoundUser e = assertThrows(UserException.NotFoundUser.class, () -> recordService.createRecord(req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void 레코드생성_운동없을때() {
        User u = User.builder().build();
        given(userRepository.findByUserId(any())).willReturn(Optional.ofNullable(u));
        given(recordRepository.save(any(),any(),any())).willReturn(Optional.ofNullable(null));

        RecordDto.Creation req = RecordDto.Creation.builder().build();
        WorkoutException.NotFound e = assertThrows(WorkoutException.NotFound.class, () -> recordService.createRecord(req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }


    @Test
    void 레코드찾깅() {
        Record r = Record.builder().id(1L).workout(Workout.builder().build()).build();

        given(recordRepository.findById(any())).willReturn(Optional.ofNullable(r));

        RecordDto.Record dto = recordService.getRecordById(1L);

        Assertions.assertEquals(r.getId(), dto.getId());

    }

    @Test
    void 레코드찾기_레코드없을때() {
        given(recordRepository.findById(any())).willReturn(Optional.ofNullable(null));

        RecordException.NotFound e = assertThrows(RecordException.NotFound.class, () -> recordService.getRecordById(1L));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void 기록수정() {
        Workout w = Workout.builder().id(1L).build();
        Record r = Record.builder().id(1L).workout(w).build();

        given(recordRepository.findById(any())).willReturn(Optional.ofNullable(r));
        given(workoutRepository.findById(any())).willReturn(Optional.ofNullable(w));

        RecordDto.Update req = RecordDto.Update.builder().count(10L).setNum(10L).weight(10d).workoutId(1L).build();
        recordService.updateRecord(1L, req);
    }

    @Test
    void 레코드수정할때_레코드없을() {
        given(recordRepository.findById(any())).willReturn(Optional.ofNullable(null));

        RecordDto.Update req = RecordDto.Update.builder().count(10L).setNum(10L).weight(10d).workoutId(1L).build();
        RecordException.NotFound e = assertThrows(RecordException.NotFound.class, () -> recordService.updateRecord(1L,req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void 레코드수정할때_운동이없을떙() {
        Workout w = Workout.builder().id(1L).build();
        Record r = Record.builder().id(1L).workout(w).build();

        given(recordRepository.findById(any())).willReturn(Optional.ofNullable(r));
        given(workoutRepository.findById(any())).willReturn(Optional.ofNullable(null));

        RecordDto.Update req = RecordDto.Update.builder().count(10L).setNum(10L).weight(10d).workoutId(1L).build();
        WorkoutException.NotFound e = assertThrows(WorkoutException.NotFound.class, () -> recordService.updateRecord(1L,req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void 기록리스트() {
        List<Record> records = new ArrayList<>();

        given(recordRepository.findByUserIdByDate(any(),any(),any())).willReturn(records);

        RecordDto.FilterForDate req = RecordDto.FilterForDate.builder().startDate("2020-10-11").endDate("2020-10-20").build();
        recordService.getRecordListByUserIdByDate("test", req);
    }
}
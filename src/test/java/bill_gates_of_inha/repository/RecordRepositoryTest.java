package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.Record;
import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.domain.Workout;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class RecordRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;

    private Workout w;
    private User u;
    private Record r;

    @BeforeEach
    void before() {
        w = Workout.builder().name("벤치").build();
        u = User.builder().userId("123").name("123").password("123").build();
        r = Record.builder().count((long)10).setNum((long)5).weight((double)100).build();

        w = workoutRepository.save(w).get();
        u = userRepository.save(u).get();
    }

    @Test
    void 기록저장() {
        Record s = recordRepository.save(r,u,w).get();

        Assertions.assertEquals(s.getCount(), 10);
        Assertions.assertEquals(s.getSetNum(), 5);
        Assertions.assertEquals(s.getWeight(), 100);
        Assertions.assertEquals(s.getWorkout().getName(), "벤치");
        Assertions.assertEquals(s.getUser().getUserId(), "123");
    }

    @Test
    void 기록찾기() {
        Record ss = recordRepository.save(r,u,w).get();
        Record s = recordRepository.findById(ss.getId()).get();

        Assertions.assertEquals(s.getCount(), 10);
        Assertions.assertEquals(s.getSetNum(), 5);
        Assertions.assertEquals(s.getWeight(), 100);
        Assertions.assertEquals(s.getWorkout().getName(), "벤치");
        Assertions.assertEquals(s.getUser().getUserId(), "123");
    }

    @Test
    void 기록수정() {
        Record ss = recordRepository.save(r,u,w).get();
        Workout ww = Workout.builder().name("스쿼트").build();
        ww = workoutRepository.save(ww).get();

        HashMap<String,Object> map = new HashMap<>();
        map.put("count", 15L);
        map.put("setNum", 1L);
        map.put("weight", 10d);
        map.put("workout", ww);
        recordRepository.update(ss,map);
        em.flush();
        em.clear();

        Record s = recordRepository.findById(ss.getId()).get();

        Assertions.assertEquals(s.getCount(), 15);
        Assertions.assertEquals(s.getSetNum(), 1);
        Assertions.assertEquals(s.getWeight(), 10);
        Assertions.assertEquals(s.getWorkout().getName(), "스쿼트");
        Assertions.assertEquals(s.getUser().getUserId(), "123");
    }

    @Test
    void findByUserIdByDate() {
        Record ss = recordRepository.save(r,u,w).get();
        Record rr = Record.builder().count((long)10).setNum((long)5).weight((double)100).build();
        rr = recordRepository.save(rr, u, w).get();
        LocalDateTime d = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth()-1, d.getHour(), d.getMinute());
        LocalDateTime endDate = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth()+1, d.getHour(), d.getMinute());
        List<Record> list = recordRepository.findByUserIdByDate(u.getUserId(), startDate, endDate);

        Assertions.assertEquals(list.size(), 2);
    }
}
package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.Record;
import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.domain.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordRepository {
    private final EntityManager em;

    @Autowired
    public RecordRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Record> save(Record record, User user, Workout workout) {
        em.persist(record);
        record.setUser(user);
        record.setWorkout(workout);

        return Optional.ofNullable(record);
    }

    public Optional<Record> findById(Long id) {
        Record record = em.find(Record.class, id);

        return Optional.ofNullable(record);
    }

    public void update(Record record, HashMap<String, Object> map) {
        if(map.containsKey("weight")) {
            record.setWeight((Double)map.get("weight"));
        }
        if(map.containsKey("setNum")) {
            record.setSetNum((Long)map.get("setNum"));
        }
        if(map.containsKey("count")) {
            record.setCount((Long)map.get("count"));
        }
        if(map.containsKey("workout")) {
            record.setWorkout((Workout)map.get("workout"));
        }
    }

    public List<Record> findByUserIdByDate(String userId, LocalDateTime startDate, LocalDateTime endDate ) {

        return em.createQuery("select r from Record r" +
                " join fetch r.workout" +
                " where r.user.userId = :userId and (:startDate is null or r.createdAt >= :startDate) and (:endDate is null or r.createdAt <= :endDate)" +
                " order by r.createdAt", Record.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}

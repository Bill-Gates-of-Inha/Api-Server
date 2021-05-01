package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkoutRepository {
    private final EntityManager em;

    @Autowired
    public WorkoutRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Workout> save(Workout workout) {
        em.persist(workout);

        return Optional.ofNullable(workout);
    }

    public Optional<Workout> findById(Long id) {

        return Optional.ofNullable(em.find(Workout.class, id));
    }

    public List<Workout> findAll() {

        return em.createQuery("select w from Workout w", Workout.class).getResultList();
    }
}

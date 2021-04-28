package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class FoodRepository {
    private final EntityManager em;

    @Autowired
    public FoodRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Food> findByName(String name) {
        try {
            return Optional.ofNullable(
                    em.createQuery("select f from Food f where f.name = :name", Food.class)
                            .setParameter("name", name)
                            .getSingleResult()
            );

        } catch(NoResultException e) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Food> save(Food food) {
        em.persist(food);

        return Optional.ofNullable(food);
    }

}

package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final EntityManager em;

    @Autowired
    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<User> findByUserId(String userId) {
        List<User> userList = em.createQuery("select u from User u where u.userId = :userId" , User.class)
                .setParameter("userId", userId)
                .getResultList();

        if(userList.isEmpty() || userList == null) {
            return Optional.ofNullable(null);
        }

        return Optional.ofNullable(userList.get(0));
    }

    public Optional<User> save(User user) {
        em.persist(user);

        return Optional.ofNullable(user);
    }
}

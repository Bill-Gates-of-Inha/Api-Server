package bill_gates_of_inha.repository;

import bill_gates_of_inha.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void 회원가입성공테스트() {
        User user = User.builder().userId("123").name("123").password(passwordEncoder.encode("1234")).build();

        User u = userRepository.save(user).get();

        Assertions.assertEquals(u.getUserId(), user.getUserId());
        Assertions.assertEquals(u.getName(), user.getName());
        Assertions.assertEquals(passwordEncoder.matches("1234", u.getPassword()), true);
    }

    @Test
    void 회원찾기() {
        User user = User.builder().userId("123").name("123").password(passwordEncoder.encode("1234")).build();
        User u = userRepository.save(user).get();

        User fu = userRepository.findByUserId(u.getUserId()).get();
        Assertions.assertEquals(fu.getUserId(), u.getUserId());
        Assertions.assertEquals(fu.getName(), u.getName());
        Assertions.assertEquals(passwordEncoder.matches("1234", fu.getPassword()), true);
    }

    @Test
    void 회원수정() {
        User user = User.builder().userId("123").name("123").password(passwordEncoder.encode("1234")).build();
        User u = userRepository.save(user).get();
        HashMap<String,Object> map = new HashMap<>();
        map.put("address", "포항시 남구 연일읍");
        map.put("name", "피수용");
        userRepository.update(u, map);
        em.flush();
        em.clear();

        User fu = userRepository.findByUserId(u.getUserId()).get();
        Assertions.assertEquals(fu.getAddress(), "포항시 남구 연일읍");
        Assertions.assertEquals(fu.getName(), "피수용");
    }
}
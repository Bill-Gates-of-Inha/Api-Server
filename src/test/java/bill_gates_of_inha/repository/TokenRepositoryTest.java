package bill_gates_of_inha.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TokenRepositoryTest {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    StringRedisTemplate tokenRedisTemplate;

    @AfterEach
    void before() {
        tokenRepository.deleteAll();
    }

    @Test
    void 저장되는() {
        tokenRepository.set("abc", "test");
        String res = tokenRedisTemplate.opsForValue().get("abc");

        Assertions.assertEquals("test", res);
    }

    @Test
    void 잘가져오는() {
        tokenRedisTemplate.opsForValue().set("abc","test");
        String res = tokenRepository.get("abc");

        Assertions.assertEquals("test",res);
    }

    @Test
    void 삭제하는지() {
        tokenRedisTemplate.opsForValue().set("abc","test");
        tokenRepository.delete("abc");

        String res = tokenRedisTemplate.opsForValue().get("abc");

        Assertions.assertEquals(res, null);

    }
}
package bill_gates_of_inha.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RankingRepositoryTest {

    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private StringRedisTemplate rankingRedisTemplate;

    @Test
    void 랭킹추가잘되나() {
        String userName = "asdf";
        String address = "address";
        Double score = 123d;

        rankingRepository.addUserNameAndScoreByAddress(address,userName,score);
        Double res = rankingRedisTemplate.opsForZSet().score(address,userName);
        Set<ZSetOperations.TypedTuple<String>> set = rankingRedisTemplate.opsForZSet().rangeWithScores(address, 0,-1);

        Assertions.assertEquals(1, set.size());
        Assertions.assertEquals(score, res);
    }

    @Test
    void 랭킹즈() {
        String userName = "asdf";
        String userName2 = "asdf2";
        String address = "address";
        Double score = 123d;
        Double score2 = 124d;

        rankingRedisTemplate.opsForZSet().add(address,userName,score);
        rankingRedisTemplate.opsForZSet().add(address,userName2,score2);

        Set<ZSetOperations.TypedTuple<String>> set = rankingRepository.getValueWithScoreByAddress(address);

        Assertions.assertEquals(2, set.size());
    }
}
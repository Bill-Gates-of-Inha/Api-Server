package bill_gates_of_inha.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class RankingRepository {
    private final StringRedisTemplate rankingRedisTemplate;
    private final ZSetOperations<String, String> zSetOperations;

    public RankingRepository(@Qualifier(value = "rankingRedisTemplate") StringRedisTemplate rankingRedisTemplate) {
        this.rankingRedisTemplate = rankingRedisTemplate;
        this.zSetOperations = this.rankingRedisTemplate.opsForZSet();
    }

    public void addUserNameAndScoreByAddress(String address, String userName, double score) {
        zSetOperations.add(address, userName, score);
    }

    public  Set<ZSetOperations.TypedTuple<String>> getValueWithScoreByAddress(String address) {

        return zSetOperations.reverseRangeWithScores(address,0,-1);
    }
}

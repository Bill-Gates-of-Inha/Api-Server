package bill_gates_of_inha.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    @Autowired
    public TokenRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    public void set(String accessToken, String refreshToken) {
        valueOperations.set(accessToken, refreshToken);
    }

    public String get(String accessToken) {
        return valueOperations.get(accessToken);
    }

    public void delete(String accessToken) {
        redisTemplate.delete(accessToken);
    }
}

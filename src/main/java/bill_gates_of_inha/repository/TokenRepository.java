package bill_gates_of_inha.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    private final StringRedisTemplate tokenRedisTemplate;
    private final ValueOperations<String, String> valueOperations;
    
    public TokenRepository(@Qualifier(value = "tokenRedisTemplate") StringRedisTemplate tokenRedisTemplate) {
        this.tokenRedisTemplate = tokenRedisTemplate;
        this.valueOperations = this.tokenRedisTemplate.opsForValue();
    }

    public void set(String accessToken, String refreshToken) {
        valueOperations.set(accessToken, refreshToken);
    }

    public String get(String accessToken) {
        return valueOperations.get(accessToken);
    }

    public void delete(String accessToken) {
        tokenRedisTemplate.delete(accessToken);
    }

    public void deleteAll() {
        tokenRedisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}

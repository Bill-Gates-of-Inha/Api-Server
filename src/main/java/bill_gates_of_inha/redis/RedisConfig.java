package bill_gates_of_inha.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.profiles.active}")
    private String profile;


    @Bean(name = "tokenRedisConnectionFactory")
    @Primary
    public LettuceConnectionFactory tokenRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(0);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "rankingRedisConnectionFactory")
    public LettuceConnectionFactory rankingRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(1);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "tokenRedisTemplate")
    public StringRedisTemplate tokenRedisTemplate(@Qualifier(value = "tokenRedisConnectionFactory") LettuceConnectionFactory tokenRedisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(tokenRedisConnectionFactory);

        if(!profile.equals("production")) {
            redisTemplate.getConnectionFactory().getConnection().flushAll();
        }

        return redisTemplate;
    }

    @Bean(name = "rankingRedisTemplate")
    public StringRedisTemplate rankingRedisTemplate(@Qualifier(value = "rankingRedisConnectionFactory") LettuceConnectionFactory rankingRedisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(rankingRedisConnectionFactory);

        if(!profile.equals("production")) {
            redisTemplate.getConnectionFactory().getConnection().flushAll();
        }

        return redisTemplate;
    }
}

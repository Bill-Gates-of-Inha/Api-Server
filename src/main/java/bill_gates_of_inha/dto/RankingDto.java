package bill_gates_of_inha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;

public class RankingDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Ranking {
        private String userName;
        private Double score;

        public static Ranking toDto(ZSetOperations.TypedTuple<String> typedTuple) {

            return Ranking.builder()
                    .userName(typedTuple.getValue())
                    .score(typedTuple.getScore())
                    .build();
        }
    }
}

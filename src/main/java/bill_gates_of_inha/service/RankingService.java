package bill_gates_of_inha.service;

import bill_gates_of_inha.dto.RankingDto;
import bill_gates_of_inha.repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RankingService {
    private final RankingRepository rankingRepository;

    @Autowired
    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public List<RankingDto.Ranking> getRankingListByAddress(String address) {
        Set<ZSetOperations.TypedTuple<String>> valueWithScoreSet = rankingRepository.getValueWithScoreByAddress(address);

        return valueWithScoreSet.stream().map(RankingDto.Ranking::toDto).collect(Collectors.toList());
    }
}

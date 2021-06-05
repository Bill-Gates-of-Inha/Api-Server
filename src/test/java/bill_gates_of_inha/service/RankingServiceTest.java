package bill_gates_of_inha.service;

import bill_gates_of_inha.dto.RankingDto;
import bill_gates_of_inha.repository.RankingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RankingServiceTest {
    @Mock
    private RankingRepository rankingRepository;
    @InjectMocks
    private RankingService rankingService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRankingListByAddress() {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        given(rankingRepository.getValueWithScoreByAddress(any())).willReturn(set);

        List<RankingDto.Ranking> list = rankingService.getRankingListByAddress("1234");

        Assertions.assertEquals(list.size(), set.size());
    }
}
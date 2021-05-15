package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.RankingDto;
import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankingController {
    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    public ResponseDto.Ok getRankingListByAddress(String address) {
        List<RankingDto.Ranking> rankingList = rankingService.getRankingListByAddress(address);

        return new ResponseDto.Ok(rankingList, "랭킹리스트");
    }
}

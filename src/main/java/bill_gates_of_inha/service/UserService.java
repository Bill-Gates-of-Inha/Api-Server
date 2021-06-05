package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.repository.RankingRepository;
import bill_gates_of_inha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RankingRepository rankingRepository;

    @Autowired
    public UserService(UserRepository userRepository, RankingRepository rankingRepository) {
        this.userRepository = userRepository;
        this.rankingRepository = rankingRepository;
    }

    public UserDto.User getProfileByUserId(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NotFoundUser::new);

        return UserDto.User.toDto(user);
    }

    public void updateProfile(String userId, UserDto.Update req) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NotFoundUser::new);
        HashMap<String, Object> updateMap = new HashMap<>();

        if(req.getName() != null) {
            updateMap.put("name", req.getName());
        }
        if(req.getAddress() != null) {
            updateMap.put("address", req.getAddress());
        }

        userRepository.update(user, updateMap);
    }

    public void updateScoreByUserId(String userId, Double score) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NotFoundUser::new);

        if(user.getAddress() == null) {
            throw new UserException.NotFoundUserAddress();
        }

        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("score", score);

        userRepository.update(user, updateMap);
        rankingRepository.addUserNameAndScoreByAddress(user.getAddress(), user.getName(), score);
    }
}

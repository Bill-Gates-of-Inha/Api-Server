package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto.User getProfileByUserId(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NotFoundUser::new);

        return UserDto.User.toDto(user);
    }
}
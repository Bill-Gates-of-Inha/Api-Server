package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.exception.UserException;
import bill_gates_of_inha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class AuthService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto.SignUpResult signUp(UserDto.Creation req) {
        if (userRepository.findByUserId(req.getUserId()).isPresent()) {
            throw new UserException.Reduplication();
        }

        String encodedPassword = passwordEncoder.encode(req.getPassword());

        User user = User.builder()
                .userId(req.getUserId())
                .password(encodedPassword)
                .name(req.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userRepository.save(user).orElseThrow(UserException.NotFoundUser::new);

        return UserDto.SignUpResult.builder().result(true).build();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserException.NotFoundUser::new);

        return user;
    }

    public void comparePassword(String password, String savedPassword) {
        if (!passwordEncoder.matches(password, savedPassword)) {
            throw new UserException.WrongPassword();
        }
    }
}

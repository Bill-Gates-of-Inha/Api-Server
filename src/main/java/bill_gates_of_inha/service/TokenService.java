package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.TokenDto;
import bill_gates_of_inha.exception.TokenException;
import bill_gates_of_inha.repository.TokenRepository;
import bill_gates_of_inha.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public TokenService(TokenRepository tokenRepository, JwtProvider jwtProvider) {
        this.tokenRepository = tokenRepository;
        this.jwtProvider = jwtProvider;
    }

    public TokenDto.Token generateToken(User user) {
        String accessToken = jwtProvider.createToken(user.getUsername(), user.getRoles());
        String refreshToken = jwtProvider.createRefreshToken();
        tokenRepository.set(accessToken, refreshToken);

        return TokenDto.Token.toDto(accessToken, refreshToken);
    }

    public TokenDto.Token regenerateToken(TokenDto.Token req) {
        String accessToken = req.getAccessToken();
        String refreshToken = req.getRefreshToken();

        if(!tokenRepository.get(accessToken).equals(refreshToken)) {
            throw new TokenException.Conflict();
        }

        if(!jwtProvider.validateToken(refreshToken)) {
            throw new TokenException.Expired();
        }

        tokenRepository.delete(accessToken);

        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        String userId = authentication.getName();
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String newAccessToken = jwtProvider.createToken(userId, authorities);
        String newRefreshToken = jwtProvider.createRefreshToken();

        tokenRepository.set(newAccessToken, newRefreshToken);

        return TokenDto.Token.toDto(newAccessToken, newRefreshToken);
    }
}

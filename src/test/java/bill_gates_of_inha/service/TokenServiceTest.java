package bill_gates_of_inha.service;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.TokenDto;
import bill_gates_of_inha.exception.TokenException;
import bill_gates_of_inha.repository.TokenRepository;
import bill_gates_of_inha.security.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class TokenServiceTest {
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void 토큰생성() {
        final User user = User.builder().build();
        final String at = "1234";
        final String rt = "1235";

        given(jwtProvider.createToken(any(), any())).willReturn(at);
        given(jwtProvider.createRefreshToken()).willReturn(rt);

        final TokenDto.Token tokenDto = tokenService.generateToken(user);
        Assertions.assertEquals(at, tokenDto.getAccessToken());
        Assertions.assertEquals(rt, tokenDto.getRefreshToken());
    }

    @Test
    void 토큰재발() {
        final UserDetails userDetails = User.builder()
                .userId("1234")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        final String accessToken = "1234";
        final String refreshToken = "12345";
        final TokenDto.Token req = TokenDto.Token.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        final String newAccessToken = "224";
        final String newRefreshToken = "225";
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        given(tokenRepository.get(any())).willReturn(refreshToken);
        given(jwtProvider.validateToken(any())).willReturn(true);
        given(jwtProvider.getAuthentication(any())).willReturn(authentication);
        given(jwtProvider.createToken(any(),any())).willReturn(newAccessToken);
        given(jwtProvider.createRefreshToken()).willReturn(newRefreshToken);

        final TokenDto.Token res = tokenService.regenerateToken(req);

        Assertions.assertEquals(res.getAccessToken(), newAccessToken);
        Assertions.assertEquals(res.getRefreshToken(), newRefreshToken);
    }

    @Test
    void 토큰검증실패() {
        final String accessToken = "1234";
        final String refreshToken = "12345";
        final TokenDto.Token req = TokenDto.Token.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        given(tokenRepository.get(any())).willReturn("123");

        TokenException.Conflict e = assertThrows(TokenException.Conflict.class, ()->tokenService.regenerateToken(req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.CONFLICT);
    }

    @Test
    void 토큰만료() {
        final String accessToken = "1234";
        final String refreshToken = "12345";
        final TokenDto.Token req = TokenDto.Token.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        given(tokenRepository.get(any())).willReturn(refreshToken);
        given(jwtProvider.validateToken(any())).willReturn(false);

        TokenException.Expired e = assertThrows(TokenException.Expired.class, ()->tokenService.regenerateToken(req));

        Assertions.assertEquals(e.getStatus(), HttpStatus.UNAUTHORIZED);
    }
}
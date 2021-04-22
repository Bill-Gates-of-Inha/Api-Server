package bill_gates_of_inha.controller;

import bill_gates_of_inha.domain.User;
import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.dto.TokenDto;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.service.AuthService;
import bill_gates_of_inha.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService){
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping ("/api/auth/signup")
    public ResponseDto.Created signUp(@RequestBody UserDto.Creation req) {
        UserDto.SignUpResult signUpResult = authService.signUp(req);

        return new ResponseDto.Created(signUpResult, "회원가입");
    }

    @PostMapping ("/api/auth/signin")
    public ResponseDto.Ok signIn(@RequestBody UserDto.SignIn req)  {
        User user = (User)authService.loadUserByUsername(req.getUserId());
        authService.comparePassword(req.getPassword(), user.getPassword());
        TokenDto.Token tokenDto = tokenService.generateToken(user);

        return new ResponseDto.Ok(tokenDto, "로그인");
    }

    @PostMapping("/api/auth/re")
    public ResponseDto.Ok regenerateToken(@RequestBody TokenDto.Token req) {
        TokenDto.Token tokenDto = tokenService.regenerateToken(req);

        return new ResponseDto.Ok(tokenDto, "재발급");
    }

    @GetMapping(value = "/api/auth/exception/forbidden")
    public ResponseDto.Exception AuthorizationForbiddenException() {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        return new ResponseDto.Exception(httpStatus, httpStatus.value(), "권한이 없습니다.");
    }

    @GetMapping(value = "/api/auth/exception/expired")
    public ResponseDto.Exception TokenExpiredException() {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        return new ResponseDto.Exception(httpStatus, httpStatus.value(), "accessToken이 유효하지 않습니다.");
    }
}

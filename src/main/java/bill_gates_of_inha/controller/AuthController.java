package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping ("/api/users/signup")
    public ResponseDto.Created signUp(@RequestBody UserDto.Creation req) {
        UserDto.SignUpResult signUpResult = userService.signUp(req);

        return new ResponseDto.Created(signUpResult, "회원가입");
    }

    @PostMapping ("/api/users/signin")
    public ResponseDto.Ok signUp(@RequestBody UserDto.SignIn req)  {
        UserDto.User userDto = userService.signIn(req);

        return new ResponseDto.Ok(userDto, "로그인");
    }

}

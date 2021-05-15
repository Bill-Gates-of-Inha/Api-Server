package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.ResponseDto;
import bill_gates_of_inha.dto.UserDto;
import bill_gates_of_inha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/{userId}")
    public ResponseDto.Ok getProfileByUserId(@PathVariable("userId") String userId) {
        UserDto.User userDto = userService.getProfileByUserId(userId);

        return new ResponseDto.Ok(userDto, "사용자 정보");
    }

    @PatchMapping("/api/users/{userId}")
    public ResponseDto.Ok updateProfileByUserId(@PathVariable("userId") String userId, @RequestBody UserDto.Update req) {
        userService.updateProfile(userId, req);
        UserDto.User userDto = userService.getProfileByUserId(userId);

        return new ResponseDto.Ok(userDto, "사용자 정보 업데이트");
    }
}

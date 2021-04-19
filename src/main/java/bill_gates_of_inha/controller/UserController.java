package bill_gates_of_inha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    public UserController(){}

    @GetMapping("/api/users")
    public List<String> test() {

        return new ArrayList(Arrays.asList("피수영", "함지용", "이석현"));
    }

}

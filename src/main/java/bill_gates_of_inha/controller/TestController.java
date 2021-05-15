package bill_gates_of_inha.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    @Value("${version}")
    private String version;

    @GetMapping("/version")
    public String getVersion() {
        if(version==null) {
            return "dev";
        }

        return version;
    }
}

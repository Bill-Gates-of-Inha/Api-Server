package bill_gates_of_inha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenException {
    public static class Expired extends ResponseStatusException {
        public Expired() {
            super(HttpStatus.UNAUTHORIZED, "refreshToken이 만료되었습니다.");
        }
    }

    public static class Conflict extends ResponseStatusException {
        public Conflict() {
            super(HttpStatus.CONFLICT, "Token이 일치하지 않습니다.");
        }
    }
}

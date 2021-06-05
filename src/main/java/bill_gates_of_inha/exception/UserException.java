package bill_gates_of_inha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserException {
    public static class Reduplication extends ResponseStatusException {
        public Reduplication() {
            super(HttpStatus.CONFLICT, "존재하는 회원입니다.");
        }
    }

    public static class NotFoundUser extends ResponseStatusException {
        public NotFoundUser() {
            super(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
    }

    public static class WrongPassword  extends ResponseStatusException {
        public WrongPassword() {
            super(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.");
        }
    }

    public static class NotFoundUserAddress  extends ResponseStatusException {
        public NotFoundUserAddress() {
            super(HttpStatus.NOT_FOUND, "주소가 존재하지 않습니다.");
        }
    }
}

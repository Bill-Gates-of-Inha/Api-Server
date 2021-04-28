package bill_gates_of_inha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FoodException {
    public static class NotFoundFood extends ResponseStatusException {
        public NotFoundFood() {
            super(HttpStatus.NOT_FOUND, "음식을 찾을 수 없습니다.");
        }
    }
}

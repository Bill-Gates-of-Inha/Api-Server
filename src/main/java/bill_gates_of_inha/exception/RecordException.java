package bill_gates_of_inha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecordException {
    public static class NotFound extends ResponseStatusException {
        public NotFound() {
            super(HttpStatus.NOT_FOUND, "운동기록을 찾을 수 없습니다.");
        }
    }
}

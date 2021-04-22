package bill_gates_of_inha.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ResponseDto {
    @Getter
    public static class Created<T> {
        private T data;
        private HttpStatus status;
        private int code;
        private String message;

        public Created(T data, String message) {
            this.data = data;
            this.status = HttpStatus.CREATED;
            this.code = HttpStatus.CREATED.value();
            this.message = message;
        }
    }

    @Getter
    public static class Ok<T> {
        private T data;
        private HttpStatus status;
        private int code;
        private String message;

        public Ok(T data, String message) {
            this.data = data;
            this.status = HttpStatus.OK;
            this.code = HttpStatus.OK.value();
            this.message = message;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Exception {
        private HttpStatus status;
        private int code;
        private String message;
    }
}

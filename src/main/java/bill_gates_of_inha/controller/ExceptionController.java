package bill_gates_of_inha.controller;

import bill_gates_of_inha.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler({
            ResponseStatusException.class,
    })
    public ResponseDto.Exception ResponseStatusException(final ResponseStatusException e) {

        return new ResponseDto.Exception(e.getStatus(), e.getRawStatusCode(), e.getReason());
    }


}
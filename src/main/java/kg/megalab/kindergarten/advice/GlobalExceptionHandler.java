package kg.megalab.kindergarten.advice;

import kg.megalab.kindergarten.exception.ConflictException;
import kg.megalab.kindergarten.exception.LogicException;
import kg.megalab.kindergarten.exception.NotFoundException;
import kg.megalab.kindergarten.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalResponse> notFound(NotFoundException exception) {
        GlobalResponse response = GlobalResponse.notFound(exception.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<GlobalResponse> conflict(ConflictException exception) {
        GlobalResponse response = GlobalResponse.conflict(exception.getMessage());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(LogicException.class)
    public ResponseEntity<GlobalResponse> logicError(Exception exception) {
        GlobalResponse response = GlobalResponse.badRequest("Logic error");
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalResponse> internalError(Exception exception) {
        GlobalResponse response = GlobalResponse.serverError("Internal Server Error");
        return ResponseEntity.status(500).body(response);
    }

}

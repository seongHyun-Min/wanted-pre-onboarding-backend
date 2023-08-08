package com.example.wantedboard.exception.user;

import com.example.wantedboard.controller.UserController;
import com.example.wantedboard.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice(basePackageClasses = UserController.class)
@Slf4j
public class UserExceptionHandler {
    private static final HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorMessage> DuplicateUserException(
            DuplicateUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorMessage> NotFoundUserException(
            NotFoundUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(ForbiddenUserException.class)
    public ResponseEntity<ErrorMessage> ForbiddenUserException(
            ForbiddenUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorMessage> unauthorizedUserException(
            UnauthorizedUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);

        return ResponseEntity.badRequest()
                .body(new ErrorMessage(exception, errorMessage, errorStatus));
    }
}

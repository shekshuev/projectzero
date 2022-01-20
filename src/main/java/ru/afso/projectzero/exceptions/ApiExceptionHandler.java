package ru.afso.projectzero.exceptions;

import io.jsonwebtoken.JwtException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.ErrorResponse;

import javax.security.auth.message.AuthException;
import org.springframework.security.access.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ErrorResponse<>(String.format("Constraint error: %s", e.getConstraintName()));
    }

    @ExceptionHandler({EmptyResultDataAccessException.class, NoSuchElementException.class})
    public ApiResponse<String> handleEmptyResultDataAccessException() {
        return new ErrorResponse<>("No entity with such id!");
    }

    @ExceptionHandler({AuthException.class, AccessDeniedException.class, JwtException.class})
    public ResponseEntity<? extends ApiResponse> handleAuthException(Exception e) {
        return new ResponseEntity<>(new ErrorResponse<>(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<String> handleNumberFormatException(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse<>(String.format("Not valid argument: %s", e.getParameter().getParameterName()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse<String> error = new ErrorResponse<>(ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse<String> error = new ErrorResponse<>("Something wrong with request body");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse<String> error = new ErrorResponse<>("Method not supported");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

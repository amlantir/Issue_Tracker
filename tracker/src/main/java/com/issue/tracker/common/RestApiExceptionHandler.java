package com.issue.tracker.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<String> handleAllUncaughtExceptions(RuntimeException exception,
                                                                    HttpServletRequest request) {
        String message = messageSource.getMessage(
                ErrorCode.ERROR_UNKNOWN.getPropertyName(),
                null,
                Locale.getDefault());

        String stacktrace = ExceptionUtils.getStackTrace(exception);

        ErrorMessage errorMessage = new ErrorMessage(
                message,
                request.getRequestURL().toString(),
                stacktrace,
                OffsetDateTime.now()
        );

        log.info(String.valueOf(errorMessage));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<String> handleBadCredentialsException() {

        String message = messageSource.getMessage(
                ErrorCode.ERROR_CREDENTIALS.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<String> handleAccessDeniedException() {

        String message = messageSource.getMessage(
                ErrorCode.ERROR_AUTHORIZATION.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<String> handleIoException() {

        String message = messageSource.getMessage(
                ErrorCode.ERROR_IO.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String message = messageSource.getMessage(
                ErrorCode.ERROR_ARGUMENT.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public final ResponseEntity<String> handleFileAlreadyExistsException() {

        String message = messageSource.getMessage(
                ErrorCode.ERROR_FILE_ALREADY_EXISTS.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<String> handleNoSuchElementException() {

        String message = messageSource.getMessage(
                ErrorCode.ERROR_NO_SUCH_ELEMENT.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class, SizeLimitExceededException.class})
    public final ResponseEntity<String> handleMaxUploadSizeExceededException() {

        String message = messageSource.getMessage(
                ErrorCode.ERROR_FILE_TOO_BIG.getPropertyName(),
                null,
                Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

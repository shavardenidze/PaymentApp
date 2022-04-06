package ge.lshavardenidze.paymentapp.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handle(Exception ex, WebRequest request) {
        logger.error(ex != null ? ex.getMessage() : "Exception is null", ex);
        if (ex instanceof PaymentException) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                    request.getDescription(false), ((PaymentException) ex).getHttpStatus() == null ? HttpStatus.NOT_ACCEPTABLE.getReasonPhrase() : ((PaymentException) ex).getHttpStatus().getReasonPhrase());
            return new ResponseEntity<>(exceptionResponse, ((PaymentException) ex).getHttpStatus() == null ? HttpStatus.NOT_ACCEPTABLE : ((PaymentException) ex).getHttpStatus());
        } else {
            ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Internal server error",
                    request.getDescription(false), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String error = "";
        if (!ObjectUtils.isEmpty(errors)) {
            error = errors.get(0);
        }
        ExceptionResponse response = new ExceptionResponse(new Date(), error, request.getDescription(false), status.getReasonPhrase());
        return new ResponseEntity<>(response, headers, status);
//        //Get all errors

    }
}

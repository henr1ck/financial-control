package br.edu.ifpi.financialcontrol.handler;

import br.edu.ifpi.financialcontrol.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Resource not found!")
                .detail(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldDetails> fieldDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, Locale.getDefault());
                    String fieldName = fieldError.getField();

                    return FieldDetails.builder()
                            .field(fieldName)
                            .developerMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Validation error")
                .detail("One or more properties aren't valid")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .fields(fieldDetails)
                .build();

        return new ResponseEntity<>(problemDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable throwable = ex.getRootCause();

        if (throwable instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) throwable);
        } else if (throwable instanceof JsonParseException) {
            return handleJsonParseException((JsonParseException) throwable);
        }

        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Message not readable")
                .detail("Request body message isn't valid")
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, status);
    }

    public ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException exception) {
        List<JsonMappingException.Reference> pathReferences = exception.getPath();
        ;

        String propertyPath = pathReferences.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        String knownProperties = exception.getKnownPropertyIds().stream()
                .map(Object::toString)
                .map(prop -> pathReferences.size() > 1 ? pathReferences.get(0).getFieldName().concat(".").concat(prop) : prop)
                .collect(Collectors.joining(", "));

        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Unrecognized property")
                .detail(String.format("Unrecognized '%s' property. Known properties: %s", propertyPath, knownProperties))
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> handleJsonParseException(JsonParseException exception) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Json parse error")
                .detail("There is a syntax error for the JSON request body")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .title(status.getReasonPhrase())
                .detail("Request method not supported for this endpoint")
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .title(status.getReasonPhrase())
                .detail("Media type not supported")
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object value = ex.getValue();
        String propertyName = value != null ? value.toString() : "Property";

        Class<?> requiredType = ex.getRequiredType();
        String typeName = requiredType != null ? requiredType.getSimpleName() : "expected";

        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Type mismatch")
                .detail(String.format("%s value in path variable isn't a %s type", propertyName, typeName))
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldDetails> fieldDetails = ex.getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, Locale.getDefault());
                    return FieldDetails.builder()
                            .field(fieldError.getField())
                            .developerMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Type mismatch")
                .detail("Failed to convert one or more parameter values")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .fields(fieldDetails)
                .build();

        return new ResponseEntity<>(problemDetails, status);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ProblemDetails> handleDateTimeException(DateTimeException exception){
        String message = exception.getMessage();
        ProblemDetails details = ProblemDetails.builder()
                .title("Date Time Error")
                .detail(message)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleAnyException(Exception exception) {
        ProblemDetails problemDetails = ProblemDetails.builder()
                .title("Unexpected error")
                .detail("Please, contact the system developer")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(problemDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

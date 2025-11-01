package dev.alexisardaya.productservice.exception;

import dev.alexisardaya.productservice.dto.ErrorResponse;
import dev.alexisardaya.productservice.exception.DuplicateResourceException;
import dev.alexisardaya.productservice.exception.OperationNotAllowedException;
import dev.alexisardaya.productservice.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "RESOURCE_NOT_FOUND",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
      DuplicateResourceException ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "DUPLICATE_RESOURCE",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(OperationNotAllowedException.class)
  public ResponseEntity<ErrorResponse> handleOperationNotAllowedException(
      OperationNotAllowedException ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "OPERATION_NOT_ALLOWED",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));

    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "VALIDATION_ERROR",
        message,
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex, HttpServletRequest request) {
    String message = ex.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.joining(", "));

    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "VALIDATION_ERROR",
        message,
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex, HttpServletRequest request) {
    String message = "Error de integridad de datos";
    
    // Detectar específicamente errores de duplicado
    if (ex.getMessage() != null && (
        ex.getMessage().contains("duplicate key") ||
        ex.getMessage().contains("unique constraint") ||
        ex.getMessage().contains("Duplicate entry"))) {
      message = "El recurso ya existe. No se permite duplicados.";
    } else if (ex.getMessage() != null && (
        ex.getMessage().contains("foreign key") ||
        ex.getMessage().contains("constraint"))) {
      message = "No se puede realizar la operación debido a restricciones de integridad referencial.";
    }

    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "DATA_INTEGRITY_VIOLATION",
        message,
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(
      Exception ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        OffsetDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "INTERNAL_SERVER_ERROR",
        "Ha ocurrido un error inesperado: " + ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}


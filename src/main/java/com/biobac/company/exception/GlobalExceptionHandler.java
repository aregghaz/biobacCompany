package com.biobac.company.exception;

import com.biobac.company.response.ApiResponse;
import com.biobac.company.utils.ResponseUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        int status = ex.getStatusCode().value();
        String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
        return ResponseEntity.status(status).body(ResponseUtil.error(message));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse<Object>> handleFeignException(FeignException ex) {
        String errorMessage = "Internal Server Error";

        try {
            String body = ex.contentUTF8();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(body);

            if (node.has("message")) {
                errorMessage = node.get("message").asText();
            } else {
                errorMessage = body;
            }
        } catch (Exception parseEx) {
            errorMessage = ex.getMessage();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseUtil.error(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();

            field = field.replaceAll("\\[\\d+\\]", "[]");

            String friendlyField;
            if ("username".equals(field)) {
                friendlyField = "Username";
            } else if ("email".equals(field)) {
                friendlyField = "Email";
            } else if ("password".equals(field)) {
                friendlyField = "Password";
            } else if (field.startsWith("roles[].roleName")) {
                friendlyField = "Roles";
            } else if (field.startsWith("roles[].permissions")) {
                friendlyField = "Permissions";
            } else if (field.startsWith("roles[].permissions[].permissionName")) {
                friendlyField = "Permissions";
            } else {
                friendlyField = field;
            }

            errors.merge(friendlyField, message, (existing, newMsg) -> existing + "; " + newMsg);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseUtil.error("Validation failed", errors));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.error(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateException(DuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseUtil.error(ex.getMessage()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseUtil.error(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtil.error("Access denied"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseUtil.error("Internal server error"));
    }
}
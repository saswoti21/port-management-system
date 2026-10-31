package com.portmanagement.exception;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portmanagement.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
		
		@ExceptionHandler(NoSuchElementException.class)
		public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NoSuchElementException exception){
			
			ApiResponse<Void> response=new ApiResponse<>(HttpStatus.NOT_FOUND.value(), 
					exception.getMessage(), 
					null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		@ExceptionHandler(IllegalArgumentException.class)
		public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException exception) {

		    ApiResponse<Void> response = new ApiResponse<>(HttpStatus.CONFLICT.value(), 
		    		exception.getMessage(), 
		    		null);
		    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		

		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<ApiResponse<Void>> handleValidationException(
		        MethodArgumentNotValidException exception) {
		    String message = exception.getBindingResult()
		            .getFieldErrors()
		            .stream()
		            .map(error -> error.getField() + ": " + error.getDefaultMessage())
		            .findFirst()
		            .orElse("Invalid request");
		
		    ApiResponse<Void> response = new ApiResponse<>(
		            HttpStatus.BAD_REQUEST.value(),
		            message,
		            null);
		    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(DataIntegrityViolationException.class)
		public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException exception){
			ApiResponse<Void> response=new ApiResponse<>(HttpStatus.CONFLICT.value(),
					"Database constraint violation, Please check your input",
					null);
			return new ResponseEntity<>(response,HttpStatus.CONFLICT);
		}

}

package com.myretailcompany.rest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	final Logger logger = LogManager.getLogger(getClass());
	
    private MessageSource messageSource;
	
    
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return customizeErrorMessage(ex, headers, status, request);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception rnfe,
			HttpServletRequest request) {

			ErrorDetail errorDetail = new ErrorDetail();
			errorDetail.setTimeStamp(new Date().getTime());
			errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
			errorDetail.setTitle("Resource Not Found");
			errorDetail.setDetail(rnfe.getMessage());
			errorDetail.setDeveloperMessage(rnfe.getClass().getName());
			return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
		
	}

	//TODO: Spring failing to start
	//@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException manve, HttpServletRequest request) {

		ErrorDetail errorDetail = new ErrorDetail();
		// Populate errorDetail instance
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestPath == null) {
			requestPath = request.getRequestURI();
		}
		errorDetail.setTitle("Validation Failed");
		errorDetail.setDetail("Input validation failed");
		errorDetail.setDeveloperMessage(manve.getClass().getName());

		// Create ValidationError instances
		List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
		for (FieldError fe : fieldErrors) {
			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
			if (validationErrorList == null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			logger.info("fe.getCode() = "+fe.getCode()+ "fe.getDefaultMessage() = "+fe.getDefaultMessage());
			logger.info("messageSource = "+messageSource);
			validationError.setMessage(fe.getDefaultMessage());
			validationErrorList.add(validationError);
		}

		return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
	}

	
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return customizeErrorMessage(ex, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return customizeErrorMessage(ex, headers, status, request);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return customizeErrorMessage(manve, headers, status, request);
	}
	
	

	private ResponseEntity<Object> customizeErrorMessage(Exception ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Date().getTime());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle(ex.getClass().getName());
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass().getName());
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}

}

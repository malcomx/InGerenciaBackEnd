package com.ingerencia.test.api.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ingerencia.test.api.exception.InGerenciaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GeneralHandlerException {

	@ExceptionHandler(InGerenciaException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<String> handlerMaxorException(InGerenciaException ex, HttpServletRequest request) {
		
		log.info("Exception custom InGerenciaException: {}", ex.getMessage());
		
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.OK);
	}

    @ExceptionHandler({
    	MissingServletRequestParameterException.class,
    	UnsatisfiedServletRequestParameterException.class,
    	HttpRequestMethodNotSupportedException.class,
    	ServletRequestBindingException.class
    })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleBadRequest(Exception ex, HttpServletRequest request){
		
		log.error("Http status: " + HttpStatus.BAD_REQUEST + ", " + ex.getMessage());
		
		return new ResponseEntity<String>("El request no ha sido creado corrctamente", HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<String> handleUnssuprtedMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest request){
    	
		log.error("Http status: " + HttpStatus.UNSUPPORTED_MEDIA_TYPE + ", " + ex.getMessage());
		
		return new ResponseEntity<String>("Media Type no soportada", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleInternalError(Exception ex, HttpServletRequest request){
    	
		log.error("Http status: " + HttpStatus.INTERNAL_SERVER_ERROR + ", " + ex.getMessage());
		
		return new ResponseEntity<String>("Error interno en el servidor, consulte al administrador", HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFoundHandle(NoHandlerFoundException ex) {
		return "404";
	}
	
}

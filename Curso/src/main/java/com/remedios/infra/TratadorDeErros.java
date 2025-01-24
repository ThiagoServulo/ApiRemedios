package com.remedios.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

// RestControllerAdvice - notaçãop para indicar que as exceções são tratadas por essa classe
@RestControllerAdvice
public class TratadorDeErros
{
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratador404()
	{
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratador400(MethodArgumentNotValidException exception)
	{
		var error = exception.getFieldErrors();
		
		return ResponseEntity.badRequest().body(error.stream().map(DadosError::new).toList());
	}
	
	public record DadosError(String messase, String field)
	{
		public DadosError(FieldError erro)
		{
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}

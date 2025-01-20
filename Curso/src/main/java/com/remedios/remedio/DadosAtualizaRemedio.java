package com.remedios.remedio;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizaRemedio(
		@NotNull
		Long id, 
		String nome, 
		Via via, 
		Laboratorio laboratorio)
{

}

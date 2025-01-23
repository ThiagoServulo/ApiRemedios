package com.remedios.remedio;

import java.time.LocalDate;

public record DadosDetalhamentoRemedio(Long id, String nome, Via via, String lote, int quantidade, 
		Laboratorio laboratorio, LocalDate validade, boolean ativo)
{
	public DadosDetalhamentoRemedio(Remedio remedio)
	{
		this(remedio.getId(), remedio.getNome(), remedio.getVia(), remedio.getLote(), remedio.getQuantidade(),
				remedio.getLaboratorio(), remedio.getValidade(), remedio.isAtivo());
	}
}

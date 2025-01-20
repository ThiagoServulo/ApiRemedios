package com.remedios.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remedios.remedio.DadosAtualizaRemedio;
import com.remedios.remedio.DadosCadastroRemedio;
import com.remedios.remedio.DadosListagemRemedios;
import com.remedios.remedio.Remedio;
import com.remedios.remedio.RemedioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
public class RemedioController
{
	@Autowired
	private RemedioRepository repository;
	
	@PostMapping
	@Transactional //garante o rollback em caso de erro
	public void cadastrar(@RequestBody @Valid DadosCadastroRemedio dados)
	{
		repository.save(new Remedio(dados));
	}
	
	@GetMapping
	public List<DadosListagemRemedios> listar()
	{
		return repository.findAll().stream().map(DadosListagemRemedios::new).toList();
	}
	
	@PutMapping
	@Transactional //garante o rollback em caso de erro
	public void atualizar(@RequestBody @Valid DadosAtualizaRemedio dados)
	{
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void deletar(@PathVariable Long id)
	{
		repository.deleteById(id);
	}
}

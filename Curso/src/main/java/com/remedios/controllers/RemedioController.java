package com.remedios.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.remedios.remedio.DadosAtualizaRemedio;
import com.remedios.remedio.DadosCadastroRemedio;
import com.remedios.remedio.DadosDetalhamentoRemedio;
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
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados, UriComponentsBuilder builder)
	{
		var remedio = new Remedio(dados);
		repository.save(remedio);
		
		var uri = builder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}
	
	@GetMapping
	public ResponseEntity<List<DadosListagemRemedios>> listar()
	{
		var lista = repository.findAll().stream().map(DadosListagemRemedios::new).toList();
		
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedio> buscarPeloId(@PathVariable Long id)
	{
		var remedio = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
	
	@PutMapping
	@Transactional //garante o rollback em caso de erro
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizaRemedio dados)
	{
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> deletar(@PathVariable Long id)
	{
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable Long id)
	{
		var remedio = repository.getReferenceById(id);
		remedio.inativar();
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<Void> ativar(@PathVariable Long id)
	{
		var remedio = repository.getReferenceById(id);
		remedio.ativar();
		
		return ResponseEntity.noContent().build();
	}
}

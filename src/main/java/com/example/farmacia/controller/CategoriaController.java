package com.example.farmacia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmacia.model.Categoria;
import com.example.farmacia.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		List<Categoria> categorias = repository.findAll();
		return ResponseEntity.ok(categorias);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		return repository.findById(id).map(categoria->ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/{tipo}")
	public ResponseEntity<List<Categoria>> getByNome(@PathVariable String tipo){
		List<Categoria> categoriasEncontradas = repository.findByTipoContainingIgnoreCase(tipo);
		if(categoriasEncontradas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(categoriasEncontradas);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
		//Optional<Produto> produtoAtual = repository.findById(id);
		return repository.findById(id).map(ct->{
			ct.setTarja(categoria.getTarja());
			ct.setTipo(categoria.getTipo());
			ct.setClassificacao(categoria.getClassificacao());
			return ResponseEntity.ok(repository.save(ct));
		}).orElse(ResponseEntity.notFound().build());
 	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		repository.deleteById(id);
	}
}

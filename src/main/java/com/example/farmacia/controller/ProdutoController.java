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

import com.example.farmacia.model.Produto;
import com.example.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
		
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		List<Produto> produtos = repository.findAll();
		return ResponseEntity.ok(produtos);
	}
	
	
	@PostMapping
	public ResponseEntity<Produto> post( @Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return repository.findById(id).map(categoria->ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		List<Produto> produtosEncontrados = repository.findByNomeProdutoContainingIgnoreCase(nome);
		if(produtosEncontrados.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(produtosEncontrados);
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long id,@Valid @RequestBody Produto produto){
		//Optional<Produto> produtoAtual = repository.findById(id);
		return repository.findById(id).map(pd->{
			pd.setNomeProduto(produto.getNomeProduto());
			pd.setDescricao(produto.getDescricao());
			pd.setPreco(produto.getPreco());
			pd.setCategoria(produto.getCategoria());
			return ResponseEntity.ok(pd);
		}).orElse(ResponseEntity.notFound().build());
 	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) {
		repository.deleteById(id);
	}
}

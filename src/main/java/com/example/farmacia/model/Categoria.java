package com.example.farmacia.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tb_categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private enum Classificacao{
		Genérico,Similar,Refêrencia
	}
	
	@Enumerated(EnumType.STRING)
	private Classificacao classificacao;
	
	private enum Tarja{
		Preta,Vermelha,Amarela,SemTarja
	};
	
	@Enumerated(EnumType.STRING)
	private Tarja tarja;
	
	private String tipo;
	
	@OneToMany(mappedBy="categoria",cascade= CascadeType.ALL)
	private List<Produto> produtos;
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Classificacao getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}

	public Tarja getTarja() {
		return tarja;
	}

	public void setTarja(Tarja tarja) {
		this.tarja = tarja;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

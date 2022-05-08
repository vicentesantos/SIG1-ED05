package com.fatec.sig1.model;

import java.text.DateFormat;	
import java.text.ParseException;	
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import org.joda.time.DateTime;	
import org.joda.time.format.DateTimeFormat;	
import org.joda.time.format.DateTimeFormatter;

@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Nome é requerido")
	private String nome;
	private long codBarras;
	private String marca;
	private String descricao;
	private int qtdEstoque;
	private Double preco;
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String dataCadastro;

	public Produto(String nome, Double preco) {
		this.nome = nome;
		this.setPreco(preco);
	}

	public Produto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public long getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(long codBarras) {
		this.codBarras = codBarras;
	}
	public boolean validaData(String data) {	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		df.setLenient(false); //	
		try {	
			df.parse(data); // data válida (exemplo 30 fev - 31 nov)	
			return true;	
		} catch (ParseException ex) {	
			return false;	
		}	
	}	
	public void obtemDataAtual(DateTime dataAtual) {	
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");	
		this.setDataCadastro(dataAtual.toString(fmt));	
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	// equals e tostring omitidos.

}
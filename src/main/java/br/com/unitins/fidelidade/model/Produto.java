package br.com.unitins.fidelidade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name="TB_PRODUTO", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Data
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 49212977968023201L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProduto;
	
	//@NotNull
	@Column(name="nome", length=200, nullable=false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@Column(name="pontosRecebidos", nullable = false)
	private Integer pontosRecebidos;
	
	@Column(name="pontosRetirada", nullable = false)
	private Integer pontosRetirada;
	
	private boolean status;
	
	private byte[] imagem;
	
	public Produto(String nome, Categoria categoria, byte[] imagem, Integer pontosRecebidos, Integer pontosRetirada) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.imagem = imagem;
		this.pontosRecebidos = pontosRecebidos;
		this.pontosRetirada = pontosRetirada;
		this.status = true;
	}

	public Produto() {
		this.status = true;
	}

}
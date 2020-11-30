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
import javax.validation.constraints.NotNull;

import br.com.unitins.fidelidade.exception.ValidationGroups;
import lombok.Data;

@Entity
@Table(name="TB_PRODUTO", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Data
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 49212977968023201L;
	
	@NotNull(groups = ValidationGroups.produtoId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProduto;
	
	@NotNull
	@Column(name="nome", length=200, nullable=false)
	private String nome;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
	@NotNull
	private Integer pontosRecebidos;
	
	@NotNull
	private Integer pontosRetirada;
	
	@NotNull
	private boolean status;
	
	@NotNull
	private String urlImage;
	
	public Produto(String nome, Categoria categoria, Integer pontosRecebidos, Integer pontosRetirada, boolean status, String urlImage) {
		super();
		this.nome = nome;
		this.categoria = categoria;
		this.pontosRecebidos = pontosRecebidos;
		this.pontosRetirada = pontosRetirada;
		this.status = status;
		this.urlImage = urlImage;
	}

	public Produto() {
	}

}
package br.com.unitins.fidelidade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.unitins.fidelidade.exception.ValidationGroups;
import lombok.Data;

@Entity
@Table(name="TB_CATEGORIA", uniqueConstraints = {@UniqueConstraint(columnNames={"nome"})})
@Data
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 5775600215216301704L;
	
	@NotNull(groups = ValidationGroups.categoriaId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCategoria;
	
	@NotNull
	@Column(name="nome", nullable = false)
	private String nome;
	
	@NotNull
	private boolean status;
	
	public Categoria(String nome, boolean status) {
		this.nome = nome;
		this.status = status;
	}

	public Categoria() {
	}
	
	public Categoria(long idCategoria) {
		this.idCategoria  = idCategoria;
	}
	
}
package br.com.unitins.fidelidade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "TB_PROMOCAO")
@Data
public class Promocao implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2834676050173926965L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPromocao;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull
	private boolean status;

	private byte[] imagem;

	@NotNull
	private String urlImg;

	public Promocao() {
		super();
	}

	public Promocao(String nome, boolean status, byte[] imagem, String urlImg) {
		super();
		this.nome = nome;
		this.status = status;
		this.imagem = imagem;
		this.urlImg = urlImg;
	}

}

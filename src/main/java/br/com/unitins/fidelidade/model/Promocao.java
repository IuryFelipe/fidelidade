package br.com.unitins.fidelidade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="TB_PROMOCAO")
@Data
public class Promocao implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 2834676050173926965L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPromocao;
	
	//@Column(name="nome", nullable = false) tirando obrigatoriedade para teste
	private String nome;
	
	private boolean status;
	
	@Lob
	private String imagem;

	public Promocao(String nome, String imagem) {
		super();
		this.nome = nome;
		this.status = true;
		this.imagem = imagem;
	}
	public Promocao() {
		super();
	}
	
	
	
}

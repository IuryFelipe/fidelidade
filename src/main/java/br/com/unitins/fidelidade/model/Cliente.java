package br.com.unitins.fidelidade.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {

	private static final long serialVersionUID = -3109859511744004660L;

	@Column(name = "telefone", nullable = false)
	private String telefone;
	private Integer pontos;

	@Builder
	public Cliente() {
	}

	@Builder
	public Cliente(String nome, String cpf, String email, String telefone, Integer pontos) {
		super(nome, cpf, email);
		this.telefone = telefone;
		this.pontos = pontos;
	}

}
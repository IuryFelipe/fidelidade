package br.com.unitins.fidelidade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_CLIENTE", uniqueConstraints = {@UniqueConstraint(columnNames={"cpf"})})
@Getter
@Setter
public class Cliente extends Usuario {

	private static final long serialVersionUID = -3109859511744004660L;

	@NotBlank
	@Column(name = "telefone", nullable = false)
	private String telefone;
	
	private Integer pontos;

	@Builder
	public Cliente(String nome, String cpf, String email, String telefone, Integer pontos) {
		super(nome, cpf, email);
		this.telefone = telefone;
		this.pontos = pontos;
	}
	
	@Builder
	public Cliente() {
		
	}


}
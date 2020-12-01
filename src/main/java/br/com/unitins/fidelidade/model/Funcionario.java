package br.com.unitins.fidelidade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.unitins.fidelidade.security.SimpleEncryptor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_FUNCIONARIO", uniqueConstraints = {@UniqueConstraint(columnNames={"cpf"})})
@Getter
@Setter()
public class Funcionario extends Usuario {

	private static final long serialVersionUID = -2513556090171419870L;

	@NotBlank
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_permissao")
	private Permissao permissao;

	@Builder
	public Funcionario() {
	}

	@Builder
	public Funcionario(String nome, String cpf, String email, String senha, Permissao permissao) {
		super(nome, cpf, email);
		this.senha = SimpleEncryptor.sha256(senha);
		this.permissao = permissao;
	}
	
	public void setSenha(String senha) {
		this.senha = SimpleEncryptor.sha256(senha);
	}

}
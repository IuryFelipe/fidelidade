package br.com.unitins.fidelidade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name="TB_PERMISSAO")
@Data
public class Permissao implements GrantedAuthority {

	private static final long serialVersionUID = 3226675709104105792L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPermissao;
	@Column(name="descricao", nullable = false)
	private String descricao;
	
	public Permissao() {}
	
	public Permissao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getAuthority() {
		return null;
	}

}
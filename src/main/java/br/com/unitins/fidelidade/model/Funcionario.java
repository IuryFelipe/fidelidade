package br.com.unitins.fidelidade.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_FUNCIONARIO", uniqueConstraints = {@UniqueConstraint(columnNames={"cpf"})})
@Getter
@Setter()
public class Funcionario extends Usuario implements UserDetails {

	private static final long serialVersionUID = -2513556090171419870L;

	@Column(name = "senha", nullable = true)
	private String senha;

	@Builder
	public Funcionario() {
	}

	@Builder
	public Funcionario(String nome, String cpf, String email, String senha, Permissao permissao) {
		super(nome, cpf, email);
		this.senha = senha;
		setPermissao(permissao);
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<Permissao> permissoes = new ArrayList<Permissao>();
		permissoes.add(getPermissao());
		
		return (Collection<? extends GrantedAuthority>) permissoes;
	}

	@Override
	public String getPassword() {
		return new BCryptPasswordEncoder().encode(this.senha);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
package br.com.unitins.fidelidade.model;

import java.util.Collection;

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
@Table(name="TB_CLIENTE", uniqueConstraints = {@UniqueConstraint(columnNames={"cpf"})})
@Getter
@Setter
public class Cliente extends Usuario implements UserDetails {

	private static final long serialVersionUID = -3109859511744004660L;

	@Column(name = "telefone", nullable = true)
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder().encode(this.telefone);
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
package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	Funcionario findById(long id);
	
	Funcionario findByCpf(String cpf);
	
	Funcionario findByEmail(String email);
	
	Funcionario findByNome(String nome);
	
}
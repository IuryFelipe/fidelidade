package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	@Query("SELECT f FROM Funcionario f WHERE f.status = 'true' AND f.idUsuario = :id")
	Funcionario findById(@Param("id") long id);
	@Query("SELECT f FROM Funcionario f WHERE f.status = 'true' AND f.cpf = :cpf")
	Funcionario findByCpf(@Param("cpf") String cpf);
	@Query("SELECT f FROM Funcionario f WHERE f.status = 'true' AND f.email = :email")
	Funcionario findByEmail(@Param("email") String email);
	
	
	
}
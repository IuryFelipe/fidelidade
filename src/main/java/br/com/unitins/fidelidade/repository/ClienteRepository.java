package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{
	
	@Query("SELECT c FROM Cliente c WHERE c.status = 'true' AND c.idUsuario = :id")
	Cliente findById(@Param("id") long id);
	@Query("SELECT c FROM Cliente c WHERE c.status = 'true' AND c.cpf = :cpf")
	Cliente findByCpf(@Param("cpf") String cpf);
	@Query("SELECT c FROM Cliente c WHERE c.status = 'true' AND c.email = :email")
	Cliente findByEmail(@Param("email") String email);
	
}
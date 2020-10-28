package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

	Cliente findById(long id);

	Cliente findByCpf(String cpf);
	
}
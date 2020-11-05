package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.model.Cliente;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Movimentacao findById(long id);
	Movimentacao findByCliente(Cliente cliente);
}
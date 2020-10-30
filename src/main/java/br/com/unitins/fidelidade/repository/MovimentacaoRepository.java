package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
	
}
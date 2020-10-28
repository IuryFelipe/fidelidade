package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.HistoricoTroca;

public interface HistoricoTrocaRepository extends JpaRepository<HistoricoTroca, Long> {
	
}
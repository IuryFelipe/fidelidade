package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{

	Promocao findById(long id);
	Promocao findByNome(String nome);
}

package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{
	
	@Query("SELECT p FROM Promocao p WHERE p.status = 'true' AND p.idPromocao = :id")
	Promocao findById(@Param("id") long id);
	@Query("SELECT p FROM Promocao p WHERE p.status = 'true' AND p.nome like :nome")
	Promocao findByNome(@Param("nome") String nome);
}

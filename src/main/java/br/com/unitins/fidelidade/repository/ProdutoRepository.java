package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query("SELECT p FROM Produto p WHERE p.status = 'true' AND p.idProduto = :id")
	Produto findById(@Param("id") long id);
	@Query("SELECT p FROM Produto p WHERE p.status = 'true' AND p.nome like :nome")
	Produto findByNome(@Param("nome") String nome);
}
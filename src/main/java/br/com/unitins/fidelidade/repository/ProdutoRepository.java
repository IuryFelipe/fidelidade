package br.com.unitins.fidelidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.unitins.fidelidade.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	Produto findById(long id);
	List<Produto> findByNome(String nome);
	@Query(value = "SELECT p FROM Produto p WHERE p.status = 'true'")
	List<Produto> findAllAtivos();
}
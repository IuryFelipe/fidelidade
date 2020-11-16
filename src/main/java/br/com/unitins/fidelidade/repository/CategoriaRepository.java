package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	@Query("SELECT c FROM Categoria c  WHERE c.status = 'true' AND c.idCategoria = :id")
	Categoria findById(@Param("id") long id);

}
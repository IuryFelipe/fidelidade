package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	Categoria findById(long id);

	Categoria findByNome(String nome);

}
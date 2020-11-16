package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{
<<<<<<< HEAD

	Promocao findById(long id);
	Promocao findByNome(String nome);
}
=======
	
	@Query("SELECT p FROM Promocao p WHERE p.status = 'true' AND p.idPromocao = :id")
	Promocao findById(@Param("id") long id);
	@Query("SELECT p FROM Promocao p WHERE p.status = 'true' AND p.nome like :nome")
	Promocao findByNome(@Param("nome") String nome);
}
>>>>>>> 16d574e6740a21c3c8a4d2dfba8c843dbd8e0643

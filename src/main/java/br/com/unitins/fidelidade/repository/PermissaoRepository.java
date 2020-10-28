package br.com.unitins.fidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unitins.fidelidade.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

	Permissao findById(long id);
}
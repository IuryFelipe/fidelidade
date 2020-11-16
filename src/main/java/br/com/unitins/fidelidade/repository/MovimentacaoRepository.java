package br.com.unitins.fidelidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Movimentacao findById(long id);
    List<Movimentacao> findByCliente(Cliente cliente);
    @Query(value = "SELECT m FROM Movimentacao m WHERE m.cliente = :cliente AND m.operacao like 'Troca'")
    List<Movimentacao> findByOperacaoCliente(@Param("cliente") Cliente cliente);
    List<Movimentacao> findByOperacao(String operacao);
}
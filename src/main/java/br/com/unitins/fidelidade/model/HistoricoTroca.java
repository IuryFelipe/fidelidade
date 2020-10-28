package br.com.unitins.fidelidade.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="TB_HISTORICOTROCA")
@Data
public class HistoricoTroca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idHistoricoTroca;
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOperacao;
	@Column(name="pontosClienteAnterior", nullable = false)
	private Integer pontosClienteAnterior;
	@Column(name="pontosClientePosterior", nullable = false)
	private Integer pontosClientePosterior;
	@Column(name="pontosOperacao", nullable = false)
	private Integer pontosOperacao;
	
	
	@PrePersist
	public void dataOperacao() {
		dataOperacao = new Date();
	}

	public HistoricoTroca() {}

	public HistoricoTroca(Cliente cliente, Produto produto) {
		this.cliente = cliente;
		this.produto = produto;
		this.pontosClienteAnterior = cliente.getPontos();
		this.pontosClientePosterior = cliente.getPontos() - produto.getPontosRetirada();
		this.pontosOperacao = produto.getPontosRetirada();
	}
	
}
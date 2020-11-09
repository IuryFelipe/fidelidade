package br.com.unitins.fidelidade.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="TB_MOVIMENTACAO")
@Data
public class Movimentacao implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1775585936366706834L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idMovimentacao;
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOperacao;
	@Column(name = "pontosClienteAnterior", nullable = false)
	private Integer pontosClienteAnterior;
	@Column(name = "pontosClientePosterior", nullable = false)
	private Integer pontosClientePosterior;
	@Column(name = "pontosOperacao", nullable = false)
	private Integer pontosOperacao;

	@PrePersist
	public void dataOperacao() {
		dataOperacao = new Date();
	}

	public Movimentacao() {
	}

	public Movimentacao(Cliente cliente, Produto produto, String op) {
		this.cliente = cliente;
		this.produto = produto;
		this.pontosClienteAnterior = cliente.getPontos();
		if(op == "+"){
			this.pontosClientePosterior = cliente.getPontos() + produto.getPontosRecebidos();
		}
		else{
			this.pontosClientePosterior = cliente.getPontos() - produto.getPontosRetirada();
		}
		this.pontosOperacao = this.pontosClientePosterior - this.pontosClienteAnterior;
	}
	
}
package br.com.unitins.fidelidade.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.model.Produto;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class MovimentacaoResource {
    
	@Autowired
	MovimentacaoRepository movimentacaoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ClienteResource clienteResource;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	ProdutoResource produtoResource;
	
	@GetMapping("/Movimentacoes")
	public List<Movimentacao> findAll() {
		return movimentacaoRepository.findAll();
	}
	
	@PostMapping("/Movimentacoes")
	public boolean createMovimentacao(@RequestBody Movimentacao movimentacao) {
		return movimentacaoRepository.save(movimentacao);
	}
    
    @GetMapping("/Movimentacoes/{idCliente}")
	public Movimentacao findByCliente(@PathVariable(value = "idCliente") long id ) {
		Cliente cliente = clienteRepository.findById(id);
		return movimentacaoRepository.findByCliente(cliente);
	}

	@GetMapping("/Movimentacoes/trocarPontos/{cpfCliente}/{idProduto}")
	public String trocarPontos(@PathVariable(value = "cpfCliente") String cpf, @PathVariable(value = "idProduto") long idProduto ) {
		Cliente cliente = clienteRepository.findByCpf(cpf);
		Produto produto = produtoRepository.findById(idProduto);
		if(cliente.getPontos() >= produto.getPontosRetirada()){
			realizarTroca(cliente, produto);
			Movimentacao movimentacao = new Movimentacao(cliente, produto, "-");
			if(createMovimentacao(movimentacao))
				return "Troca Realizada!!";
		}
		return "Troca não Realizada!!";
	}

	@GetMapping("/Movimentacoes/adicionarPontos/{cpfCliente}/{idProduto}")
	public String adicionarPontos(@PathVariable(value = "cpfCliente") String cpf, @PathVariable(value = "idProduto") long idProduto ) {
		Cliente cliente = clienteRepository.findByCpf(cpf);
		Produto produto = produtoRepository.findById(idProduto);
		realizarTroca(cliente, produto);
		Movimentacao movimentacao = new Movimentacao(cliente, produto, "+");
		if(createMovimentacao(movimentacao))
			return "Troca Realizada!!";
		else
			return "Troca não Realizada!!";
	}

	private Boolean realizarTroca(Cliente cliente, Produto produto){
		try {
			Movimentacao troca = new Movimentacao(cliente, produto, "-");
			troca.setProduto(produtoResource.findById(produto.getIdProduto()));
			clienteResource.updateCliente(troca.getCliente());
			movimentacaoRepository.save(troca);
			return true;
		  }
		  catch(Exception e) {
			return false;
		  }
	}
}

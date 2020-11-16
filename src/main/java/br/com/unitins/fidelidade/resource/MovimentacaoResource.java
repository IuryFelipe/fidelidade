package br.com.unitins.fidelidade.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.exception.PontosException;
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
	
	@PostMapping("/Movimentacao/AdicionarPontos")
	public boolean createMovimentacaoAdicionar(@RequestBody Movimentacao movimentacao) {
		movimentacao.setCliente(clienteRepository.findById(movimentacao.getCliente().getIdUsuario()));
		movimentacao.setProdutos(produtoResource.findListaProdutos(movimentacao.getProdutos()));
		movimentacao.setOperacao("Compra");
		try {
			adicionarPontos(movimentacao);
			
			movimentacaoRepository.save(movimentacao);
			clienteResource.updateCliente(movimentacao.getCliente());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@PostMapping("/Movimentacao/RealizarTroca")
	public boolean createMovimentacaoTrocar(@RequestBody Movimentacao movimentacao) {
		movimentacao.setCliente(clienteRepository.findById(movimentacao.getCliente().getIdUsuario()));
		movimentacao.setProdutos(produtoResource.findListaProdutos(movimentacao.getProdutos()));
		movimentacao.setOperacao("Troca");
		try {
			trocarPontos(movimentacao);
			
			movimentacaoRepository.save(movimentacao);
			clienteResource.updateCliente(movimentacao.getCliente());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/Movimentacoes")
	public List<Movimentacao> findAll() {
		return movimentacaoRepository.findAll();
	}
	
    @GetMapping("/Movimentacoes/{cpfCliente}")
	public List<Movimentacao> findByCliente(@PathVariable(value = "cpfCliente") String cpf ) {
		Cliente cliente = clienteRepository.findByCpf(cpf);
		return movimentacaoRepository.findByCliente(cliente);
	}
    
    @GetMapping("/Movimentacoes/operacao/{cpfCliente}")
	public List<Movimentacao> findByOPeracaoCliente(@PathVariable(value = "cpfCliente") String cpf){
		Cliente cliente = clienteRepository.findByCpf(cpf);
		return movimentacaoRepository.findByOperacaoCliente(cliente);
	}
    
	public void adicionarPontos(Movimentacao movimentacao) throws Exception {
		Integer pontosProdutos = 0;
		for (Produto produto : movimentacao.getProdutos()) {
			pontosProdutos += produto.getPontosRecebidos();
		}
		
		movimentacao.setPontosClienteAnterior(movimentacao.getCliente().getPontos());
		movimentacao.setPontosOperacao(pontosProdutos);
		movimentacao.setPontosClientePosterior(pontosProdutos + movimentacao.getCliente().getPontos());
		movimentacao.getCliente().setPontos(movimentacao.getPontosClientePosterior());
	}
    
	public void trocarPontos(Movimentacao movimentacao) throws Exception, PontosException {
		Integer pontosProdutos = 0;
		for (Produto produto : movimentacao.getProdutos()) {
			pontosProdutos += produto.getPontosRetirada();
		}
		
		if(pontosProdutos > movimentacao.getCliente().getPontos())
			throw new PontosException("Pontos insuficientes.");
		
		movimentacao.setPontosClienteAnterior(movimentacao.getCliente().getPontos());
		movimentacao.setPontosOperacao(pontosProdutos);
		movimentacao.setPontosClientePosterior(movimentacao.getCliente().getPontos() - pontosProdutos);
		movimentacao.getCliente().setPontos(movimentacao.getPontosClientePosterior());
	}

}

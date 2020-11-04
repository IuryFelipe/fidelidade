package br.com.unitins.fidelidade.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class MovimentacaoResource {
    
	@Autowired
	MovimentacaoRepository movimentacaoRepository;
	ClienteRepository clienteRepository;
	
	@GetMapping("/Movimentacoes")
	public List<Movimentacao> findAll() {
		return movimentacaoRepository.findAll();
    }
    
    @GetMapping("/Movimentacoes/{idCliente}")
	public Movimentacao findByCliente(@PathVariable(value = "idCliente") long id ) {
		Cliente cliente = clienteRepository.findById(id);
		return movimentacaoRepository.findByCliente(cliente);
	}
}

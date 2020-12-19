package br.com.unitins.fidelidade.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.exception.NegocioException;
import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	MovimentacaoRepository MovimentacaoRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/clientes")
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/cliente/{idCliente}")
	public Cliente findById(@PathVariable(value = "idCliente") long id) {
		return clienteRepository.findById(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/cliente/nome/{nome}")
	public Cliente findByNome(@PathVariable(value = "nome") String nome ) {
		return clienteRepository.findByNome(nome);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/cliente/cpf/{cpfCliente}")
	public Cliente findByCpf(@PathVariable(value = "cpfCliente") String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/cliente/{idCliente}")
	public void deleteCliente(@PathVariable(value = "idCliente") long id) {
		List<Movimentacao> listMovimentacaoExistente = MovimentacaoRepository.findByIdCliente(id);
		if (!listMovimentacaoExistente.isEmpty()) {
			throw new NegocioException("Você não pode excluir um cliente que possui movimentacoes cadastradas.");
		}
		clienteRepository.deleteById(id);
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
		if (clienteExistente != null) {
			if (clienteExistente.getIdUsuario() != cliente.getIdUsuario()) {
			throw new NegocioException("Este CPF já foi cadastrado.");
			}
		}
		if (cliente.getPontos() == null) {
			cliente.setPontos(0);
		}
		return new ResponseEntity<Cliente>(clienteRepository.save(cliente), HttpStatus.CREATED);
	}

	@PutMapping("/cliente")
	public ResponseEntity<Cliente> updateCliente(@RequestBody @Valid Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
		if (clienteExistente != null) {
			if (clienteExistente.getIdUsuario() != cliente.getIdUsuario()) {
				throw new NegocioException("Este CPF não foi cadastrado.");
			}
		}
		return new ResponseEntity<Cliente>(clienteRepository.save(cliente), HttpStatus.CREATED);
	}

}
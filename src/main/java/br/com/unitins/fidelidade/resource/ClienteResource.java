package br.com.unitins.fidelidade.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/clientes")
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@GetMapping("/cliente/id/{idCliente}")
	public Cliente findById(@PathVariable(value = "idCliente") long id) {
		return clienteRepository.findById(id);
	}

	@PostMapping("/cliente")
	public Cliente createCliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@PutMapping("/cliente")
	public Cliente updateCliente(@RequestBody @Valid Cliente antigoCliente) {
		return clienteRepository.save(antigoCliente);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/cliente/{idCliente}")
	public void deleteCliente(@PathVariable(value = "idCliente") long id ) {
		List<Movimentacao> listMovimentacaoExistente= MovimentacaoRepository.findByIdCliente(id);

		if(!listMovimentacaoExistente.isEmpty()) {
			throw new NegocioException("Você não pode excluir um cliente que possui movimentacoes cadastradas.");
		}
		clienteRepository.deleteById(id);
		
		
	}

	@GetMapping("/cliente/cpf/{cpfCliente}")
	public Cliente findByCpf(@PathVariable(value = "cpfCliente") String cpf) {
		return clienteRepository.findByCpf(cpf);
	}

}
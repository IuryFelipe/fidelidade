package br.com.unitins.fidelidade.resource;

import java.util.List;

import javax.validation.Valid;

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
import br.com.unitins.fidelidade.repository.ClienteRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class ClienteResource {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping("/clientes")
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/cliente/{idCliente}")
	public Cliente findById(@PathVariable(value = "idCliente") long id ) {
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
	
	@DeleteMapping("/cliente")
	public void deleteCliente(@RequestBody Cliente cliente) {
		cliente.setStatus(false);
		clienteRepository.save(cliente);
	}

	@GetMapping("/cliente/{cpfCliente}")
	public Cliente findByCpf(@PathVariable(value = "cpfCliente") String cpf ) {
		return clienteRepository.findByCpf(cpf);
	}
	
}
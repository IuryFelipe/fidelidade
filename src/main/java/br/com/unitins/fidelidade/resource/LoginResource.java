package br.com.unitins.fidelidade.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.exception.NegocioException;
import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Funcionario;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.FuncionarioRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class LoginResource {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	FuncionarioRepository funcionarioRepository;

	@GetMapping("/login/cliente/{email}/{telefone}")
	public ResponseEntity<Cliente> autenticarCliente(@PathVariable(value = "email") String email,
			@PathVariable(value = "telefone") String telefone) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new NegocioException("Email ou senha inválidos.");
		}
		if (!cliente.getTelefone().equals(telefone)) {
			throw new NegocioException("Email ou senha inválidos.");
		}
		return new ResponseEntity<Cliente>(clienteRepository.save(cliente), HttpStatus.OK);
	}

	@GetMapping("/login/funcionario/{email}/{senha}")
	public ResponseEntity<Funcionario> autenticarFuncionario(@PathVariable(value = "email") String email,
			@PathVariable(value = "senha") String senha) {
		Funcionario funcionario = funcionarioRepository.findByEmail(email);

		if (funcionario == null) {
			throw new NegocioException("Email ou senha inválidos.");
		}
		if (!funcionario.getSenha().equals(senha)) {
			throw new NegocioException("Email ou senha inválidos.");
		}
		return new ResponseEntity<Funcionario>(funcionarioRepository.save(funcionario), HttpStatus.OK);
	}

}

package br.com.unitins.fidelidade.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public String autenticarCliente(@PathVariable(value = "email") String email, 
			@PathVariable(value= "telefone") String telefone) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null)
			return "Email ou senha inválidos.";
		if(!cliente.getTelefone().equals(telefone))
			return "Email ou senha inválidos.";
		
		return "Acesso liberado.";
		
	}
	
	/*
	 * @GetMapping("/login/funcionario/{email}/{senha}") public String
	 * autenticarFuncionario(@PathVariable(value = "email") String email,
	 * 
	 * @PathVariable(value= "senha") String senha) { Funcionario funcionario =
	 * funcionarioRepository.findByEmail(email);
	 * 
	 * if(funcionario == null) return "Email ou senha inválidos.";
	 * if(!funcionario.getSenha().equals(new BCryptPasswordEncoder().encode(senha)))
	 * return "Email ou senha inválidos.";
	 * 
	 * return "Acesso liberado.";
	 * 
	 * }
	 */
	
	@GetMapping("/login/funcionario/{cpf}/{senha}")
	public String autenticarFuncionario(@PathVariable(value = "cpf") String cpf, 
			@PathVariable(value= "senha") String senha) {
		Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
		
		if(funcionario == null)
			return "Email ou senha inválidos.";
		if(!funcionario.getSenha().equals(new BCryptPasswordEncoder().encode(senha)))
			return "Email ou senha inválidos.";
		
		return "Acesso liberado.";
		
	}
}

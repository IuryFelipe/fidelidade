package br.com.unitins.fidelidade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Funcionario;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.FuncionarioRepository;

@Component
public class ImplementsUserDetailsService implements UserDetailsService {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = null;
		Funcionario funcionario = funcionarioRepository.findByEmail(email);

		if (funcionario == null) {
			cliente = clienteRepository.findByEmail(email);

			if (cliente == null) {
				System.out.println("Deu ruim");
				throw new UsernameNotFoundException("Email inválido");
			}

			else {
				return cliente;
			}
		}

		else {
			return funcionario;
		}

	}

}
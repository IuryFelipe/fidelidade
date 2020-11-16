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

import br.com.unitins.fidelidade.model.Funcionario;
import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.FuncionarioRepository;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;
import br.com.unitins.fidelidade.repository.PermissaoRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;
import br.com.unitins.fidelidade.security.TextEncryptor;

@RestController
@RequestMapping(value = "/fidelidade")
public class FuncionarioResource {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	MovimentacaoRepository movimentacaoRepository; 
	@Autowired
	ClienteResource clienteResource;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ProdutoResource produtoResource;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	PermissaoRepository permissaoRepository;
	
	@GetMapping("/funcionarios")
	public List<Funcionario> findAll() {
		return funcionarioRepository.findAll();
	}
	
	@GetMapping("/funcionario/{idFuncionario}")
	public Funcionario findById(@PathVariable(value = "idFuncionario") long id ) {
		return funcionarioRepository.findById(id);
	}
	
	@PostMapping("/funcionario")
	public Funcionario createFuncionario(@RequestBody Funcionario funcionario) {
		funcionario.setPermissao(permissaoRepository.findById(1));
		return funcionarioRepository.save(funcionario);
	}
	
	@DeleteMapping("/funcionario/{idFuncionario}")
	public void deleteFuncionario(@PathVariable(value = "idFuncionario") long id ) {
		Funcionario funcionario = funcionarioRepository.findById(id);
		funcionario.setStatus(false);
		funcionarioRepository.save(funcionario);
	}
	
	@PutMapping("/funcionario")
	public Funcionario updateFuncionario(@RequestBody Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}
	
	public Movimentacao createMovimentacao(Movimentacao movimentacao) {
		return movimentacaoRepository.save(movimentacao);
	}

	
}
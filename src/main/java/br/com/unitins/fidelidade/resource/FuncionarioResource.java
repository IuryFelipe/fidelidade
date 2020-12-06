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
import br.com.unitins.fidelidade.model.Funcionario;
import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.FuncionarioRepository;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;
import br.com.unitins.fidelidade.repository.PermissaoRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;

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
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/funcionarios")
	public List<Funcionario> findAll() {
		return funcionarioRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/funcionario/{idFuncionario}")
	public Funcionario findById(@PathVariable(value = "idFuncionario") long id ) {
		return funcionarioRepository.findById(id);
	}
	
	@PostMapping("/funcionario")
	public ResponseEntity<Funcionario> createFuncionario(@RequestBody Funcionario funcionario) {
		Funcionario funcionarioExistente = funcionarioRepository.findByCpf(funcionario.getCpf());
		if (funcionarioExistente != null) {
			if (funcionarioExistente.getIdUsuario() != funcionario.getIdUsuario()) {
				throw new NegocioException("Este CPF já foi cadastrado.");
			}
		}else{
			funcionario.setPermissao(permissaoRepository.findById(1));
		}
		return new ResponseEntity<Funcionario>(funcionarioRepository.save(funcionario), HttpStatus.CREATED);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/funcionario/{idFuncionario}")
	public void deleteFuncionario(@PathVariable(value = "idFuncionario") long id ) {
		Funcionario funcionario = funcionarioRepository.findById(id);
		funcionario.setStatus(false);
		funcionarioRepository.save(funcionario);
	}
	
	@PutMapping("/funcionario")
	public ResponseEntity<Funcionario> updateFuncionario(@RequestBody @Valid Funcionario funcionario) {
		Funcionario funcionarioExistente = funcionarioRepository.findByCpf(funcionario.getCpf());
		if (funcionarioExistente != null) {
			if (funcionarioExistente.getIdUsuario() != funcionario.getIdUsuario()) {
				throw new NegocioException("Este cliente não foi cadastrado.");
			}
		}
		return new ResponseEntity<Funcionario>(funcionarioRepository.save(funcionario), HttpStatus.CREATED);
	}
	
	public ResponseEntity<Movimentacao> createMovimentacao(@RequestBody Movimentacao movimentacao) {
		Movimentacao movimentacaoExistente = movimentacaoRepository.findById(movimentacao.getIdMovimentacao());
		if (movimentacaoExistente != null) {
			throw new NegocioException("Esta movimentação já foi cadastrado.");
		}
		return new ResponseEntity<Movimentacao>(movimentacaoRepository.save(movimentacao), HttpStatus.CREATED);
	}

	
}
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
import br.com.unitins.fidelidade.model.Produto;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;
	MovimentacaoRepository movimentacaoRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produtos")
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produtos/ativos")
	public List<Produto> findAllAtivos() {
		return produtoRepository.findAllAtivos();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produto/{idProduto}")
	public Produto findById(@PathVariable(value = "idProduto") long id) {
		return produtoRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produto/nome/{nome}")
	public Produto findById(@PathVariable(value = "nome") String nome) {
		return produtoRepository.findByNome(nome);
	}

	@PostMapping("/produto")
	public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto produto) {
		Produto produtoExistente = produtoRepository.findByNome(produto.getNome());
		if (produtoExistente != null) {
			throw new NegocioException("Este produto já foi cadastrado.");
		}
		return new ResponseEntity<Produto>(produtoRepository.save(produto), HttpStatus.CREATED);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/produto/{idProduto}")
	public void deleteProduto(@PathVariable(value = "idProduto") long id) {
		Produto produto = new Produto();
		produto.setIdProduto(id);
		produtoRepository.delete(produto);
	}

	@PutMapping("/produto")
	public ResponseEntity<Produto> updateProduto(@RequestBody @Valid Produto produto) {
		return new ResponseEntity<Produto>(produtoRepository.save(produto), HttpStatus.CREATED);
	}

}

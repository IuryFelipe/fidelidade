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
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Movimentacao;
import br.com.unitins.fidelidade.model.Produto;
import br.com.unitins.fidelidade.repository.MovimentacaoRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class ProdutoResource {
	
	@Autowired
	ProdutoRepository produtoRepository;
	MovimentacaoRepository movimentacaoRepository;
	
	@GetMapping("/produtos")
	public List<Produto> findAll() {
		return produtoRepository.findAll();
	}
	
	@GetMapping("/produto/{idProduto}")
	public Produto findById(@PathVariable(value = "idProduto") long id) {
		return produtoRepository.findById(id);
	}

	@PostMapping("/produto")
    public ResponseEntity<List<Produto>> createProduto(@RequestBody @Valid List<Produto> produtos, Cliente cliente) {
		System.out.println(produtos);
		for (Produto produto : produtos) {
			produtoRepository.save(produto);
		}
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/produto")
	public void deleteProduto(@RequestBody Produto produto) {
		produtoRepository.delete(produto);
	}
	
	@PutMapping("/produto") 
	public Produto updateProduto(@RequestBody @Valid Produto produto) {
		
		return produtoRepository.save(produto);
	}

}
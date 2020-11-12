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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.unitins.fidelidade.model.Categoria;
import br.com.unitins.fidelidade.model.Cliente;
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
	/*
	Precisa ser corrigido isso aqui
	@PostMapping("/produto")
    public ResponseEntity<List<Produto>> createProduto(@RequestBody @Valid List<Produto> produtos, Cliente cliente) {
		System.out.println(produtos);
		for (Produto produto : produtos) {
			produtoRepository.save(produto);
		}
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.CREATED);
	}*/ 
	
	@PostMapping("/produto")
    public ResponseEntity<Produto> createProduto(@RequestBody MultipartFile imagem, @RequestParam String nome, @RequestParam Categoria categoria, int pontosRecebidos, int pontosRetirada) {
		try {
			Produto produto = new Produto(nome, categoria, imagem.getBytes(), pontosRecebidos, pontosRetirada);
			produtoRepository.save(produto);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}	
	}
	
	@DeleteMapping("/produto/{idProduto}")
	public void deleteProduto(@PathVariable(value = "idProduto") long id) {
		Produto produto = produtoRepository.findById(id);
		produto.setStatus(false);
		produtoRepository.save(produto);
	}
	
	@PutMapping("/produto") 
	public Produto updateProduto(@RequestBody @Valid Produto produto) {
		return produtoRepository.save(produto);
	}

}
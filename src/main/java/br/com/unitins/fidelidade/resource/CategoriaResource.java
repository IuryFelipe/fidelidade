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
import br.com.unitins.fidelidade.model.Categoria;
import br.com.unitins.fidelidade.model.Produto;
import br.com.unitins.fidelidade.repository.CategoriaRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class CategoriaResource {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/categorias")
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/categoria/{idCategoria}")
	public Categoria findById(@PathVariable(value = "idCategoria") long id) {
		return categoriaRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/categoria/{idCategoria}")
	public void deleteCategoria(@PathVariable(value = "idCategoria") long id) {
		List<Produto> listProdutosExistente = produtoRepository.findByCategoria(id);
		if (!listProdutosExistente.isEmpty()) {
			throw new NegocioException("Você não pode excluir uma categoria que possui produtos cadastrados.");
		}
		categoriaRepository.deleteById(id);
	}

	@PostMapping("/categoria")
	public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria categoriaExistente = categoriaRepository.findByNome(categoria.getNome());
		if (categoriaExistente != null) {
			throw new NegocioException("Está categoria já foi cadastrada.");
		}
		return new ResponseEntity<Categoria>(categoriaRepository.save(categoria), HttpStatus.CREATED);
	}

	@PutMapping("/categoria")
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria categoriaExistente = categoriaRepository.findByNome(categoria.getNome());
		if (categoriaExistente != null) {
			if (categoriaExistente.getIdCategoria() != categoria.getIdCategoria()) {
				throw new NegocioException("Está categoria já foi cadastrada.");
			}
		}
		List<Produto> listProdutosExistente = produtoRepository.findByCategoria(categoria.getIdCategoria());
		if (!listProdutosExistente.isEmpty()) {
			for (Produto produto : listProdutosExistente) {
				if (produto.isStatus() != categoria.isStatus()) {
					produto.setStatus(categoria.isStatus());
					produtoRepository.save(produto);
				}
			}
		}
		return new ResponseEntity<Categoria>(categoriaRepository.save(categoria), HttpStatus.CREATED);
	}

}
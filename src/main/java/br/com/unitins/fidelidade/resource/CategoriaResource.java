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

import br.com.unitins.fidelidade.model.Categoria;
import br.com.unitins.fidelidade.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class CategoriaResource {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping("/categorias")
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/categoria/{idCategoria}")
	public Categoria findById(@PathVariable(value = "idCategoria") long id ) {
		return categoriaRepository.findById(id);
	}
	
	@PostMapping("/categoria")
	public Categoria createCategoria(@RequestBody Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	@DeleteMapping("/categoria")
	public void deleteCategoria(@RequestBody Categoria categoria) {
		categoriaRepository.delete(categoria);
	}
	
	@PutMapping("/categoria")
	public Categoria updateCategoria(@RequestBody Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

}
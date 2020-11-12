package br.com.unitins.fidelidade.resource;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

import br.com.unitins.fidelidade.model.Promocao;
import br.com.unitins.fidelidade.repository.PromocaoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class PromocaoResource {

	@Autowired
	PromocaoRepository promocaoRepository;
	
	@GetMapping("/promocoes")
	public List<Promocao> findAll() {
		return promocaoRepository.findAll();
	}
	
	@GetMapping("/promocao/{idPromocao}")
	public Promocao findById(@PathVariable(value = "idPromocao") long id) {
		return promocaoRepository.findById(id);
	}
	
	@GetMapping("/promocao2/{nome}")
	public Promocao findByNome(@PathVariable(value = "nome") String nome) {
		return promocaoRepository.findByNome(nome);
	}
	
	@PostMapping("/promocao")
	public String createPromocao(@RequestParam MultipartFile imagem, @RequestParam  String nome){
		Promocao promocao = null;
		try {
			promocao = new Promocao(nome, true, imagem.getBytes());
			promocaoRepository.save(promocao);
			return "Cadastrado com sucesso";
		} catch (IOException e) {
			return "Erro durante cadastro: " + e.getMessage();
		}
	}
	
	@DeleteMapping("/promocao/{idPromocao}")
	public void deletePromocao(@PathVariable(value = "idPromocao") long id) {
		Promocao promocao = promocaoRepository.findById(id);
		promocao.setStatus(false);
		promocaoRepository.save(promocao);
	}
	
	@PutMapping("/promocao") 
	public Promocao updatePromocao(@RequestBody @Validated Promocao promocao) {	
		return promocaoRepository.save(promocao);
	}
}

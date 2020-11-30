package br.com.unitins.fidelidade.resource;

import java.io.IOException;
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
import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Promocao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.PromocaoRepository;
import br.com.unitins.fidelidade.service.EmailService;

@RestController
@RequestMapping(value = "/fidelidade")
public class PromocaoResource {

	@Autowired
	private PromocaoRepository promocaoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EmailService emailService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/promocoes")
	public List<Promocao> findAll() {
		return promocaoRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/promocao/{idPromocao}")
	public Promocao findById(@PathVariable(value = "idPromocao") long id) {
		Promocao promocao = promocaoRepository.findById(id);
		return promocao;
	}

	@GetMapping("/promocao/nome/{nome}")
	public Promocao findByNome(@PathVariable(value = "nome") String nome) {
		Promocao promocao = promocaoRepository.findByNome(nome);
		return promocao;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/promocao/enviar-email-to-clientes/{idPromocao}")
	public void enviarEmail(@PathVariable(value = "idPromocao") long id) throws IOException {
		Promocao promo = promocaoRepository.findById(id);
		List<Cliente> clientes = clienteRepository.findAll();
		for (Cliente cliente : clientes) {
			emailService.sendMailWithInlineResources(cliente.getEmail(), promo.getNome(), promo.getUrlImage());
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/promocao/{idPromocao}")
	public void deletePromocao(@PathVariable(value = "idPromocao") long id) {
		Promocao promocao = new Promocao();
		promocao.setIdPromocao(id);
		promocaoRepository.delete(promocao);
	}
	
	@PostMapping("/promocao")
	public ResponseEntity<Promocao> createPromocao(@Valid @RequestBody Promocao promocao) {
		Promocao promocaoExistente = promocaoRepository.findByNome(promocao.getNome());
		if (promocaoExistente != null) {
			throw new NegocioException("Esta promocao já foi cadastrado.");
		}
		return new ResponseEntity<Promocao>(promocaoRepository.save(promocao), HttpStatus.CREATED);
	}

	@PutMapping("/promocao")
	public ResponseEntity<Promocao> updatePromocao(@RequestBody @Valid Promocao promocao) {
		return new ResponseEntity<Promocao>(promocaoRepository.save(promocao), HttpStatus.CREATED);
	}

}

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.unitins.fidelidade.exception.NegocioException;
import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Promocao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.PromocaoRepository;
import br.com.unitins.fidelidade.service.EmailService;
import br.com.unitins.fidelidade.service.Utils;

@RestController
@RequestMapping(value = "/fidelidade")
public class PromocaoResource {

	@Autowired
	private PromocaoRepository promocaoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private Utils utils;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/promocoes")
	public List<Promocao> findAll() {
		return promocaoRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/promocao/{idPromocao}")
	public Promocao findById(@PathVariable(value = "idPromocao") long id) {
		Promocao promocao = promocaoRepository.findById(id);
		if (promocao.getImagem() != null) {
			promocao.setImagem(utils.decompressZLib(promocao.getImagem()));
		}
		return promocao;
	}

	@GetMapping("/promocao/nome/{nome}")
	public Promocao findByNome(@PathVariable(value = "nome") String nome) {
		Promocao promocao = promocaoRepository.findByNome(nome);
		if (promocao.getImagem() != null) {
			promocao.setImagem(utils.decompressZLib(promocao.getImagem()));
		}
		return promocao;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/promocao/enviar-email-to-clientes/{idPromocao}")
	public void enviarEmail(@PathVariable(value = "idPromocao") long id) throws IOException {
		Promocao promo = promocaoRepository.findById(id);
		List<Cliente> clientes = clienteRepository.findAll();
		for (Cliente cliente : clientes) {
			emailService.sendMailWithInlineResources(cliente.getEmail(), promo.getNome(), promo.getUrlImg());
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/promocao/{idPromocao}")
	public void deletePromocao(@PathVariable(value = "idPromocao") long id) {
		Promocao promocao = new Promocao();
		promocao.setIdPromocao(id);
		promocaoRepository.delete(promocao);
	}

	@PutMapping("/promocao")
	public ResponseEntity<Promocao> updatePromocao(@RequestBody @Valid Promocao promocao) {
		return new ResponseEntity<Promocao>(promocaoRepository.save(promocao), HttpStatus.CREATED);
	}

	@PostMapping("/promocao/multipart-file")
	public ResponseEntity<Promocao> createPromocao(@RequestParam("nome") String nome,
			@RequestParam("status") String status, @RequestParam("imagem") MultipartFile imagem) throws IOException {
		Promocao promocaoExistente = promocaoRepository.findByNome(nome);
		if (promocaoExistente != null) {
			throw new NegocioException("Está promoção já foi cadastrada.");
		}
		// mock provisório
		// Tales -> criar método aqui para salvar a imagem na nuvem e pegar a url
		// pública da imagem para setar no objeto
		String urlImagem = "https://i.pinimg.com/originals/12/e4/ad/12e4adb1616aa2a5933c55fa9be72e59.jpg";
		Promocao promocao = new Promocao(nome, Boolean.parseBoolean(status), utils.compressZLib(imagem.getBytes()),
				urlImagem);
		return new ResponseEntity<Promocao>(promocaoRepository.save(promocao), HttpStatus.CREATED);
	}

}

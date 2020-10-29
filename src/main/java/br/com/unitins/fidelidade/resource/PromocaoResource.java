package br.com.unitins.fidelidade.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Base64;

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
		//byte[] decodedBytes = Base64.getDecoder().decode(imgBytes);
		//System.out.println("decodedBytes " + new String(decodedBytes));
		return promocaoRepository.findById(id);
	}
	
	@GetMapping("/promocao2/{nome}")
	public Promocao findByNome(@PathVariable(value = "nome") String nome) {
		return promocaoRepository.findByNome(nome);
	}
	
	@PostMapping("/promocao")
	public Promocao createPromocao(@RequestParam MultipartFile imagem, @RequestParam String nome) throws IOException {
		byte[] imgBytes = Base64.getEncoder().encode(imagem.getBytes());//converte imagem para Base64
		Promocao promocao = new Promocao(nome, new String(imgBytes));
		return promocaoRepository.save(promocao);
	}
	
	@DeleteMapping("/promocao")
	public void deletePromocao(@RequestBody Promocao promocao) {
		promocaoRepository.delete(promocao);
	}
	
	@PutMapping("/promocao") 
	public Promocao updatePromocao(@RequestBody Promocao promocao) {	
		return promocaoRepository.save(promocao);
	}
}

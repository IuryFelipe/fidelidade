package br.com.unitins.fidelidade.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
import br.com.unitins.fidelidade.model.Produto;
import br.com.unitins.fidelidade.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressZLib(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produtos")
	public List<Produto> findAll() {
		List<Produto> listProduto = produtoRepository.findAll();
		List<Produto> listProdutoAux = new ArrayList<Produto>();
		if (!listProduto.isEmpty()) {
			for (Produto produto : listProduto) {
				if (produto.getImagem() != null) {
					produto.setImagem(decompressZLib(produto.getImagem()));
				}
				listProdutoAux.add(produto);
			}
		}
		return listProdutoAux;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produtos/ativos")
	public List<Produto> findAllAtivos() {
		List<Produto> listProduto = produtoRepository.findAllAtivos();
		List<Produto> listProdutoAux = new ArrayList<Produto>();
		if (!listProduto.isEmpty()) {
			for (Produto produto : listProduto) {
				if (produto.getImagem() != null) {
					produto.setImagem(decompressZLib(produto.getImagem()));
				}
				listProdutoAux.add(produto);
			}
		}
		return listProdutoAux;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produto/{idProduto}")
	public Produto findById(@PathVariable(value = "idProduto") long id) {
		Produto produto = produtoRepository.findById(id);
		if (produto.getImagem() != null) {
			produto.setImagem(decompressZLib(produto.getImagem()));
		}
		return produto;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/produto/nome/{nome}")
	public Produto findById(@PathVariable(value = "nome") String nome) {
		Produto produto = produtoRepository.findByNome(nome);
		if (produto.getImagem() != null) {
			produto.setImagem(decompressZLib(produto.getImagem()));
		}
		return produto;
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
	
	@PostMapping("/produto/upload-imagem")
	public ResponseEntity<Produto> uplaodImage(@RequestParam("idProduto") long idProduto,
			@RequestParam("imagemFile") MultipartFile imagemFile) throws IOException {
		Produto produto = produtoRepository.findById(idProduto);
		produto.setType(imagemFile.getContentType());
		produto.setImagem(compressZLib(imagemFile.getBytes()));
		return new ResponseEntity<Produto>(produtoRepository.save(produto), HttpStatus.CREATED);
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressZLib(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

}

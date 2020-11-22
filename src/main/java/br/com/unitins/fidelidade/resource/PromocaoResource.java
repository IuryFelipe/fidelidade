package br.com.unitins.fidelidade.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.tomcat.util.codec.binary.Base64;
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

import br.com.unitins.fidelidade.email.EmailService;
import br.com.unitins.fidelidade.model.Promocao;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.PromocaoRepository;

@RestController
@RequestMapping(value = "/fidelidade")
public class PromocaoResource {

	@Autowired
	private PromocaoRepository promocaoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/promocao/enviar-email/{idPromocao}")
	public void enviarEmail(@PathVariable(value = "idPromocao") long id) throws IOException {
		
		Promocao promo = promocaoRepository.findById(id);
		
//		emailService.sendMailWithInlineResources("daniloccuft@gmail.com", promo.getNome(), promo.getImagem().toString());
		
		 ByteArrayInputStream bis = new ByteArrayInputStream(promo.getImagem());
	        Iterator<?> readers = ImageIO.getImageReadersByFormatName("png");
	 
	        //ImageIO is a class containing static methods for locating ImageReaders
	        //and ImageWriters, and performing simple encoding and decoding. 
	 
	        ImageReader reader = (ImageReader) readers.next();
	        Object source = bis; 
	        ImageInputStream iis = ImageIO.createImageInputStream(source); 
	        
	        BufferedImage image = ImageIO.read(iis);	        
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream (); 
	        ImageIO.write (image, "png", outputStream);
	        String encodedImage = Base64.encodeBase64String(outputStream.toByteArray());
	        
	        emailService.sendMailWithInlineResources("daniloccuft@gmail.com", promo.getNome(), encodedImage);
	        
	        /*
	        reader.setInput(iis, true);
	        ImageReadParam param = reader.getDefaultReadParam();
	 
	        Image image = reader.read(0, param);
	        //got an image file
	 
	        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
	        //bufferedImage is the RenderedImage to be written
	 
	        Graphics2D g2 = bufferedImage.createGraphics();
	        g2.drawImage(image, null, null);
	 
	        File imageFile = new File("C:\\testeeee.png");
	        ImageIO.write(bufferedImage, "png", imageFile);
	 
	        System.out.println(imageFile.getPath());
		*/
	        
	        
//		List<Cliente> clientes = clienteRepository.findAll();
//		
//		for (Cliente cliente : clientes) {
//			emailService.sendMailWithInlineResources("daniloccuft@gmail.com", promo.getNome(), promo.getImagem().toString());
//			emailService.sendMailWithInlineResources("daniloccuft@gmail.com", promo.getNome(), "https://i.pinimg.com/originals/12/e4/ad/12e4adb1616aa2a5933c55fa9be72e59.jpg");
//			emailService.sendMailWithInlineResources(cliente.getEmail(), promo.getNome(), "https://i.pinimg.com/originals/12/e4/ad/12e4adb1616aa2a5933c55fa9be72e59.jpg");
//		}
		
	}

	@GetMapping("/promocoes")
	public List<Promocao> findAll() {
		return promocaoRepository.findAll();
	}

	@GetMapping("/promocao/{idPromocao}")
	public Promocao findById(@PathVariable(value = "idPromocao") long id) {
		return promocaoRepository.findById(id);
	}

	@GetMapping("/promocao/{nome}")
	public Promocao findByNome(@PathVariable(value = "nome") String nome) {
		return promocaoRepository.findByNome(nome);
	}

	@PostMapping("/promocao")
	public String createPromocao(@RequestParam MultipartFile imagem, @RequestParam String nome) {
		try {
			Promocao promocao = new Promocao(nome, true, imagem.getBytes());
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

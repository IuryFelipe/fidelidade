package br.com.unitins.fidelidade.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import br.com.unitins.fidelidade.model.Categoria;
import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.model.Funcionario;
import br.com.unitins.fidelidade.model.Permissao;
import br.com.unitins.fidelidade.model.Produto;
import br.com.unitins.fidelidade.model.Promocao;
import br.com.unitins.fidelidade.repository.CategoriaRepository;
import br.com.unitins.fidelidade.repository.ClienteRepository;
import br.com.unitins.fidelidade.repository.FuncionarioRepository;
import br.com.unitins.fidelidade.repository.PermissaoRepository;
import br.com.unitins.fidelidade.repository.ProdutoRepository;
import br.com.unitins.fidelidade.repository.PromocaoRepository;

@Service
public class Dbinit implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private PermissaoRepository permissaoRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PromocaoRepository promocaoRepository;
	@Autowired
	private Utils utils;

	@Override
	public void run(String... args) throws Exception {
		List<Categoria> listaCategoria = new ArrayList<Categoria>();

		Categoria categoria1 = new Categoria("Tortas", true);
		listaCategoria.add(categoria1);
		Categoria categoria2 = new Categoria("Bolos", true);
		listaCategoria.add(categoria2);
		Categoria categoria3 = new Categoria("Doces", true);
		listaCategoria.add(categoria3);

		categoriaRepository.saveAll(listaCategoria);

		List<Produto> listaProduto = new ArrayList<Produto>();
		Produto produto1 = new Produto("Bolo de cenoura", categoria2, 10, 100, true);
		produto1.setType("https://comidinhasdochef.com/wp-content/uploads/2018/08/Bolo-de-Cenoura-Cozida.jpg");
		listaProduto.add(produto1);
		Produto produto2 = new Produto("Torta de Morango", categoria1, 12, 120, true);
		produto2.setType("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVDMckV5R1RwwVzXDzbweSzCMWZ_nntZsTAw&usqp=CAU");
		listaProduto.add(produto2);
		Produto produto3 = new Produto("Doce de leite 200g", categoria3, 8, 80, true);
		produto3.setType("https://comidinhasdochef.com/wp-content/uploads/2020/03/Doce-de-Leite-Cremoso.jpg");
		listaProduto.add(produto3);
		Produto produto4 = new Produto("Bolo de Fuba", categoria2, 5, 50, true);
		produto4.setType("https://comidinhasdochef.com/wp-content/uploads/2018/08/Bolo-de-Cenoura-Cozida.jpg");
		listaProduto.add(produto4);
		Produto produto5 = new Produto("Torta de Limão", categoria1, 11, 110, false);
		produto5.setType("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVDMckV5R1RwwVzXDzbweSzCMWZ_nntZsTAw&usqp=CAU");
		listaProduto.add(produto5);
		Produto produto6 = new Produto("Doce de Jiló", categoria3, 6, 60, false);
		produto6.setType("https://comidinhasdochef.com/wp-content/uploads/2020/03/Doce-de-Leite-Cremoso.jpg");
		listaProduto.add(produto6);

		produtoRepository.saveAll(listaProduto);

		List<Permissao> listaPermissao = new ArrayList<Permissao>();

		Permissao permissao1 = new Permissao("Funcionário");
		listaPermissao.add(permissao1);
		Permissao permissao2 = new Permissao("Cliente");
		listaPermissao.add(permissao2);

		permissaoRepository.saveAll(listaPermissao);

		List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

		Funcionario funcionario1 = new Funcionario("Gustavo Parro", "123.123.123-00", "gustavo@gmail.com", "123",
				permissao1);
		listaFuncionario.add(funcionario1);
		Funcionario funcionario2 = new Funcionario("Juju Pantera", "321.321.321-99", "juju@gmail.com", "321",
				permissao2);
		listaFuncionario.add(funcionario2);
		Funcionario funcionario3 = new Funcionario("Beatriz ...", "666.666.666.11", "bia@gmail.com", "666", permissao2);
		listaFuncionario.add(funcionario3);

		funcionarioRepository.saveAll(listaFuncionario);

		List<Cliente> listaCliente = new ArrayList<Cliente>();

		Cliente cliente0 = new Cliente("Prof. Danilo", "666.000.000.22", "daniloccuft@gmail.com", "(63) 98421-1107", 0);
		listaCliente.add(cliente0);
		Cliente cliente1 = new Cliente("Tales", "222.111.333-44", "taalesmelquiades@gmail.com", "(63) 98762-2132", 120);
		listaCliente.add(cliente1);
		Cliente cliente2 = new Cliente("Yuri", "333.212.121-12", "fidelidademail@gmail.com", "(63) 9992-9821", 230);
		listaCliente.add(cliente2);
		Cliente cliente3 = new Cliente("Gustavo", "000.000.000.22", "parrogustavo@gmail.com", "(63) 98438-9200", 100);
		listaCliente.add(cliente3);

		clienteRepository.saveAll(listaCliente);

		//apenas para preencher algo no banco
		byte[] img = "teste".getBytes(); 
		String urlImagem = "https://i.pinimg.com/originals/12/e4/ad/12e4adb1616aa2a5933c55fa9be72e59.jpg";
		Promocao promocao1 = new Promocao("teste", true, utils.compressZLib(img), urlImagem);

		promocaoRepository.save(promocao1);

	}

}
package br.com.unitins.fidelidade.service;

import java.util.ArrayList;
import java.util.Arrays;
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

	@Override
	public void run(String... args) throws Exception {
		List<Categoria> listaCategoria = new ArrayList<Categoria>();

		Categoria categoria1 = new Categoria("Tortas", true);
		listaCategoria.add(categoria1);
		Categoria categoria2 = new Categoria("Bolos", true);
		listaCategoria.add(categoria2);
		Categoria categoria3 = new Categoria("Doces", true);
		listaCategoria.add(categoria3);
		
		Categoria categoria4 = new Categoria("Veganos", true);
		listaCategoria.add(categoria4);

		categoriaRepository.saveAll(listaCategoria);

		Produto produto1 = new Produto("Bolo de cenoura", categoria2, 10, 100, true, "https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb");
		Produto produto2 = new Produto("Torta de Morango", categoria1, 12, 120, true, "https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb");
		Produto produto3 = new Produto("Doce de leite 200g", categoria3, 8, 80, true, "https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb");
		Produto produto4 = new Produto("Bolo de Fuba", categoria2, 5, 50, true, "https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb");
		Produto produto5 = new Produto("Torta de Limão", categoria1, 11, 110, false, "https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb");
		Produto produto6 = new Produto("Doce de Jiló", categoria3, 6, 60, false, "https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb");
		List<Produto> listaProduto = Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6);
		produtoRepository.saveAll(listaProduto);

		List<Permissao> listaPermissao = new ArrayList<Permissao>();

		Permissao permissao1 = new Permissao("Funcionário");
		listaPermissao.add(permissao1);
		Permissao permissao2 = new Permissao("Cliente");
		listaPermissao.add(permissao2);

		permissaoRepository.saveAll(listaPermissao);

		List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();

		Funcionario funcionario1 = new Funcionario("Francisco", "123.123.123-12", "gustavo@gmail.com", "123", permissao1);
		listaFuncionario.add(funcionario1);
		Funcionario funcionario2 = new Funcionario("Juju Pantera", "321.321.321-99", "juju@gmail.com", "321", permissao1);
		listaFuncionario.add(funcionario2);
		Funcionario funcionario3 = new Funcionario("Beatriz ...", "666.666.666.11", "bia@gmail.com", "666", permissao1);
		listaFuncionario.add(funcionario3);

		funcionarioRepository.saveAll(listaFuncionario);

		List<Cliente> listaCliente = new ArrayList<Cliente>();

		Cliente cliente0 = new Cliente("Prof. Danilo", "666.000.000.22", "daniloccuft@gmail.com", "(63) 98421-1107", 0, permissao2);
		listaCliente.add(cliente0);
		Cliente cliente1 = new Cliente("Tales", "222.111.333-44", "taalesmelquiades@gmail.com", "(63) 98762-2132", 120, permissao2);
		listaCliente.add(cliente1);
		Cliente cliente2 = new Cliente("Yuri", "333.212.121-12", "fidelidademail@gmail.com", "(63) 9992-9821", 230, permissao2);
		listaCliente.add(cliente2);
		Cliente cliente3 = new Cliente("Gustavo", "000.000.000.22", "parrogustavo@gmail.com", "(63) 98438-9200", 100, permissao2);
		listaCliente.add(cliente3);

		clienteRepository.saveAll(listaCliente);

		Promocao promocao1 = new Promocao("Bolos por metade do preço!", true, "\"https://firebasestorage.googleapis.com/v0/b/u-fidelidade.appspot.com/o/imagens-produtos%2F1606700556908?alt=media&token=cef3c3ee-94b0-4670-b65e-05b9937c0ecb\"");
		promocaoRepository.save(promocao1);

	}

}
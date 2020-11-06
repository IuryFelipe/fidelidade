package br.com.unitins.fidelidade.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unitins.fidelidade.controller.EmailController;
import br.com.unitins.fidelidade.model.Cliente;
import br.com.unitins.fidelidade.repository.ClienteRepository;


@RestController
@RequestMapping(value = "/fidelidade")
public class EmailResource {
    
	@Autowired
    ClienteRepository clienteRepository;
    EmailController emailController;

    @GetMapping("/email/send")
	public String sendEmail(@PathVariable(value = "emailText") String emailText) {
        List<Cliente> clientes = clienteRepository.findAll();
        int aux = 0;
        for (Cliente cliente : clientes) {
            String result = emailController.sendMail(cliente.getEmail(), emailText);
            if( result == "Email enviado com sucesso!"){
                aux++;
            } else {
                aux--;
            }
        }
		return "Operação realizada: " + aux + " email enviados é : " + (clientes.size() - aux) + " não enviados!";
	}


}

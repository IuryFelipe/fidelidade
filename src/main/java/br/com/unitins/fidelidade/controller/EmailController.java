package br.com.unitins.fidelidade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class EmailController {
    @Autowired private JavaMailSender mailSender;

    //método via url: http://localhost:8080/email-send
    @RequestMapping(path = "/email-send", method = RequestMethod.GET)
    public String sendMail(String emailTo, String emailText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(emailText);
        message.setTo(emailTo);
        message.setFrom("fidelidade@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
}

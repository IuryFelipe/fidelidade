package br.com.unitins.fidelidade.controller;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.multipart.MultipartFile;

public class EmailController {
    @Autowired
    private JavaMailSender mailSender;

    public String sendMail(String emailTo, String emailText, MultipartFile imagem) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
                mimeMessage.setFrom(new InternetAddress("fidelidademail@gmail.com"));
                mimeMessage.setSubject(emailText);

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setText("<html><body><img src='cid:imageEmail'></body></html>", true);
                helper.addInline("imageEmail", (DataSource) imagem);
            }
        };

        try {
            mailSender.send(preparator);
            return "Email enviado com sucesso!";
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
            return "Erro ao enviar email.";
        }
    }
}

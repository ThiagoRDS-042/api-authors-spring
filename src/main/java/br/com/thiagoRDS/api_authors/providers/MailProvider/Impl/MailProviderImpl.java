package br.com.thiagoRDS.api_authors.providers.MailProvider.Impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.thiagoRDS.api_authors.exceptions.InternalServerErrorException;
import br.com.thiagoRDS.api_authors.providers.MailProvider.MailProvider;
import br.com.thiagoRDS.api_authors.providers.MailProvider.dtos.SendMailDTO;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailProviderImpl extends MailProvider {
  private final JavaMailSender javaMailSender;

  public MailProviderImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  public void sendMail(SendMailDTO data) {
    MimeMessage message = javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setTo(data.to());
      helper.setFrom("noreply@api-authors.com.br");
      helper.setSubject(data.subject());
      helper.setText(data.text(), true);

      this.javaMailSender.send(message);
    } catch (Exception exception) {
      exception.printStackTrace();

      throw new InternalServerErrorException();
    }
  }
}

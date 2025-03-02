package br.com.authors.api_authors.modules.authors.usecases;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.authors.api_authors.modules.authors.entities.Author;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.authors.api_authors.providers.MailProvider;
import br.com.authors.api_authors.providers.dtos.SendMailDTO;

@Service
public class ForgotMailPassword {
  private final MailProvider mailProvider;
  private final AuthorsRepository authorsRepository;

  public ForgotMailPassword(MailProvider mailProvider, AuthorsRepository authorsRepository) {
    this.mailProvider = mailProvider;
    this.authorsRepository = authorsRepository;
  }

  public void execute(String email) {
    Author author = this.authorsRepository.findByEmail(email).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/authors/password/reset")
        .queryParam("code", author.getId())
        .toUriString();

    String text = String.format("Para criar uma nova senha acesse: <a href='%s'>Criar nova senha</a>", uri);

    SendMailDTO sendMailData = new SendMailDTO(email, "[API-AUTHORS] Mudança de senha",
        text);

    this.mailProvider.sendMail(sendMailData);
  }
}

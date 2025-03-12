package br.com.thiagoRDS.api_authors.modules.authors.usecases;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.thiagoRDS.api_authors.modules.authors.entities.Author;
import br.com.thiagoRDS.api_authors.modules.authors.entities.RecoveryToken;
import br.com.thiagoRDS.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.AuthorsRepository;
import br.com.thiagoRDS.api_authors.modules.authors.repositories.RecoveryTokensRepository;
import br.com.thiagoRDS.api_authors.providers.CurrentContextProvider;
import br.com.thiagoRDS.api_authors.providers.MailProvider;
import br.com.thiagoRDS.api_authors.providers.dtos.SendMailDTO;

@Service
public class ForgotMailPassword {
  private final MailProvider mailProvider;
  private final AuthorsRepository authorsRepository;
  private final CurrentContextProvider currentContextProvider;
  private final RecoveryTokensRepository recoveryTokensRepository;

  public ForgotMailPassword(MailProvider mailProvider, AuthorsRepository authorsRepository,
      CurrentContextProvider currentContextProvider, RecoveryTokensRepository recoveryTokensRepository) {
    this.mailProvider = mailProvider;
    this.authorsRepository = authorsRepository;
    this.currentContextProvider = currentContextProvider;
    this.recoveryTokensRepository = recoveryTokensRepository;
  }

  public void execute(String email) {
    Author author = this.authorsRepository.findByEmail(email).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    RecoveryToken token = this.recoveryTokensRepository.findByAuthorId(author.getId()).orElse(null);

    if (token == null) {
      token = new RecoveryToken(UUID.randomUUID(), author.getId());
    } else {
      token = new RecoveryToken(token.getId(), UUID.randomUUID(), author.getId());
    }

    this.recoveryTokensRepository.save(token);

    UriComponentsBuilder currentUri = this.currentContextProvider.getUri();

    String uri = currentUri.path("/authors/password/reset").queryParam("code", token.getCode()).toUriString();

    String text = String.format("Para criar uma nova senha acesse: <a href='%s'>Criar nova senha</a>", uri);

    SendMailDTO sendMailData = new SendMailDTO(email, "[API-AUTHORS] Mudança de senha",
        text);

    this.mailProvider.sendMail(sendMailData);
  }
}

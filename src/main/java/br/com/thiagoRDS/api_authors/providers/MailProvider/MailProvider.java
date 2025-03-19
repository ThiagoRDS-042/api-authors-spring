package br.com.thiagoRDS.api_authors.providers.MailProvider;

import br.com.thiagoRDS.api_authors.providers.dtos.SendMailDTO;

public abstract class MailProvider {
  public abstract void sendMail(SendMailDTO data);
}

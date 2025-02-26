package br.com.authors.api_authors.modules.authors.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.authors.api_authors.modules.authors.dtos.UpdatePasswordDTO;
import br.com.authors.api_authors.modules.authors.exceptions.AuthorNotFoundException;
import br.com.authors.api_authors.modules.authors.exceptions.InvalidCredentialsException;
import br.com.authors.api_authors.modules.authors.repositories.AuthorsRepository;

@Service
public class UpdatePassword {
  private final PasswordEncoder passwordEncoder;
  private final AuthorsRepository authorsRepository;

  public UpdatePassword(AuthorsRepository authorsRepository, PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.authorsRepository = authorsRepository;
  }

  public void execute(UpdatePasswordDTO data) {
    if (!data.newPassword().equals(data.confirmpassword())) {
      throw new InvalidCredentialsException();
    }

    var author = this.authorsRepository.findById(data.authorId()).orElseThrow(() -> {
      throw new AuthorNotFoundException();
    });

    var oldPasswordDoesMatches = this.passwordEncoder.matches(data.oldPassword(), author.getPassword());

    if (!oldPasswordDoesMatches) {
      throw new InvalidCredentialsException();
    }

    var passwordEncoded = this.passwordEncoder.encode(data.newPassword());

    author.setPassword(passwordEncoded);

    this.authorsRepository.save(author);
  }
}

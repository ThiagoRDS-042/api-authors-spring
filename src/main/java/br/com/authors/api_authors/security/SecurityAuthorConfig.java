package br.com.authors.api_authors.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.authors.api_authors.providers.JwtProvider;
import br.com.authors.api_authors.utils.SessionId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityAuthorConfig extends OncePerRequestFilter {
  @Autowired
  private JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    SecurityContextHolder.getContext().setAuthentication(null);

    var header = request.getHeader("Authorization");

    if (header != null) {
      var token = header.replace("Bearer ", "");

      var decodedToken = this.jwtProvider.validate(token);

      if (decodedToken == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return;
      }

      var authorId = decodedToken.getSubject();

      request.setAttribute(SessionId.ID, authorId);

      var auth = new UsernamePasswordAuthenticationToken(authorId, null, null);

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }
}

package br.com.thiagoRDS.api_authors.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.thiagoRDS.api_authors.providers.JwtProvider.JwtProvider;
import br.com.thiagoRDS.api_authors.utils.SessionId;
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

    String header = request.getHeader("Authorization");

    if (header != null) {
      String token = header.replace("Bearer ", "");

      DecodedJWT decodedToken = this.jwtProvider.validate(token);

      if (decodedToken == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return;
      }

      String authorId = decodedToken.getSubject();

      request.setAttribute(SessionId.ID, authorId);

      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authorId, null, null);

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }
}

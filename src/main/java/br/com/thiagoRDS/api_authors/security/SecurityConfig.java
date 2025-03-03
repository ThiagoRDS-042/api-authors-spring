package br.com.thiagoRDS.api_authors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private SecurityAuthorConfig securityAuthorConfig;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {

      auth.requestMatchers("/authors").permitAll()
          .requestMatchers("/authors/auth").permitAll()
          .requestMatchers("/authors/avatar/{avatar:.+}").permitAll()
          .requestMatchers("/authors/forgot-password").permitAll()
          .requestMatchers("/authors/password/reset").permitAll()
          .requestMatchers("/authors/{authorId}").permitAll() // TODO: ajustar, o {authorId} pode ser qualquer coisa,
                                                              // ele n entende
          .requestMatchers(HttpMethod.GET, "/posts").permitAll()
          .requestMatchers("/posts/{postId}").permitAll() // TODO: ajustar tambem
          .requestMatchers("/posts/{postId}/up").permitAll();

      auth.anyRequest().authenticated();
    }).addFilterBefore(this.securityAuthorConfig, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

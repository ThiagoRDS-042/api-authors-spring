package br.com.thiagoRDS.api_authors.security;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

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

  private static final String[] PERMITED_LIST = {
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/swagger-resouces/**",
      "/actuator/**",
  };

  private static final String UUID_FORMAT = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {

      auth.requestMatchers(PERMITED_LIST).permitAll()
          .requestMatchers("/authors").permitAll()
          .requestMatchers("/authors/auth").permitAll()
          .requestMatchers("/authors/avatar/{avatar:.+}").permitAll()
          .requestMatchers("/authors/forgot-password").permitAll()
          .requestMatchers("/authors/password/reset").permitAll()
          .requestMatchers(regexMatcher("/authors/" + UUID_FORMAT)).permitAll()
          .requestMatchers(HttpMethod.GET, "/posts").permitAll()
          .requestMatchers(regexMatcher("/posts/" + UUID_FORMAT)).permitAll()
          .requestMatchers(regexMatcher("/posts/" + UUID_FORMAT + "/up")).permitAll();

      auth.anyRequest().authenticated();
    }).addFilterBefore(this.securityAuthorConfig, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

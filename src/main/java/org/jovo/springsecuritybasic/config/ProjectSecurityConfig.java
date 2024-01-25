package org.jovo.springsecuritybasic.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig
{
  /**
   * Creates the default security filter chain for the HTTP security configuration.
   *
   * @param http the HttpSecurity object to configure
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("contact", "notices").permitAll()
            .requestMatchers("myAccount", "myBalance", "myCards").authenticated()
        );
    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());
    return http.build();
  }

  /**
   * Returns an instance of InMemoryUserDetailsManager configured with predefined user details.
   *
   * @return an instance of InMemoryUserDetailsManager
   */
  @Bean
  @SuppressWarnings("java:S1874")
  public InMemoryUserDetailsManager userDetailsService()
  {
    UserDetails admin = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("1234")
        .authorities("admin")
        .build();

    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user")
        .password("1234")
        .authorities("read")
        .build();

    return new InMemoryUserDetailsManager(admin, user);
  }
}

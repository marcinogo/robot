package edition.academy.seventh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.http.HttpServletRequest;

/**
 * Configuration class responsible for user authentication and authorization.
 *
 * @see edition.academy.seventh.security.model.User
 * @see JwtAuthTokenFilter
 * @see JwtAuthEntryPoint
 * @author Patryk Kucharski
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private UserDetailsServiceImpl userDetailsService;
  @Autowired private JwtAuthEntryPoint unauthorizedHandler;

  /**
   * Factory method creating {@link JwtAuthTokenFilter}.
   *
   * @return new instance of {@link JwtAuthTokenFilter}.
   */
  @Bean
  public JwtAuthTokenFilter authenticationJwtTokenFilter() {
    return new JwtAuthTokenFilter();
  }

  /**
   * Factory method creating {@link PasswordEncoder}.
   *
   * @return new instance of {@link BCryptPasswordEncoder}.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /** {@inheritDoc} */
  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  /** {@inheritDoc} */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/auth/sign_up")
        .permitAll()
        .antMatchers("/auth/sign_in")
        .permitAll()
        .antMatchers("/start")
        .permitAll()
        .antMatchers("/books")
        .permitAll()
        .antMatchers("/books/pagination")
        .permitAll()
        .antMatchers("/books/pagination/next")
        .permitAll()
        .antMatchers("/books/pagination/previous")
        .permitAll()
        .antMatchers("/books/pagination/size")
        .permitAll()
        .antMatchers("/books/pagination/filter")
        .permitAll()
        .antMatchers("/bookUrl/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(
        authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  /** {@inheritDoc} */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    StrictHttpFirewall firewall = new Foo();
    return firewall;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
  }
}

class Foo extends StrictHttpFirewall {
  @Override
  public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
    return new FirewalledRequest(request) {
      @Override
      public void reset() {
      }
    };
  }
}
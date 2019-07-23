package edition.academy.seventh.security.jwt;

import edition.academy.seventh.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Guarantees that user will be authenticated only once per request.
 *
 * @author Patryk Kucharski
 * @see JwtProvider
 * @see org.springframework.web.filter.OncePerRequestFilter
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
  private JwtProvider tokenProvider;
  private UserDetailsServiceImpl userDetailsService;

  // TODO: 23.07.19 czy potrzebne?

  //  /**
  //   * Creates bean for {@link org.springframework.context.ApplicationContext}.
  //   *
  //   * @param tokenProvider with necessary API to check given token.
  //   * @param userDetailsService necessary to load user from database.
  //   */
  //
  //  @Autowired
  //  public JwtAuthTokenFilter(JwtProvider tokenProvider, UserDetailsServiceImpl
  // userDetailsService) {
  //    this.tokenProvider = tokenProvider;
  //    this.userDetailsService = userDetailsService;
  //  }

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwt(request);
      if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
        String username = tokenProvider.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Can NOT set user authentication -> Message: " + e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private String getJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.replace("Bearer ", "");
    }
    return null;
  }

  @Autowired
  public void setTokenProvider(JwtProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }
  @Autowired
  public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
}

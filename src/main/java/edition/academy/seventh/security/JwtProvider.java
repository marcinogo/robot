package edition.academy.seventh.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Provides API to generate JSON web tokens.
 *
 * @author Patryk Kucharski
 */
@Component
class JwtProvider {
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${robot.jwtSecret}")
  private String jwtSecret;

  @Value("${robot.jwtExpiration}")
  private int jwtExpiration;

  /**
   * Generates JSON web token for specified user.
   *
   * @param authentication with all necessary data about user to generate token
   * @return JSON with {@link UserPrinciple#getUsername()}, creation date, expiration date, and
   *     {@link JwtProvider#jwtSecret}.
   */
  String generateJwtToken(Authentication authentication) {

    UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT token -> Message: " + e.getMessage());
    } catch (ExpiredJwtException e) {
      LOGGER.error("Expired JWT token -> Message: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
      LOGGER.error("Unsupported JWT token -> Message: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      LOGGER.error("JWT claims string is empty -> Message: " + e.getMessage());
    }
    return false;
  }

  String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }
}

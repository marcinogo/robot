package edition.academy.seventh.security.jwt;

import edition.academy.seventh.security.service.UserPrinciple;
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
public class JwtProvider {
  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

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
  public String generateJwtToken(Authentication authentication) {

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
      logger.error("Invalid JWT token -> Message: " + e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token -> Message: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token -> Message: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty -> Message: " + e.getMessage());
    }

    return false;
  }

  String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }
}

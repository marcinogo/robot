package edition.academy.seventh.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Simple wrapper to message sent back to client in response
 * for requests.
 *
 * @author Patryk Kucharski
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

  private String message;
}

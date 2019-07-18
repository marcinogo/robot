package edition.academy.seventh.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ola Podorska
 * @author Marcin Ogorzalek
 */
@Component
class BookstoreConnector {

  String getJsonResponse(String url) {
    RestTemplate template = new RestTemplate();
    return template.getForObject(url, String.class);
  }
}

package edition.academy.seventh.bookstoreconnectionservice;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ola Podorska
 */
@Component
class BookstoreConnector {

    String getJsonResponse(String url) {
        RestTemplate template = new RestTemplate();
        return template.getForObject(url, String.class);
    }
}

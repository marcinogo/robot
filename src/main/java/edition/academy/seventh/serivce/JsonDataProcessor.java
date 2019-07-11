package edition.academy.seventh.serivce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


/**
 * @author Marcin Ogorzałek
 * @author Ola Podorska
 */
@Component
class JsonDataProcessor {

  JsonNode mapJsonString(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = null;
    try {
      jsonNode = objectMapper.readValue(json, JsonNode.class);
    } catch (IOException exception) {
      System.err.println(exception.getMessage());
    }
    return jsonNode;
  }

  List<String> convertJsonToBookIsbn(JsonNode jsonNode) {
    List<String> isbns = new ArrayList<>();
    for (JsonNode node : jsonNode.get("books")) {
      isbns.add(node.get("isbn13").asText());
    }
    return isbns;
  }
}

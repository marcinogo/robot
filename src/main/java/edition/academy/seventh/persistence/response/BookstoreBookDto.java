package edition.academy.seventh.persistence.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/** @author Agnieszka Trzewik */
@Data
@AllArgsConstructor
public class BookstoreBookDto {
  @JsonProperty(value = "title")
  private String title;

  @JsonProperty(value = "subtitle")
  private String subtitle;

  @JsonProperty(value = "authors")
  private String authors;

  @JsonProperty(value = "image")
  private String imageLink;

  @JsonProperty(value = "url")
  private String href;

  @JsonProperty(value = "bookstore")
  private String bookstore;

  @JsonProperty(value = "priceHistory")
  private List<PriceAtTheMomentDto> priceAtTheMomentDtos;
}

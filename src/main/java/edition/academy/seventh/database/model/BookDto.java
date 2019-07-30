package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kamil Rojek
 * @author Bartosz Kupajski
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

  private String title;
  private String subtitle;
  private String authors;
  private String currency;

  @JsonProperty(value = "price")
  private BigDecimal retailPrice;

  private BigDecimal promotionalPrice;

  @JsonProperty(value = "image")
  private String imageLink;

  @JsonProperty(value = "url")
  private String href;

  private String bookstore;
}

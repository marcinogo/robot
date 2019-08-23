package edition.academy.seventh.persistence.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** @author Agnieszka Trzewik */
@Data
@AllArgsConstructor
public class PriceAtTheMomentDto {

  @JsonProperty(value = "retailPrice")
  private BigDecimal retailPrice;

  @JsonProperty(value = "promotionalPrice")
  private BigDecimal promotionalPrice;

  @JsonProperty(value = "currency")
  private String currency;

  @JsonProperty(value = "date")
  private LocalDateTime date;
}

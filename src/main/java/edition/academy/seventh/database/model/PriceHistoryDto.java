package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/** @author Agnieszka Trzewik */
@Data
@AllArgsConstructor
public class PriceHistoryDto {

  @JsonProperty(value = "retailPrice")
  private BigDecimal retailPrice;

  @JsonProperty(value = "promotionalPrice")
  private BigDecimal promotionalPrice;

  @JsonProperty(value = "currency")
  private String currency;

  @JsonProperty(value = "date")
  private LocalDateTime date;
}

package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/** @author Agnieszka Trzewik */
public class PriceHistoryDto {

  @JsonProperty(value = "retailPrice")
  private String retailPrice;

  @JsonProperty(value = "promotionalPrice")
  private String promotionalPrice;

  @JsonProperty(value = "date")
  private LocalDateTime date;

  public PriceHistoryDto(String retailPrice, String promotionalPrice, LocalDateTime date) {
    this.retailPrice = retailPrice;
    this.promotionalPrice = promotionalPrice;
    this.date = date;
  }
}

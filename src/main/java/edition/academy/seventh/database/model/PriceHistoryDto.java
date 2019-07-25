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

  public String getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(String retailPrice) {
    this.retailPrice = retailPrice;
  }

  public String getPromotionalPrice() {
    return promotionalPrice;
  }

  public void setPromotionalPrice(String promotionalPrice) {
    this.promotionalPrice = promotionalPrice;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }
}

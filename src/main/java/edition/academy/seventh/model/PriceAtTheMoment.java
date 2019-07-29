package edition.academy.seventh.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** @author Agnieszka Trzewik */
@Entity(name = "price_at_the_moment")
@Data
@NoArgsConstructor
public class PriceAtTheMoment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  @FieldNameConstants.Exclude
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "bookstoreBook_id")
  private BookstoreBook bookstoreBook;

  @Column(name = "retail_price")
  private BigDecimal retailPrice;

  @Column(name = "promotional_price")
  private BigDecimal promotionalPrice;

  @Column(name = "currency")
  private String currency;

  @Column(name = "date")
  private LocalDateTime date;

  public PriceAtTheMoment(
      BookstoreBook bookstoreBook,
      BigDecimal retailPrice,
      BigDecimal promotionalPrice,
      String currency,
      LocalDateTime date) {
    this.bookstoreBook = bookstoreBook;
    this.retailPrice = retailPrice;
    this.promotionalPrice = promotionalPrice;
    this.currency = currency;
    this.date = date;
  }
}

package edition.academy.seventh.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/** @author Agnieszka Trzewik */
@Entity(name = "price_history")
public class PriceHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne()
  @JoinColumns({
    @JoinColumn(name = "author"),
    @JoinColumn(name = "title"),
    @JoinColumn(name = "bookstore")
  })
  private BookstoreBook bookstoreBook;

  @Column(name = "retail_price")
  private String retailPrice;

  @Column(name = "promotional_price")
  private String promotionalPrice;

  @Column(name = "date")
  private LocalDateTime date;

  public PriceHistory() {}

  public PriceHistory(
      BookstoreBook bookstoreBook,
      String retailPrice,
      String promotionalPrice,
      LocalDateTime date) {
    this.bookstoreBook = bookstoreBook;
    this.retailPrice = retailPrice;
    this.promotionalPrice = promotionalPrice;
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BookstoreBook getBookstoreBook() {
    return bookstoreBook;
  }

  public void setBookstoreBook(BookstoreBook bookstoreBook) {
    this.bookstoreBook = bookstoreBook;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PriceHistory that = (PriceHistory) o;
    return Objects.equals(id, that.id)
        && Objects.equals(bookstoreBook, that.bookstoreBook)
        && Objects.equals(retailPrice, that.retailPrice)
        && Objects.equals(promotionalPrice, that.promotionalPrice)
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookstoreBook, retailPrice, promotionalPrice, date);
  }
}

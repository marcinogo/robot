package edition.academy.seventh.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class BookstoreBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "bookstore_id")
  private Bookstore bookstore;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumns({@JoinColumn(name = "title"), @JoinColumn(name = "author")})
  private Book book;

  @Column(name = "retailPrice")
  private String retailPrice;

  @Column(name = "promotionalPrice")
  private String promotionalPrice;

  @Column(name = "date")
  private LocalDate date;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "hrefAndImage_id")
  private HrefAndImage hrefAndImage;

  public BookstoreBook() {}

  public BookstoreBook(
      Bookstore bookstore,
      Book book,
      String retailPrice,
      String promotionalPrice,
      LocalDate date,
      HrefAndImage hrefAndImage) {
    this.bookstore = bookstore;
    this.book = book;
    this.retailPrice = retailPrice;
    this.promotionalPrice = promotionalPrice;
    this.date = date;
    this.hrefAndImage = hrefAndImage;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Bookstore getBookstore() {
    return bookstore;
  }

  public void setBookstore(Bookstore bookstore) {
    this.bookstore = bookstore;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public HrefAndImage getHrefAndImage() {
    return hrefAndImage;
  }

  public void setHrefAndImage(HrefAndImage hrefAndImage) {
    this.hrefAndImage = hrefAndImage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookstoreBook that = (BookstoreBook) o;
    return id == that.id
        && Objects.equals(bookstore, that.bookstore)
        && Objects.equals(book, that.book)
        && Objects.equals(retailPrice, that.retailPrice)
        && Objects.equals(promotionalPrice, that.promotionalPrice)
        && Objects.equals(date, that.date)
        && Objects.equals(hrefAndImage, that.hrefAndImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookstore, book, retailPrice, promotionalPrice, date, hrefAndImage);
  }
}

package edition.academy.seventh.model;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity(name = "bookstore_book")
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

  @Column(name = "retail_price")
  private String retailPrice;

  @Column(name = "promotional_price")
  private String promotionalPrice;

  @Column(name = "date")
  private LocalDate date;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "url_resources_id")
  private UrlResources urlResources;

  public BookstoreBook() {
  }

  public BookstoreBook(
      Book book,
      String retailPrice,
      String promotionalPrice,
      LocalDate date,
      UrlResources hrefAndImage,
      Bookstore bookstore) {
    this.bookstore = bookstore;
    this.book = book;
    this.retailPrice = retailPrice;
    this.promotionalPrice = promotionalPrice;
    this.date = date;
    this.urlResources = hrefAndImage;
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

  public UrlResources getUrlResources() {
    return urlResources;
  }

  public void setUrlResources(UrlResources urlResources) {
    this.urlResources = urlResources;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookstoreBook that = (BookstoreBook) o;
    return id == that.id
        && Objects.equals(bookstore, that.bookstore)
        && Objects.equals(book, that.book)
        && Objects.equals(retailPrice, that.retailPrice)
        && Objects.equals(promotionalPrice, that.promotionalPrice)
        && Objects.equals(date, that.date)
        && Objects.equals(urlResources, that.urlResources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookstore, book, retailPrice, promotionalPrice, date, urlResources);
  }
}

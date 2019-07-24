package edition.academy.seventh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookstore_book")
public class BookstoreBook {

  @EmbeddedId
  @Column(name = "bookstore_book_id")
  private BookstoreBookId bookstoreBookId;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bookstoreBook")
  private List<PriceHistory> priceHistories = new ArrayList<>();

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "url_resources_id")
  private UrlResources urlResources;

  public BookstoreBook() {}

  public BookstoreBook(BookstoreBookId bookstoreBookId, UrlResources urlResources) {
    this.bookstoreBookId = bookstoreBookId;
    this.urlResources = urlResources;
  }

  public BookstoreBookId getBookstoreBookId() {
    return bookstoreBookId;
  }

  public void setBookstoreBookId(BookstoreBookId bookstoreBookId) {
    this.bookstoreBookId = bookstoreBookId;
  }

  public UrlResources getUrlResources() {
    return urlResources;
  }

  public void setUrlResources(UrlResources urlResources) {
    this.urlResources = urlResources;
  }

  public List<PriceHistory> getPriceHistories() {
    return priceHistories;
  }

  public void setPriceHistories(List<PriceHistory> priceHistories) {
    this.priceHistories = priceHistories;
  }

  public PriceHistory getLastElementOfPriceHistories() {
    int lastIndexOfPriceHistories = this.priceHistories.size() - 1;
    return this.priceHistories.get(lastIndexOfPriceHistories);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookstoreBook that = (BookstoreBook) o;
    return Objects.equals(bookstoreBookId, that.bookstoreBookId)
        && Objects.equals(urlResources, that.urlResources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookstoreBookId, urlResources);
  }
}

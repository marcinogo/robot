package edition.academy.seventh.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity(name = "bookstore_book")
public class BookstoreBook {

  @Id
  @Column(name = "hyper_link", length = 300)
  private String hyperLink;

  @Column(name = "image_link")
  private String imageLink;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "bookstore_id")
  private Bookstore bookstore;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumns({@JoinColumn(name = "author"), @JoinColumn(name = "title")})
  private Book book;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bookstoreBook")
  private List<PriceHistory> priceHistories = new ArrayList<>();

  public BookstoreBook() {}

  public BookstoreBook(String hyperLink, String imageLink, Bookstore bookstore, Book book) {
    this.hyperLink = hyperLink;
    this.imageLink = imageLink;
    this.bookstore = bookstore;
    this.book = book;
  }

  public String getHyperLink() {
    return hyperLink;
  }

  public void setHyperLink(String hyperLink) {
    this.hyperLink = hyperLink;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
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
    return Objects.equals(hyperLink, that.hyperLink)
        && Objects.equals(imageLink, that.imageLink)
        && Objects.equals(bookstore, that.bookstore)
        && Objects.equals(book, that.book)
        && Objects.equals(priceHistories, that.priceHistories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hyperLink, imageLink, bookstore, book, priceHistories);
  }
}

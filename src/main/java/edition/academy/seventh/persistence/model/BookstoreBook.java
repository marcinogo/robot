package edition.academy.seventh.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "bookstore_book")
@Getter
@Setter
@NoArgsConstructor
public class BookstoreBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "hyperlink", length = 300, nullable = false)
  private String hyperlink;

  @Column(name = "image_link")
  private String imageLink;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinColumn(name = "bookstore_id")
  private Bookstore bookstore;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinColumns({@JoinColumn(name = "author"), @JoinColumn(name = "title")})
  private Book book;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bookstoreBook")
  private List<PriceAtTheMoment> priceHistories = new ArrayList<>();

  public BookstoreBook(String hyperlink, String imageLink, Bookstore bookstore, Book book) {
    this.hyperlink = hyperlink;
    this.imageLink = imageLink;
    this.bookstore = bookstore;
    this.book = book;
  }

  public PriceAtTheMoment getLastElementOfPriceHistories() {
    int lastIndexOfPriceHistories = this.priceHistories.size() - 1;
    return this.priceHistories.get(lastIndexOfPriceHistories);
  }

  @Override
  public String toString() {
    return "BookstoreBook{"
        + "id="
        + id
        + ", hyperlink='"
        + hyperlink
        + '\''
        + ", imageLink='"
        + imageLink
        + '\''
        + ", bookstore="
        + bookstore
        + ", book="
        + book
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookstoreBook that = (BookstoreBook) o;
    return Objects.equals(id, that.id)
        && Objects.equals(hyperlink, that.hyperlink)
        && Objects.equals(imageLink, that.imageLink)
        && Objects.equals(bookstore, that.bookstore)
        && Objects.equals(book, that.book);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, hyperlink, imageLink, bookstore, book);
  }
}

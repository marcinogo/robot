package edition.academy.seventh.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "bookstore_book")
@Data
@NoArgsConstructor
public class BookstoreBook {

  @Id
  @Column(name = "hyperlink", length = 300, nullable = false)
  private String hyperlink;

  @Column(name = "image_link")
  private String imageLink;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "bookstore_id")
  private Bookstore bookstore;

  @ManyToOne(cascade = CascadeType.PERSIST)
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
}

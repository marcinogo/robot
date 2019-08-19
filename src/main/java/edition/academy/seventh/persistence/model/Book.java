package edition.academy.seventh.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "book")
@Data
@NoArgsConstructor
public class Book {

  @EmbeddedId
  @Column(name = "book_id")
  private BookId bookId;

  @Column(name = "subtitle")
  private String subtitle;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public Book(BookId bookId, String subtitle) {
    this.bookId = bookId;
    this.subtitle = subtitle;
  }
}

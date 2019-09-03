package edition.academy.seventh.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "book")
@Getter
@Setter
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

  @Override
  public String toString() {
    return "Book{" + "bookId=" + bookId + ", subtitle='" + subtitle + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(bookId, book.bookId) && Objects.equals(subtitle, book.subtitle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookId, subtitle);
  }
}

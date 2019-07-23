package edition.academy.seventh.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/** @author Agnieszka Trzewik */
@Embeddable
public class BookstoreBookId implements Serializable {

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "bookstore_id")
  private Bookstore bookstore;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumns({@JoinColumn(name = "author"), @JoinColumn(name = "title")})
  private Book book;

  public BookstoreBookId() {}

  public BookstoreBookId(Bookstore bookstore, Book book) {
    this.bookstore = bookstore;
    this.book = book;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookstoreBookId that = (BookstoreBookId) o;
    return Objects.equals(bookstore, that.bookstore) && Objects.equals(book, that.book);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookstore, book);
  }
}

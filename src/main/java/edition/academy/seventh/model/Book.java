package edition.academy.seventh.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity(name = "book")
public class Book {

  @EmbeddedId
  @Column(name = "book_id")
  private BookId bookId;

  @Column(name = "subtitle")
  private String subtitle;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public Book() {}

  public Book(String subtitle, BookId bookId) {
    this.subtitle = subtitle;
    this.bookId = bookId;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public List<BookstoreBook> getBookstoreBooks() {
    return bookstoreBooks;
  }

  public void setBookstoreBooks(List<BookstoreBook> bookstoreBooks) {
    this.bookstoreBooks = bookstoreBooks;
  }

  public BookId getBookId() {
    return bookId;
  }

  public void setBookId(BookId bookId) {
    this.bookId = bookId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(bookId, book.bookId)
        && Objects.equals(subtitle, book.subtitle)
        && Objects.equals(bookstoreBooks, book.bookstoreBooks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookId, subtitle, bookstoreBooks);
  }
}
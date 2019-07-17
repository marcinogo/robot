package edition.academy.seventh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

  @EmbeddedId
  @Column(name = "book_id")
  private BookId bookId;

  @Column(name = "subtitle")
  private String subtitle;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

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
}

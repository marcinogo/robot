package edition.academy.seventh.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity(name = "bookstore")
public class Bookstore {

  @Id
  @Column(name = "name")
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookstore")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public Bookstore() {}

  public Bookstore(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<BookstoreBook> getBookstoreBooks() {
    return bookstoreBooks;
  }

  public void setBookstoreBooks(List<BookstoreBook> bookstoreBooks) {
    this.bookstoreBooks = bookstoreBooks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Bookstore bookstore = (Bookstore) o;
    return Objects.equals(name, bookstore.name)
        && Objects.equals(bookstoreBooks, bookstore.bookstoreBooks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, bookstoreBooks);
  }
}

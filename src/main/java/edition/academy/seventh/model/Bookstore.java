package edition.academy.seventh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bookstore {

  @Id
  @Column(name = "name")
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookstore")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

    public Bookstore() {
    }

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
}

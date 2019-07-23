package edition.academy.seventh.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookId implements Serializable {

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  public BookId() {}

  public BookId(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookId bookId = (BookId) o;
    return Objects.equals(title, bookId.title) && Objects.equals(author, bookId.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, author);
  }
}

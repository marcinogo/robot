package edition.academy.seventh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class HrefAndImage {

  @Id
  @Column(name = "hyperLink")
  private String hyperLink;

  @Column(name = "image")
  private String image;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookstore")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public HrefAndImage() {}

  public HrefAndImage(String hyperLink, String image) {
    this.hyperLink = hyperLink;
    this.image = image;
  }

  public String getHyperLink() {
    return hyperLink;
  }

  public void setHyperLink(String hyperLink) {
    this.hyperLink = hyperLink;
  }

  public List<BookstoreBook> getBookstoreBooks() {
    return bookstoreBooks;
  }

  public void setBookstoreBooks(List<BookstoreBook> bookstoreBooks) {
    this.bookstoreBooks = bookstoreBooks;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HrefAndImage that = (HrefAndImage) o;
    return Objects.equals(hyperLink, that.hyperLink)
        && Objects.equals(image, that.image)
        && Objects.equals(bookstoreBooks, that.bookstoreBooks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hyperLink, image, bookstoreBooks);
  }
}

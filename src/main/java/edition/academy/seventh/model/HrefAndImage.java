package edition.academy.seventh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}

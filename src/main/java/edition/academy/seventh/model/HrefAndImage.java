package edition.academy.seventh.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "href_and_image")
public class HrefAndImage {

  @Id
  @Column(name = "hyper_link", length = 300)
  private String hyperLink;

  @Column(name = "image_link")
  private String imageLink;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookstore")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public HrefAndImage() {
  }

  public HrefAndImage(String hyperLink, String image) {
    this.hyperLink = hyperLink;
    this.imageLink = image;
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

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HrefAndImage that = (HrefAndImage) o;
    return Objects.equals(hyperLink, that.hyperLink)
        && Objects.equals(imageLink, that.imageLink)
        && Objects.equals(bookstoreBooks, that.bookstoreBooks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hyperLink, imageLink, bookstoreBooks);
  }
}

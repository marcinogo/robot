package edition.academy.seventh.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "url_resources")
public class UrlResources {

  @Id
  @Column(name = "hyper_link", length = 300)
  private String hyperLink;

  @Column(name = "image_link")
  private String imageLink;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "urlResources")
  private List<BookstoreBook> bookstoreBooks = new ArrayList<>();

  public UrlResources() {}

  public UrlResources(String hyperLink, String image) {
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
    UrlResources that = (UrlResources) o;
    return Objects.equals(hyperLink, that.hyperLink)
        && Objects.equals(imageLink, that.imageLink)
        && Objects.equals(bookstoreBooks, that.bookstoreBooks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hyperLink, imageLink, bookstoreBooks);
  }
}

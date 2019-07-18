package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Kamil Rojek
 * @author Bartosz Kupajski
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String subtitle;
  private String authors;
  private String price;
  private String promotion;
  private String img;
  @Column(length = 300)
  private String href;
  private String bookstore;

  public Book(String title, String subtitle, String authors, String price, String promotion,
      String img, String href, String bookstore) {
    this.title = title;
    this.subtitle = subtitle;
    this.authors = authors;
    this.price = price;
    this.promotion = promotion;
    this.img = img;
    this.href = href;
    this.bookstore = bookstore;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getPromotion() {
    return promotion;
  }

  public void setPromotion(String promotion) {
    this.promotion = promotion;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getBookstore() {
    return bookstore;
  }

  public void setBookstore(String bookstore) {
    this.bookstore = bookstore;
  }

  @Override
  public String toString() {
    return "Book{"
        + "title='"
        + title
        + '\''
        + ", subtitle='"
        + subtitle
        + '\''
        + ", authors='"
        + authors
        + '\''
        + ", price='"
        + price
        + '\''
        + '}';
  }
}

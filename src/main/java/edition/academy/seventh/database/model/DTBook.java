package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author Kamil Rojek
 * @author Bartosz Kupajski
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DTBook {

  private String title;
  private String subtitle;
  private String authors;
  private String price;
  private String promotion;

  @JsonProperty(value = "image")
  private String img;

  @JsonProperty(value = "url")
  private String href;

  private String bookstore;

  public DTBook() {}

  public DTBook(
      String title,
      String subtitle,
      String authors,
      String price,
      String promotion,
      String img,
      String href,
      String bookstore) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DTBook book = (DTBook) o;
    return Objects.equals(title, book.title)
        && Objects.equals(subtitle, book.subtitle)
        && Objects.equals(authors, book.authors)
        && Objects.equals(price, book.price)
        && Objects.equals(promotion, book.promotion)
        && Objects.equals(img, book.img)
        && Objects.equals(href, book.href)
        && Objects.equals(bookstore, book.bookstore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, subtitle, authors, price, promotion, img, href, bookstore);
  }

  @Override
  public String toString() {
    return "DTBook{"
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

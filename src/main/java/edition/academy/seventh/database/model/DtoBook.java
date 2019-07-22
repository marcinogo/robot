package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author Kamil Rojek
 * @author Bartosz Kupajski
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoBook {

  private String title;
  private String subtitle;
  private String authors;

  @JsonProperty(value = "price")
  private String retailPrice;

  private String promotionalPrice;

  @JsonProperty(value = "image")
  private String imageLink;

  @JsonProperty(value = "url")
  private String href;

  private String bookstore;

  public DtoBook() {}

  public DtoBook(
      String title,
      String subtitle,
      String authors,
      String retailPrice,
      String promotionalPrice,
      String imageLink,
      String href,
      String bookstore) {
    this.title = title;
    this.subtitle = subtitle;
    this.authors = authors;
    this.retailPrice = retailPrice;
    this.promotionalPrice = promotionalPrice;
    this.imageLink = imageLink;
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

  public String getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(String retailPrice) {
    this.retailPrice = retailPrice;
  }

  public String getPromotionalPrice() {
    return promotionalPrice;
  }

  public void setPromotionalPrice(String promotionalPrice) {
    this.promotionalPrice = promotionalPrice;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
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
    DtoBook book = (DtoBook) o;
    return Objects.equals(title, book.title)
        && Objects.equals(subtitle, book.subtitle)
        && Objects.equals(authors, book.authors)
        && Objects.equals(retailPrice, book.retailPrice)
        && Objects.equals(promotionalPrice, book.promotionalPrice)
        && Objects.equals(imageLink, book.imageLink)
        && Objects.equals(href, book.href)
        && Objects.equals(bookstore, book.bookstore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        title, subtitle, authors, retailPrice, promotionalPrice, imageLink, href, bookstore);
  }

  @Override
  public String toString() {
    return "DtoBook{"
        + "title='"
        + title
        + '\''
        + ", subtitle='"
        + subtitle
        + '\''
        + ", authors='"
        + authors
        + '\''
        + ", retailPrice='"
        + retailPrice
        + '\''
        + '}';
  }
}

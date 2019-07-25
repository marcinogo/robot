package edition.academy.seventh.database.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** @author Agnieszka Trzewik */
public class BookstoreBookDto {
  @JsonProperty(value = "title")
  private String title;

  @JsonProperty(value = "subtitle")
  private String subtitle;

  @JsonProperty(value = "authors")
  private String authors;

  @JsonProperty(value = "image")
  private String imageLink;

  @JsonProperty(value = "url")
  private String href;

  @JsonProperty(value = "bookstore")
  private String bookstore;

  @JsonProperty(value = "priceHistory")
  private List<PriceHistoryDto> priceHistoryDtos;

  public BookstoreBookDto(
      String title,
      String subtitle,
      String authors,
      String imageLink,
      String href,
      String bookstore,
      List<PriceHistoryDto> priceHistoryDtos) {
    this.title = title;
    this.subtitle = subtitle;
    this.authors = authors;
    this.imageLink = imageLink;
    this.href = href;
    this.bookstore = bookstore;
    this.priceHistoryDtos = priceHistoryDtos;
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

  public List<PriceHistoryDto> getPriceHistoryDtos() {
    return priceHistoryDtos;
  }

  public void setPriceHistoryDtos(
      List<PriceHistoryDto> priceHistoryDtos) {
    this.priceHistoryDtos = priceHistoryDtos;
  }
}

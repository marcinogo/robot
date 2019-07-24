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
}

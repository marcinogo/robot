package edition.academy.seventh.scrapping;

import edition.academy.seventh.database.model.BookDto;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.List;

/**
 * Scraps data from pwn bookstore website in sales section using Jsoup library.
 *
 * <p>{@link AbstractScrapper} {@link PromotionProvider}
 *
 * @author Kacper Staszek
 */
class PwnScrapper extends AbstractScrapper {

  private final String bookstoreName;

  PwnScrapper(String startOfUrl, String endOfUrl, String documentClassName, String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }

  /**
   * Scraps 96 positions for each iteration.
   *
   * @return list of books after all threads finish their jobs.
   */
  @Override
  public List<BookDto> getPromotions() {

    for (int i = 1; i <= 2; i++) {
      service.submit(createScrappingTask(i));
      logger.info("Submitting scrapping task for page: " + startOfUrl + i + endOfUrl);
    }

    phaser.arriveAndAwaitAdvance();

    return listOfBooks;
  }

  @Override
  void mappingToBookList(Elements elementsByClass) {
    final String startOfHrefUrl = "https://ksiegarnia.pwn.pl";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("emp-info-title").text();
              String author = element.getElementsByClass("emp-info-authors").text();
              author = deleteAuthorTag(author);
              String retailPriceAsString =
                  prepareValidPrice(element.getElementsByClass("emp-base-price").text());
              BigDecimal retailPrice = new BigDecimal(retailPriceAsString);
              String promotionalPriceAsString =
                  prepareValidPrice(element.getElementsByClass("emp-sale-price-value").text());
              BigDecimal promotionalPrice = new BigDecimal(promotionalPriceAsString);
              String imageLink = element.getElementsByTag("img").attr("src");
              String href = element.getElementsByClass("titleLink").attr("href");
              href = startOfHrefUrl + href;
              return new BookDto(
                  title,
                  "",
                  author,
                  "zł",
                  retailPrice,
                  promotionalPrice,
                  imageLink,
                  href,
                  bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arriveAndDeregister();
  }

  private String deleteAuthorTag(String author) {
    return author.replace("Autor: ", "");
  }
}

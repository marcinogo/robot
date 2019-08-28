package edition.academy.seventh.scrapping;

import edition.academy.seventh.persistence.response.BookDto;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.List;

/**
 * Scraps data from bookoff bookstore website in sales section using Jsoup library.
 *
 * <p>{@link AbstractScrapper} {@link PromotionProvider}
 *
 * @author Bartosz Kupajski
 */
class BookoffScrapper extends AbstractScrapper {

  private final String bookstoreName;

  BookoffScrapper(
      String startOfUrl, String endOfUrl, String documentClassName, String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }

  /**
   * Scraps 30 positions for each iteration.
   *
   * @return list of books after all threads finish their jobs.
   */
  @Override
  public List<BookDto> getPromotions() {
    for (int i = 0; i <= 1; i++) {
      service.submit(createScrappingTask(i));
      logger.info("Submitting scrapping task for page: " + startOfUrl + i + endOfUrl);
    }

    phaser.arriveAndAwaitAdvance();

    return listOfBooks;
  }

  @Override
  void mappingToBookList(Elements elementsByClass) {
    final String startOfHrefUrl = "https://www.bookoff.pl";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("product-name").text();
              String author = element.getElementsByClass("product-producer").text();
              String retailPriceAsString =
                  prepareValidPrice(element.getElementsByClass("max-price").text());
              BigDecimal retailPrice = new BigDecimal(retailPriceAsString);
              String promotionalPriceAsString =
                  prepareValidPrice(element.getElementsByClass("price").text());
              BigDecimal promotionalPrice = new BigDecimal(promotionalPriceAsString);
              String imageLink = element.getElementsByTag("img").attr("src");
              imageLink = startOfHrefUrl + imageLink;
              String href = element.getElementsByClass("product-content").attr("href");
              href = startOfHrefUrl + href;
              return new BookDto(
                      null,
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
}

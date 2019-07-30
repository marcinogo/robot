package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.BookDto;
import java.math.BigDecimal;
import java.util.List;
import org.jsoup.select.Elements;

/**
 * Scraps data from revelo bookstore website in sales section using Jsoup library.
 *
 * <p>{@link AbstractScrapper} {@link edition.academy.seventh.service.PromotionProvider}
 *
 * @author Bartosz Kupajski
 */
public class RaveloScrapper extends AbstractScrapper {

  private final String bookstoreName;

  RaveloScrapper(
      String startOfUrl, String endOfUrl, String documentClassName, String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }

  /**
   * Scraps 60 positions for each iteration.
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
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("showProductTip").attr("alt");
              String author = element.getElementsByClass("autor").select("a").first().text();
              String retailPriceAsString = prepareValidPrice(element.getElementsByClass("oldPrice").text());
              BigDecimal retailPrice = new BigDecimal(retailPriceAsString);
              String promotionalPriceAsString = prepareValidPrice(element.getElementsByClass("newPrice").text());
              BigDecimal promotionalPrice = new BigDecimal(promotionalPriceAsString);
              String imageLink = element.getElementsByClass("showProductTip").attr("data-src");
              String href = element.getElementsByClass("cover").attr("href");
              return new BookDto(
                  title, "", author, "zł",retailPrice, promotionalPrice, imageLink, href, bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arriveAndDeregister();
  }
}

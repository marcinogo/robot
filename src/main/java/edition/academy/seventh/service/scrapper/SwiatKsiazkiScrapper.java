package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.Book;
import java.util.List;
import org.jsoup.select.Elements;

/**
 * Scraps data from swiatksiazki.pl bookstore website in sales section using JSoup library.
 *
 * {@link AbstractScrapper}
 * {@link edition.academy.seventh.service.PromotionProvider}
 *
 * @author Kacper Staszek
 */
class SwiatKsiazkiScrapper extends AbstractScrapper{

  private final String bookstoreName;

  SwiatKsiazkiScrapper(String startOfUrl, String endOfUrl, String documentClassName,
      String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }

  /**
   * Scraps 30 positions for each iteration.
   *
   * @return {@link List<Book>} after all threads finish their jobs.
   *
   */

  @Override
  public List<Book> getPromotions() {
    for (int i = 1; i <= 3; i++) {
      service.submit(createScrappingTask(i));
      logger.info(
          "Submitting scrapping task for page: "
              + startOfUrl
              + i
              + endOfUrl);
    }

    phaser.arriveAndAwaitAdvance();
    return listOfBooks;
  }

  @Override
  void mappingToBookList(Elements elementsByClass) {
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("product name product-item-name").text();
              title = deleteOutletSign(title);
              String href = element.getElementsByClass("product-item-link").attr("href");
              String img = element.getElementsByClass("product-image-photo lazy").attr("data-src");
              String author =
                  element.getElementsByClass("product author product-item-author").text();
              String promotionPrice = element.getElementsByClass("special-price").text();
              String oldPrice = element.getElementsByClass("old-price").text();
              return new Book(
                  title, "", author, oldPrice, promotionPrice, img, href, bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }

  private String deleteOutletSign(String title) {
    return title.replace("[OUTLET] ", "");
  }
}

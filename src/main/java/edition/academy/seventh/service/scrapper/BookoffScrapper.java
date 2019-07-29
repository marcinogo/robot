package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.BookDto;
import java.util.List;
import org.jsoup.select.Elements;

/**
 * Scraps data from bookoff bookstore website in sales section using Jsoup library.
 *
 * <p>{@link AbstractScrapper} {@link edition.academy.seventh.service.PromotionProvider}
 *
 * @author Bartosz Kupajski
 */
public class BookoffScrapper extends AbstractScrapper {

  private final String bookstoreName;

  BookoffScrapper(String startOfUrl, String endOfUrl, String documentClassName,
      String bookstoreName) {
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
              String retailPrice = element.getElementsByClass("max-price").text();
              String promotionalPrice = element.getElementsByClass("price").text();
              String imageLink = element.getElementsByTag("img").attr("src");
              imageLink = startOfHrefUrl + imageLink;
              String href = element.getElementsByClass("product-content").attr("href");
              href = startOfHrefUrl + href;
              return new BookDto(
                  title, "", author, retailPrice, promotionalPrice, imageLink, href, bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arriveAndDeregister();

  }
}

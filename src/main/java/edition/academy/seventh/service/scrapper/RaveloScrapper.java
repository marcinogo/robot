package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.BookDto;
import org.jsoup.select.Elements;

import java.util.List;

/** @author Bartosz Kupajski */
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
              String retailPrice = element.getElementsByClass("oldPrice").text();
              String promotionalPrice = element.getElementsByClass("newPrice").text();
              String imageLink = element.getElementsByClass("showProductTip").attr("data-src");
              String href = element.getElementsByClass("cover").attr("href");
              return new BookDto(
                  title, "", author, retailPrice, promotionalPrice, imageLink, href, bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arriveAndDeregister();
  }
}

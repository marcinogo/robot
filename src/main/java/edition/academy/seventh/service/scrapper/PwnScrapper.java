package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.Book;
import java.util.List;
import org.jsoup.select.Elements;

/**
 * Scraps data from pwn bookstore website in sales section using Jsoup library

 * {@link AbstractScrapper}
 * {@link edition.academy.seventh.service.PromotionProvider}
 *
 * @author Kacper Staszek
 */
class PwnScrapper extends AbstractScrapper {

  private final String bookstoreName;

  PwnScrapper(String startOfUrl, String endOfUrl, String documentClassName,
      String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }

  /**
   * Scraps 96 positions for each iteration.
   *
   * @return list of books after all threads finish their jobs.
   */

  @Override
  public List<Book> getPromotions() {

    for (int i = 1; i <= 2; i++) {
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
    String startOfTheUrl = "https://ksiegarnia.pwn.pl";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("emp-info-title").text();
              String author = element.getElementsByClass("emp-info-authors").text();
              author = deleteAuthorTag(author);
              String basePrice = element.getElementsByClass("emp-base-price").text();
              String promotionPrice = element.getElementsByClass("emp-sale-price-value").text();
              String img = element.getElementsByTag("img").attr("src");
              String href = element.getElementsByClass("titleLink").attr("href");
              href = startOfTheUrl + href;
              return new Book(
                  title, "", author, basePrice, promotionPrice, img, href, bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }

  private String deleteAuthorTag(String author) {
    return author.replace("Autor: ", "");
  }
}

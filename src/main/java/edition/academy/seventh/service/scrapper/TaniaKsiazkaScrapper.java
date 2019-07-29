package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.BookDto;
import java.util.List;
import org.jsoup.select.Elements;

/**
 * Scraps data from swiatksiazki.pl bookstore website in sales section using JSoup library.
 *
 * <p>{@link AbstractScrapper} {@link edition.academy.seventh.service.PromotionProvider}
 *
 * @author Kacper Staszek
 */
class TaniaKsiazkaScrapper extends AbstractScrapper {

  private final String bookstoreName;

  TaniaKsiazkaScrapper(String startOfUrl, String endOfUrl, String documentClassName,
      String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }


  /**
   * Scraps 30 positions for each iteration.
   *
   * @return {@link List< BookDto >} after all threads finish their jobs.
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
    final String startOfHrefUrl = "https://www.taniaksiazka.pl/";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("product-title").text();
              String author = element.getElementsByClass("product-authors").text();
              String promotionalPrice = element.getElementsByClass("product-price").text();
              String href = element.getElementsByClass("ecommerce-datalayer ").attr("href");
              href = startOfHrefUrl + href;
              String imageLink = element.getElementsByClass("lazyload lazyload-medium")
                  .attr("data-src");
              imageLink = imageLink.substring(2);
              promotionalPrice = deleteCurrencyFromPrice(promotionalPrice);
              String retailPrice = element.getElementsByTag("del").text();
              retailPrice = deleteCurrencyFromPrice(retailPrice);
              return new BookDto(
                  title, "", author, retailPrice, promotionalPrice, imageLink, href, bookstoreName);
            })
        .forEach(listOfBooks::add);
    phaser.arriveAndDeregister();
  }

  private String deleteCurrencyFromPrice(String price) {
    return price.replace(" zł", "");
  }
}

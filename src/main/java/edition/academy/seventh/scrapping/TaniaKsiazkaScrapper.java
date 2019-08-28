package edition.academy.seventh.scrapping;

import edition.academy.seventh.persistence.response.BookDto;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.List;

/**
 * Scraps data from swiatksiazki.pl bookstore website in sales section using JSoup library.
 *
 * <p>{@link AbstractScrapper} {@link PromotionProvider}
 *
 * @author Kacper Staszek
 */
class TaniaKsiazkaScrapper extends AbstractScrapper {

  private final String bookstoreName;

  TaniaKsiazkaScrapper(
      String startOfUrl, String endOfUrl, String documentClassName, String bookstoreName) {
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
              String href = element.getElementsByClass("ecommerce-datalayer ").attr("href");
              href = startOfHrefUrl + href;
              String imageLink =
                  element.getElementsByClass("lazyload lazyload-medium").attr("data-src");
              imageLink = imageLink.substring(2);
              imageLink = "https://" + imageLink;
              String retailPriceAsString =
                  prepareValidPrice(element.getElementsByTag("del").text());
              BigDecimal retailPrice = new BigDecimal(retailPriceAsString);
              String promotionalPriceAsString =
                  prepareValidPrice(element.getElementsByClass("product-price").text());
              BigDecimal promotionalPrice = new BigDecimal(promotionalPriceAsString);
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

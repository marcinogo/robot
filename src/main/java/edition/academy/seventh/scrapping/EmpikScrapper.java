package edition.academy.seventh.scrapping;

import edition.academy.seventh.database.model.BookDto;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Scraps data from empik.com bookstore website in sales section using JSoup library. {@link
 * AbstractScrapper} {@link PromotionProvider}
 *
 * @author Bartosz Kupajski
 */
class EmpikScrapper extends AbstractScrapper {

  private final String bookstoreName;
  private int numberOfPhase = 0;

  EmpikScrapper(
      String startOfUrl, String endOfUrl, String documentClassName, String bookstoreName) {
    super(startOfUrl, endOfUrl, documentClassName);
    this.bookstoreName = bookstoreName;
  }

  /**
   * Index in "for" loop is incremented by 30 since every URL page of Empik's sales section contains
   * id which is multiplicity of 30.
   *
   * <p>Loop condition is 30 books per page x 20 pages, can be changed later.
   *
   * @return list of books after all scrapping threads finish their jobs.
   */
  @Override
  public List<BookDto> getPromotions() {

    for (int i = 1; i <= 30 * 3; i = i + 30) {
      service.submit(createScrappingTask(i));
      logger.info("Submitting scrapping task for page: " + startOfUrl + i + endOfUrl);
    }

    try {
      phaser.awaitAdvanceInterruptibly(numberOfPhase, 3000, TimeUnit.MILLISECONDS);
    } catch (TimeoutException | InterruptedException e) {
      logger.error(
          "Could not scrap every page from empik. Anyway - returned what was already scrapped successfully");
    }
    numberOfPhase++;
    phaser.register();

    return listOfBooks;
  }

  @Override
  void mappingToBookList(Elements elementsByClass) {
    final String startOfHrefUrl = "https://www.empik.com/";
    List<BookDto> collected =
        elementsByClass.stream()
            .map(
                element -> {
                  String title = element.getElementsByClass("ta-product-title").text();
                  String href = element.getElementsByClass("seoTitle").attr("href");
                  href = startOfHrefUrl + href;
                  String imageLink = element.getElementsByClass("lazy").attr("lazy-img");
                  String author = element.getElementsByClass("smartAuthor").text();
                  String prices = element.getElementsByClass("ta-price-tile").text();
                  String[] pricesArray = prices.split(" ");
                  String retailPriceAsString = prepareValidPrice(pricesArray[2]);
                  BigDecimal retailPrice = new BigDecimal(retailPriceAsString);
                  String promotionalPriceAsString = prepareValidPrice(pricesArray[0]);
                  BigDecimal promotionalPrice = new BigDecimal(promotionalPriceAsString);
                  String currency = "zł";
                  return new BookDto(
                      title,
                      "",
                      author,
                      currency,
                      retailPrice,
                      promotionalPrice,
                      imageLink,
                      href,
                      bookstoreName);
                })
            .collect(Collectors.toList());
    listOfBooks.addAll(collected);
    phaser.arriveAndDeregister();
  }
}

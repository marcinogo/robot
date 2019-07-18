package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.Book;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import edition.academy.seventh.service.PromotionProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * Scraps data from pwn bookstore website in sales section using Jsoup library
 *
 * @author Bartosz Kupajski
 */
@Service
public class PwnScrapper implements PromotionProvider {

  private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
  private ExecutorService service = Executors.newFixedThreadPool(40);
  private Phaser phaser = new Phaser(1);

  /**
   * Scraps 96 positions for each iteration.
   *
   * @return list of books after all threads finish their jobs
   */
  @Override
  public List<Book> getPromotions() {

    for (int i = 1; i <= 2; i++) {
      service.submit(createScrappingTask(i));
    }

    phaser.arriveAndAwaitAdvance();
    return listOfBooks;
  }

  private Runnable createScrappingTask(int searchSiteNumber) {
      String startOfUrl = "https://ksiegarnia.pwn.pl/promocje?limit=96&vt=list&page=";
    return () -> {
      phaser.register();
      String url = startOfUrl + searchSiteNumber;
      Document document = null;
      try {
        document = Jsoup.connect(url).timeout(0).get();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      Elements elementsByClass = document.getElementsByClass("emp-product-tile-list");

      mappingToBookList(elementsByClass);
    };
  }

  private void mappingToBookList(Elements elementsByClass) {
    String nameOfTheBookstore = "PWN";
    String startOfTheUrl = "https://ksiegarnia.pwn.pl";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("emp-info-title").text();
              String author = element.getElementsByClass("emp-info-authors").text();
              author = deleteAuthorTag(author);
              String basePrice = element.getElementsByClass("emp-base-price").text();
<<<<<<< HEAD:src/main/java/edition/academy/seventh/serivce/PwnScrapper.java
              basePrice = deleteCurrencyFromPrice(basePrice);
                //              book.setTitle(title);
//              book.setAuthors(author);
//              book.setPrice(basePrice);
//              book.setPromotion(promotionPrice);
//              book.setHref(href);
//              book.setImg(img);
//              book.setBookstore("PWN");
              return new Book(title,author,basePrice,promotionPrice,href,img,"PWN");
=======
              String promotionPrice = element.getElementsByClass("emp-sale-price-value").text();
              String img = element.getElementsByTag("img").attr("src");
              String href = element.getElementsByClass("titleLink").attr("href");
              href = startOfTheUrl + href;
              return new Book(title, "", author, basePrice, promotionPrice, img, href,
                  nameOfTheBookstore);
>>>>>>> eea77c3b34d814bad39b174566c2c781d29322ee:src/main/java/edition/academy/seventh/service/scrapper/PwnScrapper.java
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }

  private String deleteAuthorTag(String author) {
    return author.replace("Autor: ", "");
  }

}

package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Scrap data from pwn bookstore website in sales section using Jsoup library
 * @author Bartosz Kupajski */
public class PwnScrapping implements IPromotionScrapping {

  private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
  private ExecutorService service = Executors.newFixedThreadPool(40);
  private Phaser phaser = new Phaser(1);
  private static final String START_OF_URL = "https://ksiegarnia.pwn.pl/promocje?limit=96&vt=list&page=";

  @Override
  public List<Book> scrapPromotion(){

    for (int i = 1; i <= 200; i++) {
      service.submit(createScrappingTask(i));
    }

    phaser.arriveAndAwaitAdvance();
    return listOfBooks;
  }

  private Runnable createScrappingTask(int serchSiteNumber) {
    return () -> {
      phaser.register();
      String url = START_OF_URL + serchSiteNumber;
      Document document = null;
      try {
        document = Jsoup.connect(url).timeout(0).get();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      Elements elementsByClass = document.getElementsByClass("emp-info-container");

      mappingToBookList(listOfBooks, elementsByClass);
    };
  }

  private void mappingToBookList(List<Book> listOfBooks, Elements elementsByClass) {
    elementsByClass.stream()
        .map(
            s -> {
              String title = s.getElementsByClass("emp-info-title").text();
              String author = s.getElementsByClass("emp-info-authors").text();
              author = deleteAuthorTag(author);
              String promotionPrice = s.getElementsByClass("emp-sale-price-value").text();
              promotionPrice = deleteCurrencyFromPrice(promotionPrice);
              String basePrice = s.getElementsByClass("emp-base-price").text();
              basePrice = deleteCurrencyFromPrice(basePrice);
              Book book = new Book();
              book.setTitle(title);
              book.setAuthors(author);
              book.setPrice(basePrice);
              book.setPromotion(promotionPrice);
              return book;
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }

  private String deleteAuthorTag(String author) {
    return author.replace("Autor: ", "");
  }

  private String deleteCurrencyFromPrice(String price) {
    return price.replace(" zł", "");
  }
}

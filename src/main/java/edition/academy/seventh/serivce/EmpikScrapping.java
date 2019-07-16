package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Bartosz Kupajski
 */
public class EmpikScrapping implements IPromotionScrapping {

    private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
    private ExecutorService service = Executors.newFixedThreadPool(40);
    private Phaser phaser = new Phaser(1);
    /**
        Above that number SSLException is thrown
     */
    private static final int MAX_CONNECTIONS = 1201;

    @Override
    public List<Book> scrapPromotion() throws IOException {

        for(int i = MAX_CONNECTIONS*2; i<= MAX_CONNECTIONS*3 ; i=i+30){

            int finalI = i;
      service.submit(
          () -> {
            phaser.register();
            String url = createUrl(finalI);
            Document doc = null;
            try {
              doc = Jsoup.connect(url).get();
            } catch (IOException e) {
              e.printStackTrace();
            }
            Elements elementsByClass =
                doc.getElementsByClass("product-details-wrapper ta-details-box");

            mappingToBookList(listOfBooks, elementsByClass);
          });
        }
        phaser.arriveAndAwaitAdvance();

    System.out.println("------------------------------------KONIEC----------------------------------------------");

        return listOfBooks;
    }

    private String createUrl(int i) {

        String startOfUrl = "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=";
        String endOfUrl = "&qtype=facetForm";

        return startOfUrl +
                i +
                endOfUrl;
    }

    private void mappingToBookList(List<Book> listOfBooks, Elements elementsByClass) {
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("ta-product-title").text();
              String author = element.getElementsByClass("smartAuthor").text();
              String promotionPrice = element.getElementsByClass("ta-price-tile").text();
              String[] prices = promotionPrice.split(" ");
             // System.out.println(title + author + promotionPrice);
              Book book = new Book();
              book.setTitle(title);
              book.setAuthors(author);
              book.setSubtitle("");
              book.setPrice(prices[2]);
              book.setPromotion(prices[0]);
              return book;
            })
        .forEach(listOfBooks::add);
        phaser.arrive();
    }


}

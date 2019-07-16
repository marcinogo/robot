package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Bartosz Kupajski
 */
public class EmpikScrapping implements IPromotionScrapping {

    private List<Book> listOfBooks = new LinkedList<>();

    @Override
    public List<Book> scrapPromotion() throws IOException {

        for(int i = 1; i<= 91 ; i=i+30){

            String url = createUrl(i);
            Document doc = Jsoup.connect(url).get();
            Elements elementsByClass = doc.getElementsByClass("product-details-wrapper ta-details-box");

            mappingToBookList(listOfBooks, elementsByClass);

        }

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
                .map(element -> {
                    String title = element.getElementsByClass("ta-product-title").text();
                    String author = element.getElementsByClass("smartAuthor").text();
                    String promotionPrice = element.getElementsByClass("ta-price-tile").text();
                    String[] prices = promotionPrice.split(" ");
                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthors(author);
                    book.setPrice(prices[0]);
                    book.setPromotion(prices[2]);
                    return book;
                }).forEach(listOfBooks::add);
    }

}

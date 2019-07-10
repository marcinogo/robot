package edition.academy.seventh.serivces;

import org.testng.annotations.Test;

import java.io.IOException;
import edition.academy.seventh.database.models.Book;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author Bartosz Kupajski
 */

public class ITBookMapperTest {

    @Test
    public void shouldReturnActualTitle() throws IOException {
        String bookJSON = "{\n" +
                "    \"error\": \"0\",\n" +
                "    \"title\": \"Learning JavaScript\",\n" +
                "    \"subtitle\": \"A Hands-On Guide to the Fundamentals of Modern JavaScript\",\n" +
                "    \"authors\": \"Tim Wright\",\n" +
                "    \"publisher\": \"Addison-Wesley\",\n" +
                "    \"language\": \"English\",\n" +
                "    \"isbn10\": \"0321832744\",\n" +
                "    \"isbn13\": \"9780321832740\",\n" +
                "    \"pages\": \"360\",\n" +
                "    \"year\": \"2012\",\n" +
                "    \"rating\": \"4\",\n" +
                "    \"desc\": \"With the arrival of HTML5, jQuery, and Ajax, JavaScript web development skills are more valuable  than ever! This complete, hands-on JavaScript tutorial covers everything you need to know now.  Using line-by-line code walkthroughs and end-of-chapter exercises, top web developer and speaker Tim Wrigh...\",\n" +
                "    \"price\": \"$8.99\",\n" +
                "    \"image\": \"https://itbook.store/img/books/9780321832740.png\",\n" +
                "    \"url\": \"https://itbook.store/books/9780321832740\"\n" +
                "}";

        List<String> listOfBooks = new LinkedList<>();

        listOfBooks.add(bookJSON);

        ITBookMapper itBookMapper = new ITBookMapper();

        List<Book> list = itBookMapper.mapListOfJSON(listOfBooks);

        String title = list.get(0).getTitle();

        assertEquals(title,"Learning JavaScript");
    }
}
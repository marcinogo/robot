package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/** @author Kamil Rojek */
public class EagerBookPaginationTest {
  private BookService bookService;

  @BeforeMethod
  public void setUp() {
    bookService = mock(BookService.class);
  }

  @Test
  public void should_return20BooksFromCurrentPage_when_25BooksWereLoadedFromDatabase() {
    //Given
    EagerBookPagination eagerBookPagination;
    List<BookDto> books = generateListOfDtoBooks(25);

    //When
    when(bookService.getBooksFromDatabase()).thenReturn(books);
    eagerBookPagination = new EagerBookPagination(bookService);
    List<BookDto> currentPage = eagerBookPagination.currentPage();

    //Then
    assertEquals(currentPage.size(), 20);
  }

  @Test
  public void should_return0BooksFromCurrentPage_when_NoBooksWereLoadedFromDatabase() {
    //Given
    EagerBookPagination eagerBookPagination;
    List<BookDto> emptyList = new ArrayList<>();

    //When
    when(bookService.getBooksFromDatabase()).thenReturn(emptyList);
    eagerBookPagination = new EagerBookPagination(bookService);
    List<BookDto> currentPage = eagerBookPagination.currentPage();

    //Then
    assertEquals(currentPage.size(), 0);
  }

  @Test
  public void testNextPage() {}

  @Test
  public void testPreviousPage() {}

  @Test
  public void testChangePaginationSize() {}

  @Test
  public void testChangeFilter() {}

  private List<BookDto> generateListOfDtoBooks(int booksQuantity) {
    List<BookDto> books = new ArrayList<>();
    for (int i = 0; i < booksQuantity; i++) {
      books.add(mock(BookDto.class));
    }
    return books;
  }
}

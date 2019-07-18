package edition.academy.seventh.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.repository.BookRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mockito.invocation.Invocation;
import org.testng.annotations.Test;

@Test
public class BookServiceTest {

  @Test
  public void
      should_addBooksToDatabaseMethodBeInvokedOnce_when_listOfBooksPassedAsParameterIsGreaterThanOne() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    DTBook book = mock(DTBook.class);

    BookService bookService = new BookService(bookRepository);
    List<DTBook> books = new ArrayList<>();

    // When
    books.add(book);
    books.add(book);
    bookService.addBooksToDatabase(books);

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("addBooksToDatabase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }

  @Test
  public void
      should_getAllBooksFromDatabaseMethodBeInvokedOnce_when_getBooksFromDatabaseIsCalled() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    DTBook book = mock(DTBook.class);

    BookService bookService = new BookService(bookRepository);

    // When
    when(bookRepository.getBooksFromDatabase()).thenReturn(List.of(book));

    bookService.getBooksFromDatabase();

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("getBooksFromDatabase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }
}

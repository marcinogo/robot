package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.repository.BookRepository;
import org.mockito.invocation.Invocation;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

@Test
public class BookServiceTest {

  @Test
  public void
      should_addBooksToDatabaseMethodBeInvokedOnce_when_listOfBooksPassedAsParameterIsGreaterThanOne() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    Book book = mock(Book.class);

    BookService bookService = new BookService(bookRepository);
    List<Book> books = new ArrayList<>();

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
    Book book = mock(Book.class);

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

package edition.academy.seventh.scrapping;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.repository.BookRepository;
import edition.academy.seventh.service.BookService;
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
      method_addBooksToDatabase_shouldBeInvokedOnce_when_listOfBooksPassedAsParameterIsGreaterThanOne() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    BookDto book = mock(BookDto.class);

    BookService bookService = new BookService(bookRepository);
    List<BookDto> books = new ArrayList<>();

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
      method_getAllBooksFromDatabase_shouldBeInvokedOnce_when_getBooksFromDatabaseIsCalled() {
    // Given
    BookRepository bookRepository = mock(BookRepository.class);
    BookDto book = mock(BookDto.class);

    BookService bookService = new BookService(bookRepository);

    // When
    when(bookRepository.getLatestBooksFromDatabase()).thenReturn(List.of(book));

    bookService.getBooksFromDatabase();

    Collection<Invocation> invocations = mockingDetails(bookRepository).getInvocations();
    long validationCalls =
        invocations.stream()
            .filter(invocation -> invocation.toString().contains("getLatestBooksFromDatabase"))
            .count();

    // Then
    assertEquals(validationCalls, 1);
  }
}

package edition.academy.seventh.pagination;

import edition.academy.seventh.persistence.model.BookstoreBook;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** @author Agnieszka Trzewik */
class PaginationResult {

  private static final int MAX_RESULT_ON_PAGE = 20;

  private List<BookstoreBook> bookstoreBooksOnPage;
  private List<BookstoreBook> bookstoreBooks;

  PaginationResult(Query<BookstoreBook> query) {
    bookstoreBooks = query.getResultList();
  }

  PaginationResult changePaginationResult(int page) {
    if (page < 1) bookstoreBooksOnPage = Collections.emptyList();
    else {
      final int pageIndex = page - 1;

      int fromRecordIndex = pageIndex * MAX_RESULT_ON_PAGE;
      int maxRecordIndex = fromRecordIndex + MAX_RESULT_ON_PAGE;

      this.bookstoreBooksOnPage =
          bookstoreBooks.stream()
              .filter(
                  result -> result.getId() >= fromRecordIndex && result.getId() < maxRecordIndex)
              .collect(Collectors.toList());
    }
    return this;
  }

  List<BookstoreBook> getBookstoreBooksOnPage() {
    return bookstoreBooksOnPage;
  }
}

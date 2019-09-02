package edition.academy.seventh.pagination;

import edition.academy.seventh.persistence.model.BookstoreBook;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

/** @author Agnieszka Trzewik */
public class PaginationResult {


  private final int MAX_RESULT_ON_PAGE = 20;

  private List<BookstoreBook> list;
  private List<BookstoreBook> resultStream;

  PaginationResult(Query<BookstoreBook> query) {
    resultStream = query.getResultList();
  }

  PaginationResult changePaginationResult(int page) {
    final int pageIndex = page - 1 < 0 ? 0 : page - 1;

    int fromRecordIndex = pageIndex * MAX_RESULT_ON_PAGE;
    int maxRecordIndex = fromRecordIndex + MAX_RESULT_ON_PAGE;

    this.list =
        resultStream.stream()
            .filter(result -> result.getId() >= fromRecordIndex && result.getId() < maxRecordIndex)
            .collect(Collectors.toList());

    return this;
  }

  public List<BookstoreBook> getList() {
    return list;
  }
}

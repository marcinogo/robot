package edition.academy.seventh.pagination;

import edition.academy.seventh.persistence.response.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;

/** @author Agnieszka Trzewik */
@Service
public class PaginationService {

  private final PaginationRepository paginationRepository;

  public PaginationService(PaginationRepository paginationRepository) {
    this.paginationRepository = paginationRepository;
  }

  List<BookDto> currentPage() {
      return paginationRepository.getCurrentPage();
  }

  List<BookDto> nextPage() {
    return paginationRepository.getNextPage();
  }

  List<BookDto> previousPage() {
    return paginationRepository.getPreviousPage();
  }
}

package edition.academy.seventh.display;

/**
 * Available {@link Pagination} sizes.
 *
 * @author Kamil Rojek
 */
public enum PaginationSize {

  /** Provides ten elements per page. */
  TEN(10),

  /** Provides twenty elements per page. */
  TWENTY(20),

  /** Provides fifty elements per page. */
  FIFTY(50),

  /** Provides hundred elements per page. */
  HUNDRED(100);

  /** Retrieves pagination size as {@code long}. */
  long value;

  PaginationSize(int value) {
    this.value = value;
  }
}

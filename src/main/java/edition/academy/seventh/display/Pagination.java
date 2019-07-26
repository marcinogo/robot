package edition.academy.seventh.display;

import java.util.List;

/**
 * Divides {@code all <E> elements} into pages. Pagination size of the simple page should be
 * implemented according to the {@link PaginationSize} enum.
 *
 * @param <E> Bunch of elements according to pagination size.
 * @author Kamil Rojek
 */
public interface Pagination<E> {

  /**
   * Retrieves {@code List<E> elements} from current page. If no elements retrieved returns {@code
   * empty List<E>}.
   *
   * @return {@code List<E> elements}.
   */
  List<E> currentPage();

  /**
   * Retrieves {@code List<E> elements} from next page. If no elements retrieved returns {@link
   * Pagination#currentPage()}.
   *
   * @return {@code List<E> elements}.
   */
  List<E> nextPage();

  /**
   * Retrieves {@code List<E> elements} from previous page. If no elements retrieved returns {@link
   * Pagination#currentPage()}.
   *
   * @return {@code List<E> elements}.
   */
  List<E> previousPage();

  /**
   * Changes {@link PaginationSize pagination size} and retrieves {@code List<E> new size of
   * elements}.
   *
   * @return {@code List<E> elements}.
   */
  List<E> changePaginationSize(PaginationSize size);

  /**
   * Changes display {@link Filter} and retrieves {@code List<E> filtered elements}.
   *
   * @param filter implementation of filter.
   * @return {@code List<E> elements}.
   */
  List<E> changeFilter(Filter filter);
}

package edition.academy.seventh.display;

/**
 * Marker interface used by {@link Pagination#changeFilter(Filter)} interface method. The primary
 * purpose of this interface is to allow generic filter implementation to alter their behaviour.
 *
 * @see BookFilter BookFilter implemenation.
 * @author Kamil Rojek
 */
public interface Filter {}

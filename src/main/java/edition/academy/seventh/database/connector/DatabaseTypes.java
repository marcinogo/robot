package edition.academy.seventh.database.connector;

/**
 * Available types for database's connector.
 *
 * <ul>
 *   <li>{@link #H2}<br>
 *       Cached database used mostly for debugging.
 *   <li>{@link #POSTGRESQL}<br>
 *       Our main database, used on production.
 * </ul>
 *
 * @author Kamil Rojek
 */
public enum DatabaseTypes {
  /** Provides connection with cached H2 database */
  H2,
  /** Provides connection with PostgreSQL database */
  POSTGRESQL
}

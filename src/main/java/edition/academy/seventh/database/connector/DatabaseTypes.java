package edition.academy.seventh.database.connector;

/**
 * Available types for database's connector.
 *
 * <ul>
 *   <li>{@link #H2}<br>
 *       Cached database used mostly for debugging and testing.
 *   <li>{@link #POSTGRESQL}<br>
 *       Main database, used on production.
 * </ul>
 *
 * @author Kamil Rojek
 */
public enum DatabaseTypes {
  /** Provides connection to cached H2 database */
  H2,
  /** Provides connection to PostgreSQL database */
  POSTGRESQL
}

package edition.academy.seventh.database.connector;

/**
 * Available types for database's connector.
 *
 * <ul>
 *   <li>{@link #H2}<br>
 *       A thread that has not yet started is in this state.
 *   <li>{@link #POSTGRESQL}<br>
 *       A thread executing in the Java virtual machine is in this state.
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

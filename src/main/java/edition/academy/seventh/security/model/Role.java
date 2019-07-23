package edition.academy.seventh.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Represents user roles in terms of authorization.
 *
 * @author Patryk Kucharski
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private UserRole userRole;

  /**
   * Creates new role based on {@link UserRole role provided}.
   *
   * @param userRole {@link UserRole user role}.
   */
  public Role(UserRole userRole) {
    this.userRole = userRole;
  }
}

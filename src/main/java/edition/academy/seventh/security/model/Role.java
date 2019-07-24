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
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 60)
  private RoleName name;

  /**
   * Creates new role based on {@link RoleName role provided}.
   *
   * @param name {@link RoleName user role}.
   */
  public Role(RoleName name) {
    this.name = name;
  }
}

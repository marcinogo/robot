package edition.academy.seventh.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/** @author Patryk Kucharski */
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
  private UserRole name;

  /**
   * asdada. todo
   *
   * @param name of the role {@link UserRole}
   */
  public Role(UserRole name) {
    this.name = name;
  }
}

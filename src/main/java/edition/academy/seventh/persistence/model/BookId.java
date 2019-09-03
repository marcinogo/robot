package edition.academy.seventh.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookId implements Serializable {

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;
}

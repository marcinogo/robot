package edition.academy.seventh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookId implements Serializable {

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;
}

package books.db;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@ToString // (exclude = {"book"})
public class Inventory {

    @Id
    @OneToOne
    @JoinColumn(name = "book_isbn")
    @JsonbTransient
    @ToString.Exclude
    private Book book;

    private Integer sold;
    private Integer supplied;
    private Integer version;

}

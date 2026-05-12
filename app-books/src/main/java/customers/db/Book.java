package customers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "books")
@Getter
@Setter
public class Book {

    @Id
    @Column(name = "boo_isbn")
    private String isbn;
    @Column ( name = "boo_price")
    private double price;
    @Column ( name = "boo_title")
    private String title;
    @Version
    @Column ( name = "boo_version")
    private Integer version;

    // Relaciones
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Inventory inventory;

}
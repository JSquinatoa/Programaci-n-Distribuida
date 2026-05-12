package customers.db;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "inventories")
@Getter
@Setter
public class Inventory {

    @Id
    @Column(name = "inv_bookIsbr")
    private String bookIsbr;
    @Column(name = "inv_solid")
    private Integer solid;
    @Column(name = "inv_supplied")
    private Integer supplied;
    @Version
    @Column(name = "inv_version")
    private Integer version;

    // Relaciones
    @JsonbTransient
    @OneToOne
    @JoinColumn(name = "inv_bookIsbr", insertable = false, updatable = false)
    private Book book;


}

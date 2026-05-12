package customers.db;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lineitem")
@IdClass(LineItemId.class)
@Getter
@Setter
public class LineItem {



    @Id
    @Column(name = "lin_idx")
    private Integer idx;

    @Column(name = "lin_book_isbr")
    private String bookIsbr;

    @Column(name = "lin_quantity")
    private Integer quantity;

    // Relaciones

    @Id
    @ManyToOne
    @JoinColumn(name = "lin_order_id")
    @JsonbTransient
    private PurchaseOrder order;

}

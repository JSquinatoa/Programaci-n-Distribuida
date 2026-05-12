package customers.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchaseorder")
@Getter
@Setter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pur_id")
    private Long id;
    @Column(name = "pur_deliveredor")
    private LocalDateTime deliveredor;
    @Column(name = "pur_placedon")
    private LocalDateTime placedon;
    @Column(name = "pur_status")
    private Integer status;
    @Column(name = "pur_total")
    private Integer total;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "pur_customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LineItem> lineItems;

}

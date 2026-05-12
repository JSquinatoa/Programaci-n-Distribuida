package customers.db;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cus_id")
    private Long id;
    @Column(name = "cus_email")
    private String email;
    @Column(name = "cus_name")
    private String name;
    @Version
    @Column(name = "cus_version")
    private Integer version;

    // Relaciones
    @JsonbTransient
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PurchaseOrder> orders;


}

package customers.db;

import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Setter
public class LineItemId implements Serializable {
    private Long order;
    private Integer idx;
}

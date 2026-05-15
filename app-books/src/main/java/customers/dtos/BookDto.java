package customers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class BookDto {

    private String isbn;
    private String title;
    private BigDecimal price;

    private Integer invetorySold;
    private Integer invetorySupplied;

}

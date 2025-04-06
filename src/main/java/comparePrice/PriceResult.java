package comparePrice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auth Felix
 * @since 2025/3/2 20:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceResult {
    private Integer id;
    private String name;
    private String platform;
    private float discount;
    private float price;
    private float readPrice;
}

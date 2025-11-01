package dev.alexisardaya.productservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
    @NotBlank(message = "{product.name.notblank}")
    @Size(min = 2, max = 120, message = "{product.name.size}")
    String name,

    @NotBlank(message = "{product.description.notblank}")
    @Size(min = 10, max = 255, message = "{product.description.size}")
    String description,

    @NotNull(message = "{product.price.notnull}")
    @DecimalMin(value = "0.01", message = "{product.price.decimalmin}")
    BigDecimal price,

    @NotNull(message = "{product.stock.notnull}")
    @Min(value = 0, message = "{product.stock.min}")
    Integer stock,

    @NotEmpty(message = "{product.categoryIds.notempty}")
    List<@NotNull Long> categoryIds
) {

}

package dev.alexisardaya.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank(message = "{category.name.notblank}")
    @Size(min = 2, max = 100, message = "{category.name.size}")
    String name,

    @Size(max = 500, message = "{category.description.size}")
    String description
) {

}


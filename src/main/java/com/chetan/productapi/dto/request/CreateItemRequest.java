package com.chetan.productapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {

    @NotNull
    private Long productId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}

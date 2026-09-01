package com.chetan.productapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {
    private Long id;
    private Integer quantity;
}
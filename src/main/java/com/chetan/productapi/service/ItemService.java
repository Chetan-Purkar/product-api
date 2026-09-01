package com.chetan.productapi.service;

import com.chetan.productapi.dto.request.CreateItemRequest;
import com.chetan.productapi.dto.response.ItemResponse;

import java.util.List;

public interface ItemService {

    ItemResponse addItem(CreateItemRequest request);

    List<ItemResponse> getItemsByProduct(Long productId);
}

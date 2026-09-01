package com.chetan.productapi.service.impl;

import com.chetan.productapi.dto.request.CreateItemRequest;
import com.chetan.productapi.dto.response.ItemResponse;
import com.chetan.productapi.entity.Item;
import com.chetan.productapi.entity.Product;
import com.chetan.productapi.exception.ResourceNotFoundException;
import com.chetan.productapi.repository.ItemRepository;
import com.chetan.productapi.repository.ProductRepository;
import com.chetan.productapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    @Override
    public ItemResponse addItem(CreateItemRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        Item item = Item.builder()
                .product(product)
                .quantity(request.getQuantity())
                .build();

        Item saved = itemRepository.save(item);

        return ItemResponse.builder()
                .id(saved.getId())
                .quantity(saved.getQuantity())
                .build();
    }

    @Override
    public List<ItemResponse> getItemsByProduct(Long productId) {

        return itemRepository.findByProductId(productId)
                .stream()
                .map(item ->
                        ItemResponse.builder()
                                .id(item.getId())
                                .quantity(item.getQuantity())
                                .build())
                .collect(Collectors.toList());
    }
}

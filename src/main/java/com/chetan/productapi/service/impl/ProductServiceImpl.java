package com.chetan.productapi.service.impl;

import com.chetan.productapi.dto.request.ItemRequest;
import com.chetan.productapi.dto.request.ProductRequest;
import com.chetan.productapi.dto.response.ItemResponse;
import com.chetan.productapi.dto.response.ProductResponse;
import com.chetan.productapi.entity.Item;
import com.chetan.productapi.entity.Product;
import com.chetan.productapi.exception.ResourceNotFoundException;
import com.chetan.productapi.repository.ProductRepository;
import com.chetan.productapi.security.SecurityUtils;
import com.chetan.productapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        String currentUser = SecurityUtils.getCurrentUserEmail(); // implement helper

        Product product = Product.builder()
                .productName(request.getProductName())
                .createdBy(currentUser)
                .build();

        if (request.getItems() != null) {
            List<Item> items = request.getItems().stream()
                    .map(itemRequest -> Item.builder()
                            .quantity(itemRequest.getQuantity())
                            .product(product)
                            .build())
                    .collect(Collectors.toList());
            product.setItems(items);
        }

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        String currentUser = SecurityUtils.getCurrentUserEmail();
        product.setProductName(request.getProductName());
        product.setModifiedBy(currentUser);

        // Update items: clear existing and add new
        product.getItems().clear();
        if (request.getItems() != null) {
            List<Item> items = request.getItems().stream()
                    .map(itemRequest -> Item.builder()
                            .quantity(itemRequest.getQuantity())
                            .product(product)
                            .build())
                    .collect(Collectors.toList());
            product.getItems().addAll(items);
        }

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        List<ItemResponse> itemResponses = product.getItems().stream()
                .map(item -> ItemResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .modifiedBy(product.getModifiedBy())
                .modifiedOn(product.getModifiedOn())
                .items(itemResponses)
                .build();
    }
}
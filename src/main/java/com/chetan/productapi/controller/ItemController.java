package com.chetan.productapi.controller;

import com.chetan.productapi.dto.request.CreateItemRequest;
import com.chetan.productapi.dto.response.ItemResponse;
import com.chetan.productapi.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Item Management", description = "Endpoints for managing items")
@SecurityRequirement(name = "bearerAuth")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new item to a product")
    public ResponseEntity<ItemResponse> addItem(@Valid @RequestBody CreateItemRequest request) {
        return new ResponseEntity<>(itemService.addItem(request), HttpStatus.CREATED);
    }

    // Optional: additional endpoints like update, delete, get by id etc.
}
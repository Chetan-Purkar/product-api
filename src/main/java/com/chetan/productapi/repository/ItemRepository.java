package com.chetan.productapi.repository;

import com.chetan.productapi.entity.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByProductId(Long productId);
}
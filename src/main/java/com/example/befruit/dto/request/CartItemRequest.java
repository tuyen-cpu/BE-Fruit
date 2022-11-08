package com.example.befruit.dto.request;

import com.example.befruit.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Long id;
    private Integer quantity;
    private Long productId;
    private Long userId;
}
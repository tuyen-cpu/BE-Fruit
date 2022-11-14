package com.example.befruit.converter;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.request.CartItemRequest;
import com.example.befruit.dto.response.CartItemResponse;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.CartItem;
import com.example.befruit.entity.Category;
import com.example.befruit.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class CartItemConverter {
    @Autowired
    private ModelMapper modelMapper;
    public CartItemResponse convertToResponse(CartItem entity){
        CartItemResponse cartItemResponse = modelMapper.map(entity, CartItemResponse.class);
        ProductResponse productResponse= modelMapper.map(entity.getProduct(),ProductResponse.class);
        ImageDTO imageDTO = modelMapper.map(entity.getProduct().getImages().get(0),ImageDTO.class);
        productResponse.setImage(imageDTO);
        cartItemResponse.setProduct(productResponse);
        return cartItemResponse;

    }
    public CartItemResponse convertToResponse(CartItemRequest entity){
        return modelMapper.map(entity, CartItemResponse.class);
    }

    public CartItem convertToEntity(CartItemRequest dto){
        return modelMapper.map(dto, CartItem.class);
    }
    public CartItem convertToEntity(CartItemResponse dto){
        return modelMapper.map(dto, CartItem.class);
    }
    public Page<CartItemResponse> convertToResponse(Page<CartItem> pageEntity){
        if (pageEntity == null) {
            return null;
        }
        return pageEntity.map(this::convertToResponse);
    }
}

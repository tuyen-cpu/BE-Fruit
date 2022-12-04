package com.example.befruit.service.impl;

import com.example.befruit.converter.CartItemConverter;
import com.example.befruit.dto.request.CartItemRequest;
import com.example.befruit.dto.response.CartItemResponse;
import com.example.befruit.entity.CartItem;
import com.example.befruit.entity.Product;
import com.example.befruit.repo.CartItemRepo;
import com.example.befruit.repo.ProductRepo;
import com.example.befruit.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CartItemService implements ICartService {

	@Autowired
	private CartItemRepo cartItemRepo;
	@Autowired
	private CartItemConverter cartItemConverter;

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Page<CartItemResponse> getByUserId(Long userId, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return cartItemConverter.convertToResponse(cartItemRepo.findAllByUserId(userId, pageable));
	}

	@Override
	public CartItemResponse add(CartItemRequest cartItemRequest) {
		Product product = getProductById(cartItemRequest.getProductId());
		Integer quantityProduct = product.getQuantity();
		if (quantityProduct < cartItemRequest.getQuantity()) {
			throw new RuntimeException("Quantity of product is not enough");
		}

		Optional<CartItem> cartItemDB = cartItemRepo.findByProductIdAndUserId(cartItemRequest.getProductId(), cartItemRequest.getUserId());
		if (cartItemDB.isPresent()) {
			CartItem newCartItem = cartItemDB.get();
			if (quantityProduct >= cartItemRequest.getQuantity() + newCartItem.getQuantity()) {

				newCartItem.setQuantity(newCartItem.getQuantity() + cartItemRequest.getQuantity());
				CartItem add = cartItemRepo.save(newCartItem);

				return cartItemConverter.convertToResponse(add);
			} else {
				throw new RuntimeException("Quantity of product is not enough");
			}
		}

		if (quantityProduct >= cartItemRequest.getQuantity()) {
			CartItem cartItem = cartItemConverter.convertToEntity(cartItemRequest);
			Product product1 = productRepo.findById(cartItem.getProduct().getId())
					.orElseThrow(() -> new EntityNotFoundException("Product "+cartItem.getProduct().getId()+" does not exist!"));
			cartItem.setProduct(product1);
			CartItem add = cartItemRepo.save(cartItem);

//            System.out.println(add.toString());
			return cartItemConverter.convertToResponse(add);
		} else {
			throw new RuntimeException("Quantity of product is not enough");
		}
	}

	private Product getProductById(Long id) {
		return productRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product "+id+" does not exist!"));
	}

	@Override
	public CartItemResponse update(CartItemRequest cartItemRequest) {
		Product product = getProductById(cartItemRequest.getProductId());
		Integer quantityProduct = product.getQuantity();
		if (quantityProduct < cartItemRequest.getQuantity()) {
			throw new RuntimeException("Quantity of product is not enough");
		}

		return cartItemConverter.convertToResponse(cartItemRepo.save(cartItemConverter.convertToEntity(cartItemRequest)));


	}

	@Override
	public void delete(Long id) {
		cartItemRepo.deleteById(id);
	}

	@Override
	public void deleteByUserId(Long userId) {
		cartItemRepo.deleteAllByUserId(userId);
	}
}

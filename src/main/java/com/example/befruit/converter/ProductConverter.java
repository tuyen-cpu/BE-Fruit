package com.example.befruit.converter;

import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter {
	@Autowired
	private ModelMapper modelMapper;

	public Product convertToEntity(ProductResponse productResponse) {
		return modelMapper.map(productResponse, Product.class);
	}

	public ProductResponse convertToResponse(Product product) {
		ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
		if(product.getImages().size()>0){
			ImageDTO imageDTO = modelMapper.map(product.getImages().get(0), ImageDTO.class);
			productResponse.setImage(imageDTO);
		}
		return productResponse;
	}

	public Page<ProductResponse> convertToResponse(Page<Product> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToResponse);
	}

}

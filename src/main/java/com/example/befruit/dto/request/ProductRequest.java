package com.example.befruit.dto.request;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.response.CategoryResponse;
import com.example.befruit.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {
	private Long id;
	private String name;
	private Long price;
	private String description;
	private Integer discount;
	private Integer quantity;
	private Integer status;
	private CategoryResponse category;
	private List<ImageDTO> images;
}

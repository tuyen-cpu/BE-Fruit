package com.example.befruit.controller.admin;

import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.request.ProductRequest;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.IImageService;
import com.example.befruit.service.IProductService;
import com.example.befruit.service.IStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/product")
public class ProductManagerController {
	private final int MAX_NUMBER_IMAGE=5;
	@Autowired
	private IProductService productService;
	@Autowired
	private IStorageService storageService;

	@Autowired
	private IImageService imageService;

	@GetMapping("/all")
	public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "categoryId", required = false) long categoryId,
																																	 @RequestParam(name = "price", required = false) long price,
																																	 @RequestParam(name = "page", defaultValue = "0") int page,
																																	 @RequestParam(name = "size", defaultValue = "10") int size) {
		try {
			Page<ProductResponse> products;
			if (categoryId == 0) {
				products = productService.getAll(price, null, page, size);
			} else {
				products = productService.getAllByCategoryId(categoryId, price, null, page, size);
			}
			return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}
	}

	@PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseEntity<ResponseObject> add(@RequestPart("product") String productRequest, @RequestPart(value = "file",required = false) MultipartFile[] file) throws JsonProcessingException {
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			ProductRequest product = objectMapper.readValue(productRequest, ProductRequest.class);

			ProductResponse productResponse = productService.add(product);
			if(file!=null && file.length>0){
				List<String> listImage = new ArrayList<>();
				for (MultipartFile multipartFile : file) {
					String generatedFileName = storageService.storeFile(multipartFile);
					listImage.add(generatedFileName);
				}
				if(imageService.getSizeByProductId(product.getId())+file.length>MAX_NUMBER_IMAGE){
					throw new RuntimeException("The allowed number of photos has been exceeded (maximum 5 photos)");
				}
				List<ImageDTO> imageDTOs = imageService.add(listImage.stream().map(e -> new ImageDTO(null, e, productResponse.getId())).collect(Collectors.toList()));
				imageService.add(imageDTOs);
			}

			ProductResponse result = productService.getById(productResponse.getId());
			return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", result));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("faild", e.getMessage(), ""));

		}

	}

}

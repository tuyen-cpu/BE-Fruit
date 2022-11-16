package com.example.befruit.service;

import com.example.befruit.dto.ImageDTO;

import java.util.List;

public interface IImageService {
	List<ImageDTO> getAllByProductId(Long id);

	ImageDTO getByProductId(Long id);
}

package com.example.befruit.service.impl;

import com.example.befruit.converter.ImageConverter;
import com.example.befruit.dto.ImageDTO;
import com.example.befruit.entity.Image;
import com.example.befruit.repo.ImageRepo;
import com.example.befruit.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService implements IImageService {
	@Autowired
	private ImageConverter imageConverter;
	@Autowired
	private ImageRepo imageRepo;

	@Override
	public List<ImageDTO> getAllByProductId(Long id) {
		return imageRepo.findAllByProductId(id).stream().map(entity -> imageConverter.convertToDto(entity)).collect(Collectors.toList());
	}

	@Override
	public ImageDTO getByProductId(Long id) {
		return imageConverter.convertToDto(imageRepo.findTopByProductId(id));
	}

	@Override
	public ImageDTO add(ImageDTO imageDTO) {
		Image image = imageConverter.convertToEntity(imageDTO);
		return imageConverter.convertToDto(imageRepo.save(image));
	}

	@Override
	public List<ImageDTO> add(List<ImageDTO> imageDTOs) {
		List<Image> images = imageDTOs.stream().map(e->imageConverter.convertToEntity(e)).collect(Collectors.toList());
		List<Image> imagesAdded	=imageRepo.saveAll(images);
		return imagesAdded.stream().map(e->imageConverter.convertToDto(e)).collect(Collectors.toList());
	}

	@Override
	public Integer getSizeByProductId(Long productId) {
		return imageRepo.getSizeByProductId(productId);
	}
}
